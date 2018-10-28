package com.redsponge.notenoughtime.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.DelayedRemovalArray;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.redsponge.notenoughtime.NotEnoughTime;
import com.redsponge.notenoughtime.collectthedots.CollectTheDotsPlayer;

import java.util.Random;

public class CollectTheDotsScreen extends GameScreen{

    public int score;
    private CollectTheDotsPlayer player;
    private FitViewport viewport;
    private DelayedRemovalArray<Vector2> dots;
    private int counter;
    private BitmapFont countersFont;
    private long start;
    // TODO: ADD THE DOTS AND MAKE THE COUNTER.

    @Override
    public void setup() {
        super.setup();
        viewport = new FitViewport(500, 500);
        FreeTypeFontGenerator gen = new FreeTypeFontGenerator(Gdx.files.internal("dotty.ttf"));
        FreeTypeFontParameter param = new FreeTypeFontParameter();
        param.size = 32;
        countersFont = gen.generateFont(param);
        gen.dispose();
    }

    @Override
    public void gameStarted() {
        player = new CollectTheDotsPlayer(this);
        dots = new DelayedRemovalArray<Vector2>();
        counter = 0;
        score = 0;
        start = TimeUtils.nanoTime();
    }

    @Override
    public void tickGame(float delta) {
        player.tick(delta);
        counter++;
        if(counter % 60 == 0) {
           spawnNewDot();
        }
    }

    private void spawnNewDot() {
        Random random = new Random();
        dots.add(new Vector2(random.nextInt(490), random.nextInt(490))); // Dot Size Is 10
    }

    @Override
    public void renderGame() {
        Gdx.gl.glClearColor(0,0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        viewport.apply();

        batch.setProjectionMatrix(viewport.getCamera().combined);
        renderer.setProjectionMatrix(viewport.getCamera().combined);

        player.render(batch, renderer);

        renderer.begin(ShapeType.Filled);
        renderer.setColor(Color.YELLOW);
        for (Vector2 dot : dots) {
            renderer.rect(dot.x, dot.y, 10, 10);
        }
        renderer.end();

        float timePassed = (TimeUtils.nanoTime() - start) / 1000000000f;
        batch.begin();
        countersFont.draw(batch, "Score: " + score, 10, viewport.getWorldHeight());
        countersFont.draw(batch, "Time Left: " + (int)(60-timePassed), 10, viewport.getWorldHeight()-20);
        batch.end();
        if(timePassed > 60) {
            backToMenu(score);
        }
    }

    @Override
    public String getTitle() {
        return "Collect The Dots!";
    }

    @Override
    public Color getTitleColor() {
        return Color.YELLOW;
    }

    @Override
    public String[] getInstructions() {
        return new String[]{"In this game, you need to collect as many yellow dots as you can in 60 seconds!", "Use Arrows to move!", "Good Luck!"};
    }

    @Override
    public Color getInstructionsBackgroundColor() {
        return Color.BROWN;
    }

    public void resize(int width, int height) {
        super.resize(width, height);
        viewport.update(width, height, true);
    }

    public DelayedRemovalArray<Vector2> getDots() {
        return dots;
    }

    @Override
    public void dispose() {
        super.dispose();
        countersFont.dispose();
    }

    public Viewport getViewport() {
        return viewport;
    }
}
