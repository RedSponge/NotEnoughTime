package com.redsponge.notenoughtime.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.redsponge.notenoughtime.NotEnoughTime;

public class IntroScreen extends AbstractScreen {

    long startTime;
    private BitmapFont font;
    private ExtendViewport viewport;

    @Override
    public void setup() {
        startTime = TimeUtils.nanoTime();
        viewport = new ExtendViewport(500, 500);

        FreeTypeFontGenerator gen = new FreeTypeFontGenerator(Gdx.files.internal("dotty.ttf"));
        FreeTypeFontParameter param = new FreeTypeFontParameter();
        param.size = 32;
        font = gen.generateFont(param);
        gen.dispose();
    }

    @Override
    public void tick(float delta) {

    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0.005f, 0.06f, 0.14f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        float secondsSince = (TimeUtils.nanoTime() - startTime) / 1000000000f;
        String render = "";

        if(secondsSince < 3) {
            render = "Hello!";
        } else if(secondsSince < 8) {
            render = "As you can see..\nI didn't have enough time to make a game...";
        } else if(secondsSince < 12) {
            render = "I did however.. Manage to make some minigames about the theme \"Time\"!";
        } else {
            NotEnoughTime.instance.setScreen(new MenuScreen());
        }

        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);

        batch.begin();
        GlyphLayout layout = new GlyphLayout(font, render);

        font.draw(batch, render, viewport.getWorldWidth()/2, viewport.getWorldHeight()/2, 0, Align.center, true);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }
}
