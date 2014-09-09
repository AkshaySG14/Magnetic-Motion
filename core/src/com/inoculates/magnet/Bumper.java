
package com.inoculates.magnet;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Bumper {
	static final int FLYING = 0;
	static float VELOCITY = 6;
    static final double ACCELERATION = 0.5;
    float angle = 0;

	Map map;
	float stateTime = 0;
	int state = FLYING;
	Vector2 startPos = new Vector2();
	Vector2 pos = new Vector2();
	Vector2 vel = new Vector2();
	Rectangle bounds = new Rectangle();

	public Bumper(Map map, float x, float y) {
		this.map = map;
		this.startPos.set(x, y);
		this.pos.set(x, y);
		this.bounds.x = x + 0.2f;
		this.bounds.y = y + 0.2f;
		this.bounds.width = 0.6f;
		this.bounds.height = 0.6f;
	}

	public void update (float deltaTime) {
        //Deletes the bumper if it exits the map bounds
        if (pos.x - map.shooter.pos.x > 12f || pos.x - map.shooter.pos.x < -12f
                || pos.y - map.shooter.pos.y > 12f || pos.y - map.shooter.pos.y < -12f)
        {
            map.bumpers.removeValue(this, true);
        }

        checkHit();
        //Moves the bumper by adding the velocity to the position
        pos.add(vel.x * deltaTime, vel.y * deltaTime);
        bounds.x = pos.x + 0.2f;
        bounds.y = pos.y + 0.2f;
        //Rotates the bumper if it is moving
        if (vel.x != 0 && vel.y != 0)
            angle += 10;

        for (int i = 0; i < map.magnets.size; i ++) {
            Magnet magnet = map.magnets.get(i);
            double distX;
            double distY;
            Vector2 acceleration = new Vector2(0, 0);
            distX = pos.x - magnet.pos.x;
            distY = pos.y - magnet.pos.y;
            double distance = Math.sqrt(Math.pow(distX, 2) + Math.pow(distY, 2));
            if (distance < 4f)
            {
                //We don't want the ball to glitch if it is in the center of the magnet, so we move it
                if (distance == 0)
                    distance += 5;
                //Identical code to the ball
                angle = (float) (Math.atan2(pos.y - magnet.pos.y, pos.x - magnet.pos.x));
                acceleration.set((float) (Math.cos(angle)), (float) (Math.sin(angle))).nor().scl((float)
                        (ACCELERATION * 0.2 * (1/distance)));
                if (!magnet.getSign()) {
                    vel.add(acceleration.x, acceleration.y);
                }
                else
                {
                    if (distance < 0.1f)
                        return;
                    else vel.sub(acceleration);
                }
            }        }
		stateTime += deltaTime;
	}

	private boolean checkHit () {
        float velocity;
        if (bounds.overlaps(map.ball.bounds) && map.ball.state == map.ball.SHOT) {
            angle = (float) (Math.atan2(pos.y - map.ball.pos.y, pos.x - map.ball.pos.x));
            vel.nor().scl(1/VELOCITY);
            velocity = VELOCITY + map.ball.VELOCITY;
            vel.set((float) Math.cos(angle), (float) Math.sin(angle)).nor().scl(velocity / 2);
            //Runs the method for collision of the ball
            map.ball.checkHit(VELOCITY, angle);
            return true;
        }
        return false;
    }

}
