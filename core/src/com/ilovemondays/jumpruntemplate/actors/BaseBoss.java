package com.ilovemondays.jumpruntemplate.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.ilovemondays.jumpruntemplate.conf.Defines;
import com.ilovemondays.jumpruntemplate.utils.ActorManager;
import com.ilovemondays.jumpruntemplate.utils.SpriteAnimation;

/**
 * Created by Matthias on 13.04.2017.
 */
public class BaseBoss extends GameObject {
    protected int hitpoints;
    protected String name;
    protected Defines.Actors actorType;

    protected float stateTime = 0f;
    protected Animation spriteAnimation;
    protected Animation actAnimation;
    protected TextureRegion currentFrame;

    // SHADER TEST
    private ShaderProgram shader;
    private FileHandle fragmentShader, vertexShader;

    public BaseBoss() {
        actorType = Defines.Actors.BOSS;
        spriteAnimation = SpriteAnimation.create("default.jpg", 1, 1, 1);
        actAnimation = spriteAnimation;
        currentFrame = actAnimation.getKeyFrame(stateTime, true);

        actorManager = ActorManager.getInstance();
        actorManager.addEnemy(this);

        // SHADER TEST
        vertexShader = Gdx.files.internal("shader/vertex.glsl");
        fragmentShader = Gdx.files.internal("shader/fragment.glsl");
        shader = new ShaderProgram(vertexShader, fragmentShader);
    }

    @Override
    public void draw(Batch batch, float alpha){
        batch.draw(currentFrame,this.getX(),getY());
    }

    public void update() {
        stateTime += Gdx.graphics.getDeltaTime();
        currentFrame = actAnimation.getKeyFrame(stateTime, true);
        updateBounds();
    }

    public Defines.Actors getActorType() {
        return actorType;
    }

    public void getDamage(int power) {
        hitpoints -= power;
        System.out.println("FIST: Got me, remaining HP = "+hitpoints);
    }
}
