package com.ilovemondays.jumpruntemplate.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.ilovemondays.jumpruntemplate.conf.Defines;
import com.ilovemondays.jumpruntemplate.utils.CollisionManager;
import com.ilovemondays.jumpruntemplate.utils.SpriteAnimation;

/**
 * Created by Matthias on 13.04.2017.
 */
public class BaseBoss extends GameObject {
    protected int hitpoints;
    protected String name;
    protected Defines.Actors actorType;

    float stateTime = 0f;
    protected Animation spriteAnimation;
    protected Animation actAnimation;
    TextureRegion currentFrame;

    public BaseBoss() {
        actorType = Defines.Actors.BOSS;
        spriteAnimation = SpriteAnimation.create("default.jpg", 1, 1, 1);
        actAnimation = spriteAnimation;

        collision = CollisionManager.getInstance();
        collision.addEnemy(this);
    }

    @Override
    public void draw(Batch batch, float alpha){
        stateTime += Gdx.graphics.getDeltaTime();
        currentFrame = actAnimation.getKeyFrame(stateTime, true);
        batch.draw(currentFrame,this.getX(),getY());
        update();
    }

    public void update() {
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
