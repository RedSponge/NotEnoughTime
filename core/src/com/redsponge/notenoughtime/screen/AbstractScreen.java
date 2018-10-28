package com.redsponge.notenoughtime.screen;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public abstract class AbstractScreen extends ScreenAdapter {

    protected SpriteBatch batch;
    protected ShapeRenderer renderer;

    @Override
    public final void show() {
        batch = new SpriteBatch();
        renderer = new ShapeRenderer();
        setup();
    }

    public abstract void setup();

    @Override
    public final void render(float delta) {
        tick(delta);
        render();
    }

    public abstract void tick(float delta);

    public abstract void render();

    @Override
    public void dispose() {
        batch.dispose();
        renderer.dispose();
    }
}
