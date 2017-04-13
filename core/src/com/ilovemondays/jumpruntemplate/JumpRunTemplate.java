package com.ilovemondays.jumpruntemplate;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.ilovemondays.jumpruntemplate.actors.Player;
import com.ilovemondays.jumpruntemplate.conf.Defines;
import com.ilovemondays.jumpruntemplate.utils.TestViewport;

public class JumpRunTemplate extends ApplicationAdapter {

	private Player player;
	private Stage stage;
	private Controller controller;
	private Array<Controller> controllers;
	private OrthographicCamera camera;
	private TestViewport viewport;
	private Texture background;

	@Override
	public void create () {
		player = new Player(30, 50);
		stage = new Stage();
		stage.addActor(player);
		camera = new OrthographicCamera(720, 450);
		camera.translate(720/2, 450/2);
		viewport = new TestViewport();
		viewport.setScreenSize(720, 450);
		viewport.setCamera(camera);
		stage.setViewport(viewport);
		background = new Texture(Gdx.files.internal("backgrounds/1.png"));

		// Controller Setup
		controller = null;
		controllers = Controllers.getControllers();
		if(controllers.size == 0) {
			//Keine Controller vorhanden...
		} else {
			Controller pad = null;
			for(Controller con : controllers) {
				if(con.getName().contains("Xbox")) {
					controller = con;
				}
			}
		}
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		playerUpdate();
        stage.getBatch().begin();
        stage.getBatch().draw(background, 0, 0, 720, 450);
        stage.getBatch().end();
		stage.draw();
	}

	@Override
	public void dispose () {
		stage.dispose();
	}

	private void playerUpdate() {

		// Move left and right
		if((Gdx.input.isKeyPressed(Input.Keys.A) ||
                controller.getPov(Defines.Controller.POV) == Defines.Controller.BUTTON_DPAD_LEFT ||
                controller.getPov(Defines.Controller.POV) == Defines.Controller.BUTTON_DPAD_UP_LEFT) &&
                !Gdx.input.isKeyPressed(Input.Keys.D)) {
			player.moveLeft();
		}
		if((Gdx.input.isKeyPressed(Input.Keys.D) ||
                controller.getPov(Defines.Controller.POV) == Defines.Controller.BUTTON_DPAD_RIGHT ||
                controller.getPov(Defines.Controller.POV) == Defines.Controller.BUTTON_DPAD_UP_RIGHT) &&
                !Gdx.input.isKeyPressed(Input.Keys.A)) {
			player.moveRight();
		}
		if(Gdx.input.isKeyPressed(Input.Keys.W) ||
				controller.getPov(Defines.Controller.POV) == Defines.Controller.BUTTON_DPAD_UP ||
				controller.getPov(Defines.Controller.POV) == Defines.Controller.BUTTON_DPAD_UP_RIGHT ||
				controller.getPov(Defines.Controller.POV) == Defines.Controller.BUTTON_DPAD_UP_LEFT) {
			player.setLookUp(true);
		} else {
			player.setLookUp(false);
		}

		// Not LEFT nor RIGHT OR Both at the same time :)
		if(
            ((!Gdx.input.isKeyPressed(Input.Keys.A) && !Gdx.input.isKeyPressed(Input.Keys.D)) ||
            Gdx.input.isKeyPressed(Input.Keys.A) && Gdx.input.isKeyPressed(Input.Keys.D)) &&
            (controller.getPov(Defines.Controller.POV) == Defines.Controller.BUTTON_DPAD_CENTER)
            ) {
			player.setIdleAnimation();
		}

		// jump
		if(Gdx.input.isKeyPressed(Input.Keys.K) || controller.getButton(Defines.Controller.BUTTON_A)) {
			player.jump();
		} else {
			player.endJump();
		}

		// shoot
		if(Gdx.input.isKeyPressed(Input.Keys.L) || controller.getButton(Defines.Controller.BUTTON_X)) {
			player.shoot(stage);
		}

		player.update();
	}
}
