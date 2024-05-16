package com.kosmik.nightwalker;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;

public class ScreenMenu implements Screen {
     NightWalker nightWalker;
     Texture img;
     SpriteBatch batch;
     OrthographicCamera camera;
     BitmapFont font;


    public ScreenMenu(NightWalker nightWalker) {
        this.nightWalker = nightWalker;

        batch = nightWalker.batch;
        camera = nightWalker.camera;
        font=nightWalker.font;

        img = new Texture("ScreenMenu.png");


    }

    @Override
    public void show() {

    }

    @Override
    public void render(float v) {
        if(Gdx.input.isTouched()){
            nightWalker.setScreen(nightWalker.screenGame);
        }
        ScreenUtils.clear(1, 0, 0, 1);
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(img, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        font.draw(batch,"hello",0,Gdx.graphics.getHeight()/2, Gdx.graphics.getWidth(), Align.center, true);
        batch.end();
    }

    @Override
    public void resize(int i, int i1) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        img.dispose();
    }
}
