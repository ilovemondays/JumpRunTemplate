package com.ilovemondays.jumpruntemplate.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class Player extends BaseActor {
    //@TODO: Srite Animation
    private Texture texture = new Texture(Gdx.files.internal("player/megaman.jpg"));
    private boolean lookUp;

    public Player(float x, float y) {
        super();
        setBounds(getX(),getY(),texture.getWidth(),texture.getHeight());
        setColor(Color.RED);
        setPosition(x, y);
        isJumping = false;
        lookUp = false;
        shootTimerMax = 20;
        shootTimer = shootTimerMax;
    }

    @Override
    public void draw(Batch batch, float alpha){
        batch.draw(texture,this.getX(),getY());
    }

    public void moveLeft() {
        setX(getX() - speed);
        direction = -1;
    }

    public void moveRight() {
        setX(getX() + speed);
        direction = 1;
    }

    public void jump() {
        if(isAir) return;

        isJumping = true;
        //@TODO: Magic numbers...
        setY(getY() + speed*2);
        actJumpingDistance += speed*2;

        if(actJumpingDistance >= jumpingDistance) {
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
                stage.addActor(new Bullet(getX(), getY()+30, 2));
            } else {
                stage.addActor(new Bullet(getX(), getY()+30, this.direction));
            }
        }
    }

    public void setLookUp(boolean state) {
        lookUp = state;
    }

    public boolean getLookUp() {
        return lookUp;
    }

}
