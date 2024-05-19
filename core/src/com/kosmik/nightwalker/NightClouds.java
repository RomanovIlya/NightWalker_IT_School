package com.kosmik.nightwalker;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class NightClouds {
    protected Sprite sprite;
    private Batch batch;
    public NightClouds(Texture texture, float x, float y, Batch batch){
        sprite=new Sprite(texture);
        this.batch=batch;
        sprite.setPosition(x,y);
    }
    public void update(){
        batch.begin();
        sprite.draw(batch);
        batch.end();

        move();
    }
    public void move(){
        sprite.setPosition((float) (sprite.getX()-2), sprite.getY());

    }
    public boolean outOfvision(float x,float y){
        return sprite.getX()>x+6000 || sprite.getX()<x-6000 || sprite.getY()>y+6000 || sprite.getY()<y-6000;

    }
    public void dispose(){

    }
}
