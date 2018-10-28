package com.redsponge.notenoughtime.dodge;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.redsponge.notenoughtime.screen.DodgeScreen;

import java.util.Random;

public class DodgeEnemy  {

    private Vector2 pos, vel;
    private int size;
    private DodgeScreen screen;

    public DodgeEnemy(DodgeScreen screen) {
        this.screen = screen;
        pos = new Vector2();
        vel = new Vector2();
        size = 20;
        create(screen.getViewport().getWorldWidth(), screen.getViewport().getWorldHeight());
    }

    private void create(float worldWidth, float worldHeight) {
        Random random = new Random();
        int dir = random.nextInt(4);
        vel.set(random.nextFloat()*3+2, random.nextFloat()*3+2);
        if(dir == 0) {
            pos.set(0, 0);
        } else if(dir == 1) {
            pos.set(worldWidth-size, 0);
            vel.scl(-1, 1);
        } else if(dir == 2) {
            pos.set(worldWidth - size, worldHeight - size);
            vel.scl(-1, -1);
        } else {
            pos.set(0, worldHeight - size);
            vel.set(1, -1);
        }
    }

    public void tick(float delta) {
        pos.add(vel);
        if(pos.x + size < 0 || pos.x > screen.getViewport().getWorldWidth()
        || pos.y + size < 0 || pos.y > screen.getViewport().getWorldHeight()
        ) screen.getEnemies().removeValue(this, true);
    }

    public void render(SpriteBatch batch, ShapeRenderer renderer) {
        renderer.begin(ShapeType.Filled);
        renderer.setColor(Color.RED);
        renderer.rect(pos.x, pos.y, size, size);
        renderer.end();
    }

    public Rectangle asRect() {
        return new Rectangle(pos.x, pos.y, size, size);
    }
}
