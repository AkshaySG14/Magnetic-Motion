
package com.inoculates.magnet.screens;

import com.badlogic.gdx.Screen;
import com.inoculates.magnet.Map;
import com.inoculates.magnet.MapRenderer;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

public class GameScreen implements Screen {
    Game game;
	Map map;
	MapRenderer renderer;

	public GameScreen (Game game) {
		this.game = game;
	}

	@Override
	public void show () {
        //Create map and its corresponding renderer
		map = new Map();
		renderer = new MapRenderer(map);
	}

	@Override
	public void render (float delta) {
        //Updates the map and all objects, as well as setting the background to black using glClearColor
		delta = Math.min(0.06f, Gdx.graphics.getDeltaTime());
		map.update(delta);
		Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //Rendering all objects using renderer
		renderer.render(delta);
	}

	@Override
	public void hide () {
		Gdx.app.debug("MainMagnet", "dispose game screen");
		renderer.dispose();
	}

    @Override
    public void pause () {
    }

    @Override
    public void resume () {
    }

    @Override
    public void dispose () {
    }

    public void resize (int width, int height) {
    }
}
