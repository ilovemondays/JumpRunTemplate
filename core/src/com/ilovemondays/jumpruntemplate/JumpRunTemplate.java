package com.ilovemondays.jumpruntemplate;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.ilovemondays.jumpruntemplate.actors.Player;

public class JumpRunTemplate extends ApplicationAdapter {

	private Player player;
	private Stage stage;

	@Override
	public void create () {
		player = new Player(30, 50);
		stage = new Stage();
		stage.addActor(player);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		playerUpdate();
		stage.draw();
	}

	@Override
	public void dispose () {
		stage.dispose();
	}

	private void playerUpdate() {
		// Move left and right
		if(Gdx.input.isKeyPressed(Input.Keys.A)) {
			player.moveLeft();
		}
		if(Gdx.input.isKeyPressed(Input.Keys.D)) {
			player.moveRight();
		}
		if(Gdx.input.isKeyPressed(Input.Keys.W)) {
			player.setLookUp(true);
		} else {
			player.setLookUp(false);
		}

		// jump
		if(Gdx.input.isKeyPressed(Input.Keys.K)) {
			player.jump();
		} else {
			player.endJump();
		}

		// shoot
		if(Gdx.input.isKeyPressed(Input.Keys.L)) {
			player.shoot(stage);
		}

		player.update();
	}
}
