package com.ilovemondays.jumpruntemplate.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 * Created by Matthias on 06.05.2017.
 */
public class BloomStage extends Stage {

    // SHADER TEST
    private ShaderProgram shader, blurHShader, blurVShader;
    private FileHandle fragmentShader, vertexShader, blurHFragmentShader, blurVFragmentShader;

    private FrameBuffer frameBufferBrightColors, frameBufferBlur;
    private TextureRegion bufferTextureBrightColors, bufferTextureBlur;

    public BloomStage() {
        super();

        // shader
        vertexShader = Gdx.files.internal("shader/vertex.glsl");
        fragmentShader = Gdx.files.internal("shader/getBrightColors.glsl");
        blurHFragmentShader = Gdx.files.internal("shader/blur-h.glsl");
        blurVFragmentShader = Gdx.files.internal("shader/blur-v.glsl");
        shader = new ShaderProgram(vertexShader, fragmentShader);
        blurHShader = new ShaderProgram(vertexShader, blurHFragmentShader);
        blurVShader = new ShaderProgram(vertexShader, blurVFragmentShader);
        shader.begin();
        shader.setUniformf("u_bloomLevel", 0.6f);
        shader.end();

        frameBufferBrightColors = new FrameBuffer(Pixmap.Format.RGBA8888, 720, 450, false);
        frameBufferBlur = new FrameBuffer(Pixmap.Format.RGBA8888, 720, 450, false);
        bufferTextureBrightColors = new TextureRegion(frameBufferBrightColors.getColorBufferTexture());
        bufferTextureBlur = new TextureRegion(frameBufferBlur.getColorBufferTexture());
        bufferTextureBrightColors.flip(false, true);
    }

    private void renderFrameBuffer(FrameBuffer fb, ShaderProgram sh, Batch batch, Texture tex) {
        fb.begin();
        batch.begin();
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setShader(sh);
        if(tex == null) {
            getRoot().draw(batch, 1);
        } else {
            batch.draw(tex, 0, 0);
        }
        batch.end();
        batch.setShader(null);
        fb.end();
    }

    @Override
    public void draw () {
        Camera camera = getViewport().getCamera();
        camera.update();

        if (!getRoot ().isVisible()) return;

        Batch batch = getBatch();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        getRoot ().draw(batch, 1);
        batch.end();

        renderFrameBuffer(frameBufferBrightColors, shader, batch, null);
        renderFrameBuffer(frameBufferBlur, blurHShader, batch, bufferTextureBrightColors.getTexture());

        batch.begin();
        batch.setShader(blurVShader);
        batch.setBlendFunction(GL20.GL_ONE, GL20.GL_ONE);
        batch.draw(bufferTextureBlur, 0, 0, 720, 450);
        batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        batch.setShader(null);
        batch.end();

    }
}
