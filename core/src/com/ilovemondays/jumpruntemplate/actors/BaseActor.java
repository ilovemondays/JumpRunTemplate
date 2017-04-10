package com.ilovemondays.jumpruntemplate.actors;

import com.badlogic.gdx.scenes.scene2d.Actor;

public class BaseActor  extends Actor{
    //@TODO: Sinnvolle Konstanten einbauen
    int hitpoints = 10;
    int mass = 5;
    int speed = 5;
    boolean isAir = false;
    boolean isJumping = false;
    int jumpingDistance = 300;
    int actJumpingDistance = 0;
    int direction = 1;
    int shootTimerMax = 100;
    int shootTimer;

    public BaseActor() {
        shootTimer = shootTimerMax;
    }

    public void update() {
        checkAir();
        updateShootTimer();
    }

    public void checkAir() {
        if(isJumping) return;

        isAir = true;
        if(getY() >= 0) {
            setY(getY() - mass*2);
        }
        if(getY() < 0) {
            setY(0);
            isAir = false;
        }
    }

    private void updateShootTimer() {
        if(shootTimer<shootTimerMax) {
            shootTimer++;
        } else {
            shootTimer=shootTimerMax;
        }
    }

    public boolean canShoot() {
        return shootTimer==shootTimerMax;
    }
}
