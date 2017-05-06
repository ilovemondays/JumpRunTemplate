package com.ilovemondays.jumpruntemplate.actors;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.ilovemondays.jumpruntemplate.conf.Defines;
import com.ilovemondays.jumpruntemplate.utils.ActorManager;
import com.ilovemondays.jumpruntemplate.utils.SpriteAnimation;

public class Player extends BaseActor {
    private boolean lookUp;
    private Animation spriteRunAnimation;
    private Animation spriteDashAnimation;
    private boolean isDashing = false;
    private int actDashingDistance = 0;
    private int maxDashingDistance = 100;

    public Player(float x, float y) {
        super();
        actorType = Defines.Actors.PLAYER;
        actorManager = ActorManager.getInstance();

        spriteAnimation = SpriteAnimation.create("player/idle.png", 1, 2, 0.5f);
        spriteRunAnimation = SpriteAnimation.create("player/run.png", 1, 10, 0.1f);
        spriteDashAnimation = SpriteAnimation.create("player/dash.png", 1, 2, 0.1f);

        setSize(64, 64);

        actAnimation = spriteAnimation;
        setPosition(x, y);
        isJumping = false;
        lookUp = false;
        shootTimerMax = 10;
        shootTimer = shootTimerMax;

        bounds = new Rectangle(getX(), getY(), getWidth(), getHeight());
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

    //@todo: verbessern
    public void dashRight() {
        if(!isAir || (actDashingDistance >= maxDashingDistance)) {
            isDashing = false;
            return;
        }
        isDashing = true;
        direction = Defines.Direction.RIGHT;
        actAnimation = spriteDashAnimation;
        currentSpeed.x = 0;
        currentSpeed.y = 0;
        currentSpeed.x += 3;
        actDashingDistance += 3;
        setX(getX() + currentSpeed.x);
        setAccelerationX();
    }

    //@todo: verbessern
    public void dashLeft() {
        if(!isAir || (actDashingDistance >= maxDashingDistance)) {
            isDashing = false;
            return;
        }
        isDashing = true;
        direction = Defines.Direction.LEFT;
        actAnimation = spriteDashAnimation;
        currentSpeed.x = 0;
        currentSpeed.y = 0;
        currentSpeed.x -= 3;
        actDashingDistance += 3;
        setX(getX() + currentSpeed.x);
        setAccelerationX();
    }

    public void jump() {
        if(isAir) return;

        actAnimation = spriteAnimation;
        isJumping = true;
        currentSpeed.y += 1.4f;
        actJumpingDistance += currentSpeed.y;
        setY(getY() + currentSpeed.y);
        if(actJumpingDistance >= targetSpeed.y) {
            isJumping = false;
            isDashing = false;
            actDashingDistance = 0;
            actJumpingDistance = 0;
        }

    }

    @Override
    public void checkAir() {
        if(isJumping || isDashing) return;

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

    public void endJump() {
        isJumping = false;
        actJumpingDistance = 0;
    }

    public void shoot(Stage stage) {
        BaseActor bullet;
        if(canShoot()) {
            shootTimer = 0;
            //@TODO: delete magic number
            if(lookUp) {
                bullet = new Bullet(getX(), getY()+30, Defines.Direction.UP);
            } else {
                bullet = new Bullet(getX(), getY()+30, direction);
            }
            stage.addActor(bullet);
            actorManager.addBullet(bullet);
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

    public boolean getIsJumping() {
        return isJumping;
    }

    public void setIsDashing(boolean value) {
        isDashing = value;
    }

}
