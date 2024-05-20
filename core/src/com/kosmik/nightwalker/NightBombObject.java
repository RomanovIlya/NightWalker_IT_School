package com.kosmik.nightwalker;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector3;

public class NightBombObject extends NightObject {
    private Vector3 enemyPos;

    public NightBombObject(Texture texture, float x, float y, Batch batch) {
        super(texture,x, y,batch);
        enemyPos=new Vector3();

    }


    public void moveToenemy(float x,float y) {
        enemyPos.set(x, y, 0);
        moveTo(enemyPos,100,245);


    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
