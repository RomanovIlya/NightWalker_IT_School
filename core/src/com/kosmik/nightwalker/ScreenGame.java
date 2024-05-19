package com.kosmik.nightwalker;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.TimeUtils;


public class ScreenGame implements Screen {

    private OrthographicCamera camera;
    private SpriteBatch batch;
    private NightWalker nightWalker;
    private NightWalkerObject nightWalkerObject;
    private Array<NightBombObject> nightbombobject=new Array<>();
    private Array<NightClouds> nightClouds=new Array<>();

    private long timeSpawnLastEnemy, timeSpawnEnemyInterval = 15000;
    private long timeSpawnLastClouds, timeSpawnCloudsInterval = 1000;
    private BitmapFont font;

    private float time;


    public ScreenGame(NightWalker nightWalker) {
        this.nightWalker=nightWalker;
        batch = nightWalker.batch;
        camera = nightWalker.camera;
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        font=nightWalker.font;
        time=nightWalker.time;

        Texture texture = new Texture("NightWalker.png");

        nightWalkerObject = new NightWalkerObject(texture, Gdx.graphics.getWidth() / 2 - texture.getWidth() / 2,
                Gdx.graphics.getHeight() / 2 - texture.getHeight() / 2,camera,batch);
        nightWalkerObject.creationTime = TimeUtils.millis();



    }
    @Override
    public void show() {
        nightbombobject.clear();
        nightClouds.clear();
        nightWalkerObject.sprite.setPosition(Gdx.graphics.getWidth() / 2 - nightWalkerObject.sprite.getWidth() / 2,
                Gdx.graphics.getHeight() / 2 - nightWalkerObject.sprite.getHeight() / 2);
        nightWalkerObject.sprite.setRotation(0);
        nightWalkerObject.moveX=0;
        nightWalkerObject.moveY=0;
        timeSpawnLastEnemy = 15000;
        timeSpawnEnemyInterval = 15000;
        nightWalkerObject.creationTime = TimeUtils.millis();
        nightWalkerObject.isGameOver = false;
        nightWalkerObject.isDeath = false;
    }

    @Override
    public void render(float v) {

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glClearColor(0.7f,0.875f,0.957f, 1);

        camera.viewportWidth = Gdx.graphics.getWidth() * 2;
        camera.viewportHeight = Gdx.graphics.getHeight() * 2;
        camera.position.set(nightWalkerObject.getX() + nightWalkerObject.sprite.getWidth() / 2, nightWalkerObject.getY() , 0);
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        nightWalkerObject.update(batch);
        if (!nightWalkerObject.isGameOver){
            time=  (TimeUtils.millis()-NightWalkerObject.creationTime)/1000.0f;
            if (nightWalkerObject.isDeath){
                nightWalkerObject.isGameOver=true;
            }
            spawnEnemy();
            spawnClouds();

            nightWalkerObject.handleInput();

        } else {

            batch.begin();

            font.getData().setScale(5f);
            font.draw(batch,"Your time:"+String.format("%.1f",time)+"s", nightWalkerObject.getX()-nightWalkerObject.sprite.getWidth(), nightWalkerObject.getY()+300, Gdx.graphics.getWidth()+250, Align.center, true);
            font.getData().setScale(3f);
            font.draw(batch,"Tap to restart",nightWalkerObject.getX()-nightWalkerObject.sprite.getWidth() ,nightWalkerObject.getY()-50, Gdx.graphics.getWidth()+250, Align.center, true);
            batch.end();

            if(Gdx.input.justTouched()){
                nightbombobject.clear();
                nightWalkerObject.sprite.setPosition(Gdx.graphics.getWidth() / 2 - nightWalkerObject.sprite.getWidth() / 2,
                        Gdx.graphics.getHeight() / 2 - nightWalkerObject.sprite.getHeight() / 2);
                nightWalkerObject.sprite.setRotation(0);
                nightWalkerObject.moveX=0;
                nightWalkerObject.moveY=0;
                timeSpawnLastEnemy = 15000;
                timeSpawnEnemyInterval = 15000;
                nightWalkerObject.creationTime = TimeUtils.millis();
                nightWalkerObject.isGameOver = false;
                nightWalkerObject.isDeath = false;

               }
        }
        for (int i=0;i<nightbombobject.size;i++){
            nightWalkerObject.death(nightbombobject.get(i));
            nightbombobject.get(i).update(batch);
            for (int d=0;d<nightbombobject.size;d++)
            {
                if(d>i || d<i){nightbombobject.get(i).death(nightbombobject.get(d));}
            }
            nightbombobject.get(i).moveToenemy(nightWalkerObject.getX() + nightWalkerObject.sprite.getWidth() / 2, nightWalkerObject.getY() + nightWalkerObject.sprite.getHeight() / 2);
            if (nightbombobject.get(i).isDeath){
                nightbombobject.get(i).dispose();
                nightbombobject.removeIndex(i);
            }
        }
        for(int i=0;i<nightClouds.size;i++){
            nightClouds.get(i).update();
            if(nightClouds.get(i).outOfvision(nightWalkerObject.getX(), nightWalkerObject.getY())){
                nightClouds.get(i).dispose();
                nightClouds.removeIndex(i);
            }
        }




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
    public void spawnEnemy(){
        if(TimeUtils.millis() > timeSpawnLastEnemy+timeSpawnEnemyInterval){
            float i=nightWalkerObject.getX()+MathUtils.random(-5000,5000);
            if (i>nightWalkerObject.getX()+999 || i<nightWalkerObject.getX()-999) {
                timeSpawnLastEnemy = TimeUtils.millis();
                timeSpawnEnemyInterval -= 1;
                nightbombobject.add(new NightBombObject(new Texture("Bomb.png"), i, nightWalkerObject.getY() + MathUtils.random(-5000, 5000), batch));
            }
            else {
                spawnEnemy();
            }
        }
    }
    public void spawnClouds() {
        if (TimeUtils.millis() > timeSpawnLastClouds+timeSpawnCloudsInterval) {
            float i = nightWalkerObject.getX() + MathUtils.random(-3200, 3200);
            if (i > nightWalkerObject.getX() +  1500 || i < nightWalkerObject.getX() - 1500) {
                timeSpawnLastClouds=TimeUtils.millis();
                nightClouds.add(new NightClouds(new Texture("Clouds.png"), i, nightWalkerObject.getY() + MathUtils.random(-3200, 3200), batch));
            }
            else {
                spawnClouds();
            }
        }
    }
    @Override
    public void dispose() {
        batch.dispose();
        nightWalkerObject.dispose();


    }
}
