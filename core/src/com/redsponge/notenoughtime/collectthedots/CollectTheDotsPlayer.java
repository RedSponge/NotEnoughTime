package com.redsponge.notenoughtime.collectthedots;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
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
    }

    public void render(SpriteBatch batch, ShapeRenderer shapeRenderer) {
        shapeRenderer.begin(ShapeType.Filled);
        shapeRenderer.setColor(Color.YELLOW);
        shapeRenderer.rect(pos.x, pos.y, size, size);
        shapeRenderer.end();
    }

}
