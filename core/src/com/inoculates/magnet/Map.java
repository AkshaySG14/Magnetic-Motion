
package com.inoculates.magnet;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

public class Map {
    //A list of all objects in the demo
	public Ball ball = new Ball(this, Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f);
	Shooter shooter = new Shooter(Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f);
	Array<Bumper> bumpers = new Array<Bumper>();
    Array<Magnet> magnets = new Array<Magnet>();

    public Map () {
        //Setting up an input processor for mouse clicks
        Gdx.input.setInputProcessor(new InputProcessor() {
            public boolean touchDown(int x, int y, int pointer, int button) {
                if (button == Input.Buttons.RIGHT) {
                    //Method if a right mouse click occurs
                    processKeys1();
                    return true;
                }
                else if (button == Input.Buttons.MIDDLE) {
                    //Method if a middle mouse click occurs
                    processKeys2();
                    return true;
                }
                if (button == Input.Buttons.LEFT)
                {
                    //Method if a left mouse click occurs
                    ball.processKeys();
                    return true;
                }
                return false;
            }
            public boolean touchDragged(int x, int y, int z)
            {
                return true;
            }
            public boolean keyTyped(char x)
            {
                return true;
            }
            public boolean scrolled(int x)
            {
                return true;
            }
            public boolean keyDown(int x)
            {
                return true;
            }
            public boolean mouseMoved(int x, int y)
            {
                return true;
            }
            public boolean keyUp(int x)
            {
                return true;
            }
            public boolean touchUp(int x, int y, int z, int a)
            {
                return true;
            }
        });
    }

	public void update (float deltaTime) {
        //Updates all objects after method is caleld by screen rendering
		ball.update(deltaTime);
        shooter.update(deltaTime);

        for (int i = 0; i < bumpers.size; i++) {
			Bumper bumper = bumpers.get(i);
			bumper.update(deltaTime);
		}

	}

    //Creates a magnet
    private void processKeys1()
    {
        /* These two lines of code acquire the position of the mouse click in relation to the coordinate plane,
        not with regards to the camera */
        Vector3 vector = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        vector = MapRenderer.getUnbound(vector);

        for (int i = 0; i < magnets.size; i ++) {
                //If there is no magnet within a certain distance, a new one is generated
                    if (Math.sqrt(Math.pow((magnets.get(i).pos.x - vector.x), 2) + Math.pow(magnets.get(i)
                    .pos.y - vector.y, 2)) < 1f) {
                        //If there exists a magnet of a positive sign, it's sign is changed
                        if (magnets.get(i).getSign())
                        magnets.get(i).changeSign();
                        else
                        {
                            //If the magnet is already negative, it is removed
                            magnets.removeIndex(i);
                        }
                        return;
                    }
                }
        Magnet magnet = new Magnet(vector.x, vector.y, true, this);
        magnets.add(magnet);
    }

    //Creates a stationary bumper
    private void processKeys2()
    {
        Vector3 vector = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        vector = MapRenderer.getUnbound(vector);

        //If there exists no bumper within a certain distance, a new one is generated
        for (int i = 0; i < bumpers.size; i ++) {
            if (Math.sqrt(Math.pow((bumpers.get(i).pos.x - vector.x), 2) + Math.pow(bumpers.get(i)
                    .pos.y - vector.y, 2)) < 1f) {
                return;
            }
        }
        Bumper bumper = new Bumper(this, vector.x, vector.y);
        bumpers.add(bumper);
    }
}
