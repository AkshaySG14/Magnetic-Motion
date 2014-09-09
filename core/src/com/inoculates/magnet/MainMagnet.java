
package com.inoculates.magnet;

import com.inoculates.magnet.screens.GameScreen;
import com.badlogic.gdx.Game;

public class MainMagnet extends Game {
	@Override
	public void create () {
		setScreen(new GameScreen(this));
	}
}
