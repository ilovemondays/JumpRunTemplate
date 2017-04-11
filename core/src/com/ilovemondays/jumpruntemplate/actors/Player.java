package com.ilovemondays.jumpruntemplate.actors;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.ilovemondays.jumpruntemplate.conf.Defines;
import com.ilovemondays.jumpruntemplate.utils.SpriteAnimation;

public class Player extends BaseActor {
    private boolean lookUp;
    private Animation spriteRunAnimation;

    public Player(float x, float y) {
        super();
        spriteAnimation = SpriteAnimation.create("player/idle.png", 1, 2, 0.5f);
        spriteRunAnimation = SpriteAnimation.create("player/run.png", 1, 10, 0.1f);

        actAnimation = spriteAnimation;
        setPosition(x, y);
        isJumping = false;
        lookUp = false;
        shootTimerMax = 10;
        shootTimer = shootTimerMax;
    }

    public void moveLeft() {
        direction = Defines.Direction.LEFT;
        actAnimation = spriteRunAnimation;
        setAccelerationX();
        setX(getX() + currentSpeed.x);
    }

    public void moveRight() {
        direction = Defines.Direction.RIGHT;
        actAnimation = spriteRunAnimation;
        setX(getX() + currentSpeed.x);
        setAccelerationX();
    }

    public void jump() {
        if(isAir) return;

        actAnimation = spriteAnimation;
        isJumping = true;
        currentSpeed.y += 1.8f;
        actJumpingDistance += currentSpeed.y;
        setY(getY() + currentSpeed.y);
        if(actJumpingDistance >= targetSpeed.y) {
            isJumping = false;
            actJumpingDistance = 0;
        }

    }

    public void endJump() {
        isJumping = false;
        actJumpingDistance = 0;
    }

    public void shoot(Stage stage) {
        if(canShoot()) {
            shootTimer = 0;
            //@TODO: delete magic number
            if(lookUp) {
                stage.addActor(new Bullet(getX(), getY()+30, Defines.Direction.UP));
            } else {
                stage.addActor(new Bullet(getX(), getY()+30, direction));
            }
        }
    }

    public void setLookUp(boolean state) {
        lookUp = state;
    }

    public boolean getLookUp() {
        return lookUp;
    }

    public void setIdleAnimation() {
        actAnimation = spriteAnimation;
        if(currentSpeed.x != 0 && currentSpeed.x < 0) {
            currentSpeed.x += acceleration;
        }
        if(currentSpeed.x != 0 && currentSpeed.x > 0) {
            currentSpeed.x -= acceleration;
        }
        setX(getX() + currentSpeed.x);
    }

}
