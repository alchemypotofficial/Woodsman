package com.alchemy.woodsman;

import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		Graphics.DisplayMode displayMode = Lwjgl3ApplicationConfiguration.getDisplayMode();

		config.setForegroundFPS(120);
		config.setDecorated(false);
		config.setWindowedMode(displayMode.width, displayMode.height);
		config.useVsync(true);
		config.setTitle("Woodsman");
		config.setWindowIcon("sprites/system/System_Icon.png");
		new Lwjgl3Application(new Game(), config);
	}
}
