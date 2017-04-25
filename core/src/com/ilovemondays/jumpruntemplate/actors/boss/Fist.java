package com.ilovemondays.jumpruntemplate.actors.boss;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.ilovemondays.jumpruntemplate.actors.BaseBoss;
import com.ilovemondays.jumpruntemplate.utils.SpriteAnimation;

/**
 * Created by Matthias on 13.04.2017.
 */
public class Fist extends BaseBoss {
    float animationSpeed = 1;
    int timer;
    int defaultDelayTime = 10;
    boolean isMovingBack = false;
    boolean isAttacking = false;
    Actor player;

    public Fist(Actor player) {
        super();
        name = "CYBER FIST";
        hitpoints = 10;
        timer = defaultDelayTime;
        this.player = player;

        spriteAnimation = SpriteAnimation.create("boss/fist.png", 1, 3, animationSpeed);
        actAnimation = spriteAnimation;
        setPosition(500, 0);
    }

    @Override
    public void update() {
        movement();
    }

    private void movement() {
        if(timer <= 0 && !isMovingBack) {
            moveToPlayer();
        }

        // check borders
        if(getX() <= 10 || isMovingBack) {
            isMovingBack = true;
            isAttacking = false;
            moveBy(10,0);
        }
        if(getX() >= 505) {
            isMovingBack = false;
            moveBy(0,0);
            setPosition(500, getY());
            timer = defaultDelayTime;
        }

        // move to players Y position
        if(!isAttacking && !isMovingBack) {
            if(player.getY() > getY()) {
                moveBy(0, 1);
            }
            if(player.getY() < getY()) {
                moveBy(0, -1);
            }
        }

        timer--;
    }

    private void moveToPlayer() {
        isAttacking = true;
        moveBy(-10,0);
    }
}
