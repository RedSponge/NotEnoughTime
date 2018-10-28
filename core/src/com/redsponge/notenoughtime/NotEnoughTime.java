package com.redsponge.notenoughtime;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.redsponge.notenoughtime.screen.CollectTheDotsScreen;
import com.redsponge.notenoughtime.screen.IntroScreen;

public class NotEnoughTime extends Game {

	@Override
	public void create () {
		setScreen(new CollectTheDotsScreen());
	}

}
