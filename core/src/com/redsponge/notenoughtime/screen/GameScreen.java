package com.redsponge.notenoughtime.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

public abstract class GameScreen extends AbstractScreen{

    private static final BitmapFont titleFont;
    private static final BitmapFont instructionsFont;

    static {
        FreeTypeFontGenerator gen = new FreeTypeFontGenerator(Gdx.files.internal("FFFFORWA.TTF"));
        FreeTypeFontParameter param = new FreeTypeFontParameter();
        param.size = 32;
        titleFont = gen.generateFont(param);
        gen.dispose();

        gen = new FreeTypeFontGenerator(Gdx.files.internal("dotty.TTF"));
        param = new FreeTypeFontParameter();
        param.size = 32;
        instructionsFont = gen.generateFont(param);
        gen.dispose();
    }

    private boolean started;
    private ExtendViewport instructionsViewport;

    @Override
    public void setup() {
        started = false;
        instructionsViewport = new ExtendViewport(500, 500);
    }

    @Override
    public final void tick(float delta) {
        if(started) tickGame(delta);
        else {
            if(Gdx.input.isKeyJustPressed(Keys.SPACE)) {
                startGame();
            }
        }
    }

    private void startGame() {
        gameStarted();
        started = true;
    }

    @Override
    public final void render() {
        if(started) renderGame();
        else {
            Color BG = getInstructionsBackgroundColor();
            Gdx.gl.glClearColor(BG.r, BG.g, BG.b, BG.a);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

            instructionsViewport.apply();
            batch.setProjectionMatrix(instructionsViewport.getCamera().combined);

            batch.begin();
            titleFont.setColor(getTitleColor());
            GlyphLayout layout = new GlyphLayout(titleFont, getTitle().toUpperCase());
            titleFont.draw(batch, getTitle().toUpperCase(), instructionsViewport.getWorldWidth()/2-layout.width/2, instructionsViewport.getWorldHeight()/4*3 + layout.height/2);

            instructionsFont.setColor(Color.WHITE);
            int i = 0;
            for (String instruction : getInstructions()) {
                GlyphLayout instructions = new GlyphLayout(instructionsFont, instruction);
                instructionsFont.draw(batch, instruction, instructionsViewport.getWorldWidth()/2-instructions.width/2, instructionsViewport.getWorldHeight()/2 + instructions.height/2-16*i);
                i++;
            }

            instructionsFont.setColor(Color.GRAY);
            GlyphLayout bottom = new GlyphLayout(instructionsFont, "Press [Space] To Start The Game");
            instructionsFont.draw(batch, "Press [Space] To Start The Game", instructionsViewport.getWorldWidth()/2 - bottom.width/2, 20);
            batch.end();

        }
    }

    @Override
    public void resize(int width, int height) {
        instructionsViewport.update(width, height, true);
    }

    public abstract void gameStarted();

    public abstract void tickGame(float delta);

    public abstract void renderGame();

    public abstract String getTitle();

    public abstract Color getTitleColor();

    public abstract String[] getInstructions();

    public abstract Color getInstructionsBackgroundColor();
}
