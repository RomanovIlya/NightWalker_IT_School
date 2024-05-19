package com.kosmik.nightwalker;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;

public class NightObject  {
    protected Sprite sprite;
    private float targetRotation;
    protected float moveX;
    protected float moveY;
    private Batch batch;
    protected Boolean isDeath=false;

    private Animation<TextureRegion> explosionAnimation;
    private float stateTime;


    public NightObject(Texture texture, float x, float y,Batch batch) {
        sprite = new Sprite(texture);
        sprite.setPosition(x, y);
        this.batch=batch;
        Texture explosionSheet = new Texture("Exploed.png"); // Загрузите изображение анимации взрыва
        TextureRegion[][] explosionFrames = TextureRegion.split(explosionSheet, 738, 665);
        TextureRegion[] animationFrames = new TextureRegion[explosionFrames.length * explosionFrames[0].length];
        int index = 0;
        for (int i = 0; i < explosionFrames.length; i++) {
            for (int j = 0; j < explosionFrames[i].length; j++) {
                animationFrames[index++] = explosionFrames[i][j];
            }
        }
        float frameDuration = 0.1f;
        explosionAnimation = new Animation<>(frameDuration, animationFrames);
        stateTime = 0;

    }

    public void update(SpriteBatch batch) {
        if (!isDeath){
            batch.begin();
            sprite.draw(batch);
            batch.end();
            sprite.translate(moveX, moveY);
        }else {
            stateTime += Gdx.graphics.getDeltaTime();
            TextureRegion currentFrame = explosionAnimation.getKeyFrame(stateTime, false);
            batch.begin();
            batch.draw(currentFrame, sprite.getX()-sprite.getWidth()/2, sprite.getY()-sprite.getHeight()/2, 768, 665);
            batch.end();


        }

    }
    private void rotateTowardsTarget(float rotationSpeed) {
        float currentRotation = sprite.getRotation();
        float rotationDelta = targetRotation - currentRotation;

        rotationDelta = (rotationDelta + 180) % 360 - 180;

        float maxRotationStep = rotationSpeed * Gdx.graphics.getDeltaTime();
        float rotationStep = MathUtils.clamp(rotationDelta, -maxRotationStep, maxRotationStep);

        sprite.rotate(rotationStep);
    }
    public void moveTo(Vector3 targetPos,float objectSpeed,float rotationSpeed) {

            targetRotation = MathUtils.atan2(targetPos.y - (sprite.getY() + sprite.getHeight() / 2),
                    targetPos.x - (sprite.getX() + sprite.getWidth() / 2)) * MathUtils.radiansToDegrees;
            rotateTowardsTarget(rotationSpeed);
            float rotationRadians = MathUtils.degreesToRadians * sprite.getRotation();
            moveX = MathUtils.cos(rotationRadians) * objectSpeed * Gdx.graphics.getDeltaTime();
            moveY = MathUtils.sin(rotationRadians) * objectSpeed * Gdx.graphics.getDeltaTime();


    }
    public float getX(){
        return sprite.getX();
    }
    public float getY(){
        return sprite.getY();
    }
    public boolean death(NightObject o)  {
        if(Math.abs(getX()-o.getX()) < sprite.getWidth()/3 + o.sprite.getWidth()/2 & Math.abs(getY()-o.getY()) < sprite.getHeight()/3 + o.sprite.getHeight()/3 & !o.isDeath){
            isDeath=true;
            o.isDeath=true;
            return isDeath;
        }
        return false;
    }
    public void dispose() {

    }
}
