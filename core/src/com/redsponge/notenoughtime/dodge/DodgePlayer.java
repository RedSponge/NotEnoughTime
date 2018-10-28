package com.redsponge.notenoughtime.dodge;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.redsponge.notenoughtime.screen.DodgeScreen;

public class DodgePlayer {

    private Vector2 pos, vel;
    private float size, speed;
    private DodgeScreen screen;

    public DodgePlayer(DodgeScreen screen) {
        this.screen = screen;
        pos = new Vector2(250, 250);
        vel = new Vector2();
        size = 20;
        speed = 1;
    }

    public void tick(float delta) {
        vel.set(0, 0);
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
        pos.add(vel);
        if(pos.x < 0 || pos.x + size > 500 || pos.y < 0 || pos.y + size > 500) {
            screen.gameOver();
        }
        Rectangle me = new Rectangle(pos.x, pos.y, size, size);
        for (DodgeEnemy enemy : screen.getEnemies()) {
            if(me.overlaps(enemy.asRect())) screen.gameOver();
        }
    }

    public void render(SpriteBatch batch, ShapeRenderer renderer) {
        renderer.begin(ShapeType.Filled);
        renderer.setColor(Color.GREEN);
        renderer.rect(pos.x, pos.y, size, size);
        renderer.end();
    }

}
