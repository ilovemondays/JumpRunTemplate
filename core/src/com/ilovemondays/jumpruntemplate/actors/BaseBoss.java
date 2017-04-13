package com.ilovemondays.jumpruntemplate.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.ilovemondays.jumpruntemplate.utils.SpriteAnimation;

/**
 * Created by Matthias on 13.04.2017.
 */
public class BaseBoss extends Actor {
    protected int hitpoints;
    protected String name;

    float stateTime = 0f;
    protected Animation spriteAnimation;
    protected Animation actAnimation;
    TextureRegion currentFrame;

    public BaseBoss() {
        spriteAnimation = SpriteAnimation.create("default.jpg", 1, 1, 1);
        actAnimation = spriteAnimation;
    }

    @Override
    public void draw(Batch batch, float alpha){
        stateTime += Gdx.graphics.getDeltaTime();
        currentFrame = actAnimation.getKeyFrame(stateTime, true);
        batch.draw(currentFrame,this.getX(),getY());
        update();
    }

    public void update() {
    }
}
