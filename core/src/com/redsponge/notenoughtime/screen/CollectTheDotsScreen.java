package com.redsponge.notenoughtime.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.redsponge.notenoughtime.collectthedots.CollectTheDotsPlayer;

import java.util.List;

public class CollectTheDotsScreen extends GameScreen{

    private CollectTheDotsPlayer player;
    private FitViewport viewport;
    private List<Vector2> dots;
    // TODO: ADD THE DOTS AND MAKE THE COUNTER.

    @Override
    public void setup() {
        super.setup();
        viewport = new FitViewport(500, 500);
    }

    @Override
    public void gameStarted() {
        player = new CollectTheDotsPlayer(this);
    }

    @Override
    public void tickGame(float delta) {
        player.tick(delta);
    }

    @Override
    public void renderGame() {
        Gdx.gl.glClearColor(0,0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        viewport.apply();

        batch.setProjectionMatrix(viewport.getCamera().combined);
        renderer.setProjectionMatrix(viewport.getCamera().combined);

        player.render(batch, renderer);
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
}
