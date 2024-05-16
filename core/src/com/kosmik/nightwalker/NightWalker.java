package com.kosmik.nightwalker;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.awt.*;

public class NightWalker extends Game {
	SpriteBatch batch;
	OrthographicCamera camera;

	ScreenMenu screenMenu;
	ScreenGame screenGame;
	BitmapFont font;


	@Override
	public void create () {
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		font=new BitmapFont(Gdx.files.internal("Hez6e8q7ARTzF_f1ezpq_2Th.ttf.fnt"));
		screenMenu = new ScreenMenu(this);
		screenGame = new ScreenGame(this);
		setScreen(screenMenu);
	}



	@Override
	public void dispose () {
		batch.dispose();
		font.dispose();
	}
}
