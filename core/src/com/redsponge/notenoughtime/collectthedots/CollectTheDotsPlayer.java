package com.redsponge.notenoughtime.collectthedots;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.redsponge.notenoughtime.screen.CollectTheDotsScreen;

public class CollectTheDotsPlayer {


    private CollectTheDotsScreen screen;
    private Vector2 pos, vel;
    private int size;
    private int speed;

    public CollectTheDotsPlayer(CollectTheDotsScreen screen) {
        this.screen = screen;
        pos = new Vector2(250, 250);
        vel = new Vector2();
        size = 20;
        speed = 10;
    }

    public void tick(float delta) {
        vel.scl(0.9f);
        if(Gdx.input.isKeyPressed(Keys.UP)) {
            vel.add(0, speed);
        }
        if(Gdx.input.isKeyPressed(Keys.DOWN)) {
            vel.add(0, -speed);
        }
        if(Gdx.input.isKeyPressed(Keys.RIGHT)) {
            vel.add(speed, 0);
        }
        if(Gdx.input.isKeyPressed(Keys.LEFT)) {
            vel.add(-speed, 0);
        }
        pos.mulAdd(vel, delta*2);

        Rectangle me = new Rectangle(pos.x, pos.y, size, size);
        for (Vector2 dot : screen.getDots()) {
            Rectangle d = new Rectangle(dot.x, dot.y, 10, 10);
            if(me.overlaps(d)) {
                screen.score+=10;
                screen.getDots().removeValue(dot, true);
            }
        }

        if(pos.x < 0) {
            pos.x = 0;
            vel.x = 0;
        }
        if(pos.x + size > screen.getViewport().getWorldWidth()) {
            pos.x = screen.getViewport().getWorldWidth() - size;
            vel.x = 0;
        }
        if(pos.y < 0) {
            pos.y = 0;
            vel.y = 0;
        }
        if(pos.y + size > screen.getViewport().getWorldHeight()) {
            pos.y = screen.getViewport().getWorldHeight() - size;
            vel.y = 0;
        }
    }

    public void render(SpriteBatch batch, ShapeRenderer shapeRenderer) {
        shapeRenderer.begin(ShapeType.Filled);
        shapeRenderer.setColor(Color.GREEN);
        shapeRenderer.rect(pos.x, pos.y, size, size);
        shapeRenderer.end();
    }

}
