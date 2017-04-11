package com.ilovemondays.jumpruntemplate.actors;

import com.badlogic.gdx.Gdx;
import com.ilovemondays.jumpruntemplate.conf.Defines;
import com.ilovemondays.jumpruntemplate.utils.SpriteAnimation;

/**
 * Created by Matthias on 08.04.2017.
 */
public class Bullet extends BaseActor {


    public Bullet(float x, float y, Defines.Direction dir) {
        spriteAnimation = SpriteAnimation.create("bullets/bullet.png", 2, 2, 0.05f);
        actAnimation = spriteAnimation;

        setX(x);
        setY(y);
        direction = dir;
        acceleration = 10;
    }


    public void update() {
        move();
        checkBorder();
    }

    public void move() {
        if(direction== Defines.Direction.UP) {
            setY(getY() + acceleration);
        } else {
            setX(getX() + acceleration * (direction == Defines.Direction.RIGHT ? 1 : -1));
        }
    }

    public void checkBorder() {
        if(getX()> Gdx.graphics.getWidth() || getX() < 0 || getY() > Gdx.graphics.getHeight() || getY() < 0) {
            remove();
        }
    }
}
