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
import com.badlogic.gdx.utils.TimeUtils;


public class ScreenGame implements Screen {

    private OrthographicCamera camera;
    private SpriteBatch batch;
    private NightWalker nightWalker;
    private NightWalkerObject nightWalkerObject;
    private Array<NightBombObject> nightbombobject=new Array<>();

    private long timeSpawnLastEnemy, timeSpawnEnemyInterval = 15000;
    private BitmapFont font;


    public ScreenGame(NightWalker nightWalker) {
        this.nightWalker=nightWalker;
        batch = nightWalker.batch;
        camera = nightWalker.camera;
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        font=nightWalker.font;

        Texture texture = new Texture("NightWalker.png");
        nightWalkerObject = new NightWalkerObject(texture, Gdx.graphics.getWidth() / 2 - texture.getWidth() / 2,
                Gdx.graphics.getHeight() / 2 - texture.getHeight() / 2,camera,batch);


    }
    @Override
    public void show() {

    }

    @Override
    public void render(float v) {

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glClearColor(0.7f,0.875f,0.957f, 1);


        camera.position.set(nightWalkerObject.getX() + nightWalkerObject.sprite.getWidth() / 2, nightWalkerObject.getY() + nightWalkerObject.sprite.getHeight() / 2, 0);
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        nightWalkerObject.update(batch);
        if (!nightWalkerObject.isGameOver){
            if (nightWalkerObject.isDeath==true){
                nightWalkerObject.isGameOver=true;
            }
            spawnEnemy();

            nightWalkerObject.handleInput();
            for (int i=0;i<nightbombobject.size;i++){
                nightWalkerObject.death(nightbombobject.get(i));
                nightbombobject.get(i).update(batch);
                for (int d=0;d<nightbombobject.size;d++)
                {
                    if(d>i || d<i){nightbombobject.get(i).death(nightbombobject.get(d));}
                }
                nightbombobject.get(i).moveToenemy(nightWalkerObject.getX() + nightWalkerObject.sprite.getWidth() / 2, nightWalkerObject.getY() + nightWalkerObject.sprite.getHeight() / 2);
            }
        } else {



            if(Gdx.input.justTouched()){
                nightbombobject.clear();
                timeSpawnLastEnemy = 15000;
                timeSpawnEnemyInterval = 15000;
                nightWalkerObject.isGameOver=false;
                nightWalkerObject.isDeath=false;
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
            timeSpawnLastEnemy = TimeUtils.millis();
            timeSpawnEnemyInterval-=1;
            nightbombobject.add(new NightBombObject(new Texture("Bomb.png"), nightWalkerObject.getX()-500, nightWalkerObject.getY()+ MathUtils.random(-500,500), batch));

        }
    }
    @Override
    public void dispose() {
        batch.dispose();
        nightWalkerObject.dispose();

    }
}
