package com.ilovemondays.jumpruntemplate.actors;

import com.badlogic.gdx.scenes.scene2d.Actor;

public class BaseActor  extends Actor{
    int hitpoints = 10;
    int mass = 5;
    int speed = 5;
    boolean isAir = false;
    boolean isJumping = false;
    int jumpingDistance = 600;
    int actJumpingDistance = 0;

    public void update() {
        checkAir();
    }

    public void checkAir() {
        if(isJumping) return;

        isAir = true;
        if(getY() >= 0) {
            setY(getY() - mass);
        }
        if(getY() < 0) {
            setY(0);
            isAir = false;
        }
    }
}
