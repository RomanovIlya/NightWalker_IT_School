package com.kosmik.nightwalker;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.TimeUtils;

public class NightWalkerObject extends NightObject {

    private Vector3 touchPos;

    private OrthographicCamera camera;

    protected Boolean isGameOver=false;
    protected static long creationTime;


    public NightWalkerObject(Texture texture, float x, float y, OrthographicCamera camera, Batch batch,Texture explosionSheet) {
        super(texture,x,y,batch,explosionSheet);
        touchPos = new Vector3();
        this.camera=camera;
    }

    @Override
    public void update(SpriteBatch batch) {
        super.update(batch);
    }

    public void handleInput() {
        if (Gdx.input.isTouched()) {
            float x = Gdx.input.getX();
            float y = Gdx.input.getY();
            touchPos.set(x, y, 0);
            touchPos = screenToWorld(touchPos);
            moveTo(touchPos,1000,300);
        }
    }

    private Vector3 screenToWorld(Vector3 screenPos) {
        camera.unproject(screenPos);
        return screenPos;
    }

    @Override
    public void dispose() {
        super.dispose();

    }
}

