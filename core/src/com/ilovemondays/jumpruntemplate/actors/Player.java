package com.ilovemondays.jumpruntemplate.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 * Created by Matthias on 08.04.2017.
 */
public class Player extends BaseActor {
    private Texture texture = new Texture(Gdx.files.internal("player/megaman.jpg"));

    public Player(float x, float y) {
        setBounds(getX(),getY(),texture.getWidth(),texture.getHeight());
        setColor(Color.RED);
        setPosition(x, y);
        isJumping = false;
    }

    @Override
    public void draw(Batch batch, float alpha){
        batch.draw(texture,this.getX(),getY());
    }

    public void moveLeft() {
        setX(getX() - speed);
    }

    public void moveRight() {
        setX(getX() + speed);
    }

    public void jump() {
        if(isAir) return;

        isJumping = true;
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
        stage.addActor(new Bullet(getX()+35, getY()+30, 1));
    }

}
