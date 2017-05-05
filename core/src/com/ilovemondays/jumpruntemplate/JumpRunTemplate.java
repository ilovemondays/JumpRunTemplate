package com.ilovemondays.jumpruntemplate;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.ilovemondays.jumpruntemplate.actors.Player;
import com.ilovemondays.jumpruntemplate.actors.boss.Fist;
import com.ilovemondays.jumpruntemplate.utils.ControllerMap;
import com.ilovemondays.jumpruntemplate.utils.MyViewport;

public class JumpRunTemplate extends ApplicationAdapter {

	private Player player;
	private Stage stage;
	private Controller controller;
	private Array<Controller> controllers;
	private OrthographicCamera camera;
	private MyViewport viewport;
	private Texture background;
    private ControllerMap input;
    private Fist fist;
	private Music music;

	private FrameBuffer frameBufferBrightColors, frameBufferBlur;
	private TextureRegion bufferTextureBrightColors, bufferTextureBlur;
	// SHADER TEST
	private ShaderProgram shader, blurHShader, blurVShader;
	private FileHandle fragmentShader, vertexShader, blurHFragmentShader, blurVFragmentShader;

	@Override
	public void create () {
		player = new Player(30, 50);
		stage = new Stage();
        fist = new Fist(player);
		stage.addActor(player);
		stage.addActor(fist);
		camera = new OrthographicCamera(720, 450);
		camera.translate(720/2, 450/2);
		viewport = new MyViewport();
		viewport.setScreenSize(720, 450);
		viewport.setCamera(camera);
		stage.setViewport(viewport);

		frameBufferBrightColors = new FrameBuffer(Pixmap.Format.RGBA8888, 720, 450, false);
		frameBufferBlur = new FrameBuffer(Pixmap.Format.RGBA8888, 720, 450, false);
		bufferTextureBrightColors = new TextureRegion(frameBufferBrightColors.getColorBufferTexture());
		bufferTextureBlur = new TextureRegion(frameBufferBlur.getColorBufferTexture());
		bufferTextureBrightColors.flip(false, true);

		// shader
		vertexShader = Gdx.files.internal("shader/vertex.glsl");
		fragmentShader = Gdx.files.internal("shader/getBrightColors.glsl");
		blurHFragmentShader = Gdx.files.internal("shader/blur-h.glsl");
		blurVFragmentShader = Gdx.files.internal("shader/blur-v.glsl");
		shader = new ShaderProgram(vertexShader, fragmentShader);
		blurHShader = new ShaderProgram(vertexShader, blurHFragmentShader);
		blurVShader = new ShaderProgram(vertexShader, blurVFragmentShader);

		background = new Texture(Gdx.files.internal("backgrounds/3.png"));
		Music music = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));
		// music.play();

		// Controller Setup
		controller = null;
		controllers = Controllers.getControllers();
		if(controllers.size == 0) {
			//Keine Controller vorhanden...
		} else {
			for(Controller con : controllers) {
				if(con.getName().contains("Xbox")) {
					controller = con;
				}
			}
		}
        input = new ControllerMap(controller);
	}

	private void renderFrameBuffer(FrameBuffer fb, ShaderProgram sh, Texture tex) {
		fb.begin();
		stage.getBatch().begin();
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.getBatch().setShader(sh);
		stage.getBatch().draw(tex, 0, 0, 720, 450);
		stage.getBatch().end();
		stage.getBatch().setShader(null);
		fb.end();
		stage.getCamera().update();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		playerUpdate();

        stage.getBatch().begin();
        stage.getBatch().draw(background, 0, 0, 720, 450);
		stage.getBatch().end();

		renderFrameBuffer(frameBufferBrightColors, shader, background);
		renderFrameBuffer(frameBufferBlur, blurHShader, bufferTextureBrightColors.getTexture());

		stage.getBatch().begin();
		stage.getBatch().enableBlending();
		stage.getBatch().setBlendFunction(GL20.GL_ONE, GL20.GL_ONE);
		stage.getBatch().setShader(blurVShader);
		stage.getBatch().draw(bufferTextureBlur, 0, 0, 720, 450);
		stage.getBatch().setShader(null);
		stage.getBatch().end();

		stage.draw();

	}

	@Override
	public void dispose () {
		stage.dispose();
	}

	/**
	 * @todo: auslagern in einen player controller
	 */
	private void playerUpdate() {

		// Move left and right
		if(input.isLeft()) {
			player.moveLeft();
		}
		if(input.isRight()) {
			player.moveRight();
		}
		if(input.isUp()) {
			player.setLookUp(true);
		} else {
			player.setLookUp(false);
		}

		// Not LEFT nor RIGHT OR Both at the same time :)
		if(!input.isLeft() && !input.isRight()) {
			player.setIdleAnimation();
		}

		// jump
		if(input.isJump()) {
			player.jump();
		} else {
			player.endJump();
		}

		// dash
		if(input.isDash() && input.isRight()) {
			player.dashRight();
		}
		if(input.isDash() && input.isLeft()) {
			player.dashLeft();
		}
		if(!input.isDash() || (!input.isLeft() && !input.isRight())) {
			player.setIsDashing(false);
		}

		// shoot
		if(input.isShoot()) {
			player.shoot(stage);
		}

		player.update();
	}
}
