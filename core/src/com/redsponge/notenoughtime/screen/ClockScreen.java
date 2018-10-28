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
import com.redsponge.notenoughtime.NotEnoughTime;

import java.util.Calendar;
import java.util.Date;

public class ClockScreen extends GameScreen {

    private BitmapFont font, dotty;
    private ExtendViewport viewport;

    @Override
    public void setup() {
        super.setup();
        FreeTypeFontGenerator gen = new FreeTypeFontGenerator(Gdx.files.internal("digital-7.ttf"));
        FreeTypeFontParameter param = new FreeTypeFontParameter();
        param.size = 128;
        font = gen.generateFont(param);
        gen.dispose();

        viewport = new ExtendViewport(500, 500);
    }

    @Override
    public void gameStarted() {

    }

    @Override
    public void tickGame(float delta) {
        if(Gdx.input.isKeyJustPressed(Keys.SPACE)) {
            NotEnoughTime.instance.setScreen(new MenuScreen());
        }
    }

    @Override
    public void renderGame() {
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        String second, minute, hour;
        second = "" + Calendar.getInstance().get(Calendar.SECOND);
        if(second.length() == 1) second = "0" + second;

        minute = "" + Calendar.getInstance().get(Calendar.MINUTE);
        if(minute.length() == 1) minute = "0" + minute;

        hour = "" + Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        if(hour.length() == 1) hour = "0" + hour;

        String text = hour + ":" + minute + ":" + second;
        GlyphLayout layout = new GlyphLayout(font, text);
        batch.begin();
        font.setColor(Color.GREEN);
        font.draw(batch, text, viewport.getWorldWidth() / 2 - layout.width / 2, viewport.getWorldHeight() / 2 + layout.height / 2);


        instructionsFont.setColor(Color.GRAY);
        GlyphLayout bottom = new GlyphLayout(instructionsFont, "Press [Space] To Return To Menu");
        instructionsFont.draw(batch, "Press [Space] To Return To Menu", viewport.getWorldWidth() / 2 - bottom.width / 2, 20);
        batch.end();

    }

    @Override
    public String getTitle() {
        return "Clock!";
    }

    @Override
    public Color getTitleColor() {
        return Color.GREEN;
    }

    @Override
    public String[] getInstructions() {
        return new String[] {"Not Exactly a Game...", "Eh.. who cares, you use it to tell the time"};
    }

    @Override
    public Color getInstructionsBackgroundColor() {
        return new Color(0.1f, 0.2f, 0.1f, 1);
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        viewport.update(width, height);
    }
}
