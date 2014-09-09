
package com.inoculates.magnet;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.Gdx;

public class Ball {
	static final int WAITING = 0;
    static final int SHOT = 1;
    static float VELOCITY = 4;
    static final double ACCELERATION = 0.5;

    Map map;
	float stateTime = 0;
	int state = WAITING;
	Vector2 startPos = new Vector2();
    //Position of the ball
	Vector2 pos = new Vector2();
    //Velocity of the ball
	Vector2 vel = new Vector2();
	Rectangle bounds = new Rectangle();
    float angle;

	public Ball(Map map, float x, float y) {
        //Sets the starting position to the shooter with displacement, as well as the velocity
		this.map = map;
		this.startPos.set(x, y);
		this.pos.set(x, y);
        bounds.width = 0.6f;
        bounds.height = 0.8f;
        bounds.x = pos.x + 0.2f;
        bounds.y = pos.y;
		this.vel.set(-VELOCITY, 0);
	}

	public void update (float deltaTime) {
        if (state == SHOT) {
            //Moves the ball by adding the velocity to each of the position components
            pos.add(vel.x * deltaTime, vel.y * deltaTime);
			bounds.x = pos.x + 0.2f;
			bounds.y = pos.y + 0.2f;
            //Rotates the ball by increasing the angle every time the ball is updated
            angle += 10;
		}

        if (pos.x - map.shooter.pos.x > 12f || pos.x - map.shooter.pos.x < -12f
                || pos.y - map.shooter.pos.y > 12f || pos.y - map.shooter.pos.y < -12f)
        {
            //If the ball wanders off bounds, it will be returned to the shooter
            state = WAITING;
        }

		if (state == WAITING) {
				stateTime = 0;
				pos.set(startPos);
				bounds.x = pos.x + 0.2f;
				bounds.y = pos.y + 0.2f;
                //If the ball is not shot, it will remain near the shooter
		}

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
                //Obtains the angle between the ball and the magnet by using the arctangent of the distances
                angle = (float) (Math.atan2(pos.y - magnet.pos.y, pos.x - magnet.pos.x));
                //Sets the acceleration of the ball in accordance with the distance and angle
                acceleration.set((float) (Math.cos(angle)), (float) (Math.sin(angle))).nor().scl((float)
                        (ACCELERATION * 0.2 * (1/distance)));
                if (!magnet.getSign()) {
                    //If the magnet is positive, the velocity will orient itself towards the magnet
                    vel.add(acceleration.x, acceleration.y);
                }
                else
                {
                    if (distance < 0.1f)
                        return;
                    //If the magnet is negative, the velocity will orient itself away from the magnet
                    else vel.sub(acceleration);
                }
            }
        }
            stateTime += deltaTime;
    }

    public void processKeys () {
        if (state == WAITING)
        {
            //If the ball is waiting, it will be shot
                float velocityX;
                float velocityY;
                state = SHOT;
                velocityX = Gdx.input.getX();
                velocityY = Gdx.input.getY();
            //Sets the ball's velocity
                vel.set(velocityX - map.shooter.pos.x, map.shooter.pos.y - velocityY).nor().scl(VELOCITY);
        }
        else if (state == SHOT)
        {
            //If the ball is shot, it will return to the shooter
            state = WAITING;
        }
    }

    //Detects collision with bumpers
    public void checkHit(float bVelocity, double angle)
    {
            float velocity;
            vel.nor().scl(1/VELOCITY);
            velocity = VELOCITY + bVelocity;
            VELOCITY = velocity / 2;
            //Sets the velocity to half of the original, with a reversed angle using trigonometry
            vel.set((float) -Math.cos(angle), (float) -Math.sin(angle)).nor().scl(velocity / 2);
    }
}
