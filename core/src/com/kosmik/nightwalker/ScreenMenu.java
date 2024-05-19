package com.kosmik.nightwalker;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;

public class ScreenMenu implements Screen {
     NightWalker nightWalker;
     Texture BackImg;
     Texture PlayImg;
     SpriteBatch batch;
     OrthographicCamera camera;
     BitmapFont font;
    private Array<NightClouds> nightClouds=new Array<>();
    private long timeSpawnLastClouds,timeSpawnCloudsInterval=4000;


    public ScreenMenu(NightWalker nightWalker) {
        this.nightWalker = nightWalker;

        batch = nightWalker.batch;
        camera = nightWalker.camera;
        font=nightWalker.font;

        PlayImg=new Texture("PlayButton.png");
        BackImg = new Texture("ScreenMenu.png");


    }

    @Override
    public void show() {

    }

    @Override
    public void render(float v) {
        camera.position.set(Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight()/2,0);
        camera.update();
        if(Gdx.input.isTouched()){
            if (Gdx.input.getX()>Gdx.graphics.getWidth()/2-128 & Gdx.input.getX()<Gdx.graphics.getWidth()/2+128 & Gdx.graphics.getHeight()/2>Gdx.input.getY() & Gdx.input.getY()>Gdx.graphics.getHeight()/2- 256) {
                nightWalker.setScreen(nightWalker.screenGame);
            }
        }
        ScreenUtils.clear(1, 0, 0, 1);
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(BackImg, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();
        if (nightClouds.size<15) {
            spawnClouds();
        }
        for (int i=0;i<nightClouds.size;i++){
            nightClouds.get(i).update();
            if (nightClouds.get(i).outOfvision(0,Gdx.graphics.getHeight()/2)){
                nightClouds.get(i).dispose();
                nightClouds.removeIndex(i);
            }
        }
        batch.begin();
        batch.draw(PlayImg,Gdx.graphics.getWidth()/2-128,Gdx.graphics.getHeight()/2,256,256);
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
    public void spawnClouds() {
        if (TimeUtils.millis() > timeSpawnLastClouds+timeSpawnCloudsInterval) {
                timeSpawnLastClouds=TimeUtils.millis();
                nightClouds.add(new NightClouds(new Texture("Clouds.png"),MathUtils.random(Gdx.graphics.getWidth(),Gdx.graphics.getWidth()+500 ) ,  MathUtils.random(0, Gdx.graphics.getHeight()), batch));
        }
    }
    @Override
    public void dispose() {
        BackImg.dispose();
        PlayImg.dispose();
    }
}
