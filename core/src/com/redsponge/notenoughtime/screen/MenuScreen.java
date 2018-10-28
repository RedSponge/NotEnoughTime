package com.redsponge.notenoughtime.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.redsponge.notenoughtime.NotEnoughTime;

public class MenuScreen extends AbstractScreen {


    private BitmapFont title, dotty;

    private ExtendViewport viewport;
    private int selectedIndex;
    @Override
    public void setup() {
        FreeTypeFontGenerator gen = new FreeTypeFontGenerator(Gdx.files.internal("FFFFORWA.TTF"));
        FreeTypeFontParameter parameter = new FreeTypeFontParameter();
        parameter.size = 32;
        title = gen.generateFont(parameter);
        gen.dispose();
        gen = new FreeTypeFontGenerator(Gdx.files.internal("dotty.ttf"));
        dotty = gen.generateFont(parameter);
        gen.dispose();

        viewport = new ExtendViewport(500, 500);
        selectedIndex = 0;
    }

    @Override
    public void tick(float delta) {
        if(Gdx.input.isKeyJustPressed(Keys.SPACE)) {
            try {
                NotEnoughTime.instance.setScreen(Games.values()[selectedIndex].gameClass.newInstance());
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        } if(Gdx.input.isKeyJustPressed(Keys.RIGHT)) {
            selectedIndex++;
        } if(Gdx.input.isKeyJustPressed(Keys.LEFT)) {
            selectedIndex--;
        } if(Gdx.input.isKeyJustPressed(Keys.UP)) {
            selectedIndex -= 3;
        } if(Gdx.input.isKeyJustPressed(Keys.DOWN)) {
            selectedIndex += 3;
        }
        if(selectedIndex > Games.values().length-1) selectedIndex = 0;
        if(selectedIndex < 0) selectedIndex = Games.values().length-1;
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);
        renderer.setProjectionMatrix(viewport.getCamera().combined);

        batch.begin();
        title.setColor(Color.GREEN);
        GlyphLayout titleLayout = new GlyphLayout(title, "Not Enough Time");
        title.draw(batch, "Not Enough Time", viewport.getWorldWidth()/2 - titleLayout.width / 2, viewport.getWorldHeight() / 4 * 3.5f);

        dotty.setColor(Color.LIGHT_GRAY);
        dotty.draw(batch, "By RedSponge\nMade for the 5th LibGDX jam\nCuz I didn't have enough time to make a \"REAL\" game", viewport.getWorldWidth()/2 - titleLayout.width/2, viewport.getWorldHeight() / 4 * 3.5f - 50);


        GlyphLayout help = new GlyphLayout(dotty,"Use arrows to move and [Space] to select");
        dotty.draw(batch, "Use arrows to move and [Space] to select", viewport.getWorldWidth() / 2 - help.width / 2, 20);

        batch.end();

        float gameWidth = 150;
        float gameHeight = 75;
        float widthMargin = 30;
        float heightMargin = 20;
        for (int i = 0; i < Games.values().length; i++) {
            Games game = Games.values()[i];
            float x = viewport.getWorldWidth() / 2 - gameWidth / 2;
            if(i % 3 == 0) x -= widthMargin + gameWidth;
            else if(i % 3 == 2) x += widthMargin + gameWidth;

            float y = viewport.getWorldHeight() / 2;
            y -= (heightMargin + gameHeight) * (i / 3);
            renderer.begin(ShapeType.Filled);
            renderer.setColor(game.background);
            if(selectedIndex == i) renderer.setColor(game.selectedBackground);

            renderer.rect(x, y, gameWidth, gameHeight);
            renderer.end();

            Gdx.gl.glLineWidth(2);
            renderer.begin(ShapeType.Line);
            renderer.setColor(game.foreground);
            if(selectedIndex == i) renderer.setColor(game.selectedForeground);
            renderer.rect(x, y, gameWidth, gameHeight);
            renderer.end();
            Gdx.gl.glLineWidth(1);

            batch.begin();
            title.getData().setScale(0.5f);
            GlyphLayout layout = new GlyphLayout(title, game.name);
            title.setColor(game.foreground);
            if(selectedIndex == i) title.setColor(game.selectedForeground);
            title.draw(batch, game.name, x + gameWidth / 2 - layout.width / 2, y + gameHeight / 2 + layout.height / 2);
            title.getData().setScale(1);
            if(selectedIndex == i) {
                dotty.setColor(Color.WHITE);
                dotty.getData().setScale(2);
                dotty.draw(batch, ">", x - widthMargin/2, y + heightMargin + gameHeight / 2);
                dotty.getData().setScale(1);
            }

            batch.end();
        }
    }

    @Override
    public void dispose() {
        super.dispose();
        title.dispose();
        dotty.dispose();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    enum Games {
        COLLECT_THE_DOTS("Collect The\nDots", CollectTheDotsScreen.class, Color.BLACK, Color.YELLOW, Color.DARK_GRAY, Color.ORANGE),
        CLOCK("Clock", ClockScreen.class, new Color(0.1f, 0.2f, 0.1f, 1), Color.GREEN, Color.GREEN, new Color(0.3f, 0.6f, 0.3f, 1)),
        DODGE("Dodge", DodgeScreen.class, new Color(0.1f, 0, 0, 1), Color.RED, new Color(0.8f, 0, 0, 1), Color.RED);


        private String name;
        private Class<? extends GameScreen> gameClass;
        private Color background, foreground, selectedBackground, selectedForeground;

        Games(String name, Class<? extends GameScreen> gameClass, Color background, Color foreground, Color selectedBackground, Color selectedForeground) {
            this.name = name;
            this.gameClass = gameClass;
            this.background = background;
            this.foreground = foreground;
            this.selectedBackground = selectedBackground;
            this.selectedForeground = selectedForeground;
        }
    }
}
