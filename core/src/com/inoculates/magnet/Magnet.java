
package com.inoculates.magnet;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Magnet {
	Rectangle bounds = new Rectangle();
    boolean sign = false;
    Vector2 pos = new Vector2();
    Map map;

    public Magnet(float x, float y, boolean z, Map map) {
        this.pos.set(x, y);
        this.bounds.x = x + 0.4f;
        this.bounds.y = y + 0.4f;
        this.bounds.width = 0.6f;
        this.bounds.height = 0.6f;
        sign = z;
        this.map = map;
	}

        public boolean getSign()
    {
        return sign;
    }

        public void changeSign()
    {
        sign = !sign;
    }
}
