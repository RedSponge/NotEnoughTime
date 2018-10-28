package com.redsponge.notenoughtime.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.utils.DelayedRemovalArray;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.redsponge.notenoughtime.dodge.DodgeEnemy;
import com.redsponge.notenoughtime.dodge.DodgePlayer;

import java.util.Collection;

public class DodgeScreen extends GameScreen {

    private DodgePlayer player;
    private DelayedRemovalArray<DodgeEnemy> enemies;
    private FitViewport viewport;
    private long startTime;
    private int counter;

    @Override
    public void setup() {
        super.setup();
        viewport = new FitViewport(500, 500);
    }

    @Override
    public void gameStarted() {
        player = new DodgePlayer(this);
        enemies = new DelayedRemovalArray<DodgeEnemy>();
        startTime = TimeUtils.nanoTime();
    }

    @Override
    public void tickGame(float delta) {
        player.tick(delta);
        for (DodgeEnemy enemy : enemies) {
            enemy.tick(delta);
        }
        counter++;
        if(counter % 30 == 0) {
            enemies.add(new DodgeEnemy(this));
        }
    }

    @Override
    public void renderGame() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);
        renderer.setProjectionMatrix(viewport.getCamera().combined);
        player.render(batch, renderer);

        for (DodgeEnemy enemy : enemies) {
            enemy.render(batch, renderer);
        }

        renderer.begin(ShapeType.Line);
        renderer.setColor(Color.RED);
        Gdx.gl.glLineWidth(2);
        renderer.rect(1, 1, viewport.getWorldWidth()-2, viewport.getWorldHeight()-2);
        renderer.end();
        Gdx.gl.glLineWidth(1);

        batch.begin();
        instructionsFont.setColor(Color.WHITE);
        instructionsFont.draw(batch, "Seconds Survived:" + (int)((TimeUtils.nanoTime()-startTime)/1000000000f), 10, viewport.getWorldHeight() - 10);
        batch.end();
    }

    @Override
    public String getTitle() {
        return "Dodge!";
    }

    @Override
    public Color getTitleColor() {
        return Color.RED;
    }

    @Override
    public String[] getInstructions() {
        return new String[] {"Dodge anything that comes at you!", "Survive for the longest time you can!","","Oh yea you can't touch the edges as well..","","Use arrows to move"};
    }

    @Override
    public Color getInstructionsBackgroundColor() {
        return new Color(0.5f, 0, 0, 1);
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        viewport.update(width, height, true);
    }

    public void gameOver() {
        backToMenu((int)((TimeUtils.nanoTime() - startTime) / 1000000000f));
    }

    public FitViewport getViewport() {
        return viewport;
    }

    public DelayedRemovalArray<DodgeEnemy> getEnemies() {
        return enemies;
    }
}
