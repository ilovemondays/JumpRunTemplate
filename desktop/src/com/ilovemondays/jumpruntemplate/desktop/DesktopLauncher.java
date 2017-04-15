package com.ilovemondays.jumpruntemplate.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.ilovemondays.jumpruntemplate.JumpRunTemplate;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		/*
		config.width = 1650;
		config.height = 1050;
		config.fullscreen = true;
		*/

		new LwjglApplication(new JumpRunTemplate(), config);
	}
}
