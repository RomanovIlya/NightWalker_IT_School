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
	float time;


	@Override
	public void create () {
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());


		font=new BitmapFont(Gdx.files.internal("Berlin.fnt"));
		font.getData().setScale(5f);
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
