package com.ilovemondays.jumpruntemplate.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.ilovemondays.jumpruntemplate.conf.Defines;
import com.ilovemondays.jumpruntemplate.utils.CollisionManager;
import com.ilovemondays.jumpruntemplate.utils.SpriteAnimation;

/**
 * Created by Matthias on 08.04.2017.
 */
public class Bullet extends BaseActor {
    int power;

    public Bullet(float x, float y, Defines.Direction dir) {
        actorType = Defines.Actors.BULLET;

        spriteAnimation = SpriteAnimation.create("bullets/neonBullet.png", 1, 4, 0.05f);
        actAnimation = spriteAnimation;

        setX(x);
        setY(y);
        setSize(12,12);
        direction = dir;
        acceleration = 10;
        power = 1;

        // for collision detection
        bounds = new Rectangle(getX(), getY(), getWidth(), getHeight());
        collision = CollisionManager.getInstance();
    }

    @Override
    public void update() {
        move();
        checkCollision();
        checkBorder();
        updateBounds();
    }

    public void move() {
        if(direction== Defines.Direction.UP) {
            setY(getY() + acceleration);
        } else {
            setX(getX() + acceleration * (direction == Defines.Direction.RIGHT ? 1 : -1));
        }
    }

    public void checkBorder() {
        if (getX() > Gdx.graphics.getWidth() || getX() < 0 || getY() > Gdx.graphics.getHeight() || getY() < 0) {
            remove();
        }
    }

    public Defines.Actors getActorType() {
        return actorType;
    }

    private void checkCollision() {
        for(BaseBoss enemy: collision.getEnemies()) {
            if(bounds.overlaps(enemy.getBounds())) {
                enemy.getDamage(power);
                remove();
            }
        }
    }
}
