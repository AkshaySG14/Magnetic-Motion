
package com.inoculates.magnet;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.Gdx;

public class Shooter {
	Rectangle bounds = new Rectangle();
    float stateTime = 0;
    float angle = 0;
    Vector2 pos;

    public Shooter(float x, float y) {
		bounds.x = x;
		bounds.y = y;
		bounds.width = bounds.height = 1;
        pos = new Vector2(Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f);
    }

    public void update (float deltaTime) {
        angle = (float) (Math.atan2(Gdx.input.getX() - pos.x, Gdx.input.getY() - pos.y) * 180/Math.PI);
        stateTime += deltaTime;
    }
}
