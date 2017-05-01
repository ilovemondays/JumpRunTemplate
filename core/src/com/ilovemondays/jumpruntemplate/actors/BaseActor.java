package com.ilovemondays.jumpruntemplate.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.ilovemondays.jumpruntemplate.conf.Defines;
import com.ilovemondays.jumpruntemplate.utils.SpriteAnimation;

public class BaseActor  extends GameObject{
    //@TODO: Sinnvolle Konstanten einbauen
    int hitpoints = 10;

    protected Defines.Actors actorType;

    protected float acceleration = 0.5f;
    protected Vector2 targetSpeed = new Vector2(6.0f, 20.0f);
    protected Vector2 currentSpeed = new Vector2(0, 0);
    protected Defines.Direction direction = Defines.Direction.RIGHT;
    protected boolean flip;

    boolean isAir = false;
    boolean isJumping = false;
    protected float actJumpingDistance = 0;

    int shootTimerMax = 100;
    int shootTimer;

    float stateTime = 0f;
    protected Animation spriteAnimation;
    protected Animation actAnimation;
    TextureRegion currentFrame;

    public BaseActor() {
        spriteAnimation = SpriteAnimation.create("default.jpg", 1, 1, 1);
        actAnimation = spriteAnimation;
        shootTimer = shootTimerMax;
        bounds = new Rectangle(getX(), getY(), getWidth(), getHeight());
    }

    @Override
    public void draw(Batch batch, float alpha){
        stateTime += Gdx.graphics.getDeltaTime();
        currentFrame = actAnimation.getKeyFrame(stateTime, true);
        flip = (direction == Defines.Direction.LEFT);
        batch.draw(currentFrame, flip ? getX()+getWidth() : getX(), getY(), flip ? -1*getWidth() : getWidth(), getHeight());
        update();
    }

    public void update() {
        checkAir();
        updateShootTimer();
        checkSpeed();
        updateBounds();
    }

    public void checkAir() {
        if(isJumping) return;

        currentSpeed.y -= Defines.GRAVITY;

        isAir = true;
        if(getY() >= 0) {
            setY(getY() + currentSpeed.y);
        }
        if(getY() < 0) {
            setY(0);
            currentSpeed.y = 0;
            isAir = false;
        }
    }

    public void checkSpeed() {
        if(currentSpeed.x > targetSpeed.x) currentSpeed.x = targetSpeed.x;
        if(currentSpeed.x < 0 && currentSpeed.x < -1*targetSpeed.x) currentSpeed.x = -1*targetSpeed.x;
        if(currentSpeed.y > targetSpeed.y) currentSpeed.y = targetSpeed.y;
        if(currentSpeed.y < 0 && currentSpeed.y < -1*targetSpeed.y) currentSpeed.y = -1*targetSpeed.y;
    }

    public void setAccelerationX() {
        if(direction == Defines.Direction.LEFT) {
            currentSpeed.x -= acceleration;
        }
        if(direction == Defines.Direction.RIGHT) {
            currentSpeed.x += acceleration;
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

    public boolean isJumping() {
        return isJumping;
    }

    public void setJumping(boolean value) {
        isJumping = value;
    }

    public int getShootTimerMax() {
        return shootTimerMax;
    }

    public void setShootTimerMax(int val) {
        shootTimerMax = val;
    }

    public boolean getIsAir() {
        return isAir;
    }

    public void setShootTimer (int shootTimer) {
        this.shootTimer = shootTimer;
    }

    public int getShootTimer () {
        return shootTimer;
    }

    public Defines.Actors getActorType() {
        return actorType;
    }

}
