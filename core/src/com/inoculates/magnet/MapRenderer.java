
package com.inoculates.magnet;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteCache;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ImmediateModeRenderer20;
import com.badlogic.gdx.math.Vector3;

public class MapRenderer {
	Map map;
	static OrthographicCamera cam;
	SpriteBatch batch = new SpriteBatch(5460);
	ImmediateModeRenderer20 renderer = new ImmediateModeRenderer20(false, true, 0);
	int[][] blocks;
    TextureRegion bumper;
    TextureRegion ball;
    TextureRegion magnetPos;
    TextureRegion magnetNeg;
    TextureRegion shooter;
	FPSLogger fps = new FPSLogger();

	public MapRenderer (Map map) {
		this.map = map;
        //Generates a cam with a specific bounds and position
		this.cam = new OrthographicCamera(24, 16);
		this.cam.position.set(map.shooter.bounds.x, map.shooter.bounds.y, 0);
        //Creates a texture based off the Bob Image
        Texture grid = new Texture(Gdx.files.internal("data/bob.png"));
        TextureRegion[] split;
        //Creates all the images of the objects
        ball = new TextureRegion(new Texture(Gdx.files.internal("data/ball1.png")));
        bumper = new TextureRegion(new Texture(Gdx.files.internal("data/ball2.png")));
        magnetNeg = new TextureRegion(new Texture(Gdx.files.internal("data/electron.png")));
        magnetPos = new TextureRegion(new Texture(Gdx.files.internal("data/proton.png")));
        //Takes a piece of the grid texture to create the shooter
        split = new TextureRegion(grid).split(20, 20)[3];
        shooter = split[4];
    }

	float stateTime = 0;

    public static Vector3 getUnbound(Vector3 vector)
    {
        return cam.unproject(vector);
    }

    public void render (float deltaTime) {
		cam.update();
		Gdx.gl.glDisable(GL20.GL_BLEND);
		stateTime += deltaTime;
        //Set up the camera and render all the objects in the demo
		batch.setProjectionMatrix(cam.combined);
		batch.begin();
		renderBall();
        renderShooter();
        renderMagnets();
        renderBumpers();
        batch.end();

		fps.log();
	}

	private void renderBall () {
        batch.draw(this.ball, map.ball.pos.x, map.ball.pos.y, 0.5f, 0.5f, 1, 1, 1, 1, map.ball.angle);
	}

    private void renderBumpers () {
        for (int i = 0; i < map.bumpers.size; i++) {
            Bumper bumper = map.bumpers.get(i);
            if (bumper.state == bumper.FLYING) {
                batch.draw(this.bumper, bumper.pos.x, bumper.pos.y, 0.5f, 0.5f, 1, 1, 1, 1, bumper.angle);
            }
        }
    }

	private void renderShooter () {
            batch.draw(this.shooter, map.shooter.pos.x, map.shooter.pos.y, 0.5f, 0.5f, 1, 1, 1, 1, map.shooter.angle);
		}

    private void renderMagnets () {
        for (int i = 0; i < map.magnets.size; i++) {
            Magnet magnet = map.magnets.get(i);
            if (magnet.getSign()) {
                batch.draw(this.magnetPos, magnet.pos.x, magnet.pos.y, 0.5f, 0.5f, 1, 1, 1, 1, 180);
            }
            else
            {
                batch.draw(this.magnetNeg, magnet.pos.x, magnet.pos.y, 0.5f, 0.5f, 1, 1, 1, 1, 180);
            }
        }
    }


	public void dispose () {
		batch.dispose();
	}
}
