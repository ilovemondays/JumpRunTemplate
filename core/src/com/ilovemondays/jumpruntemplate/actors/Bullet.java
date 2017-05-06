package com.ilovemondays.jumpruntemplate.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.ilovemondays.jumpruntemplate.conf.Defines;
import com.ilovemondays.jumpruntemplate.utils.ActorManager;
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
        currentFrame = actAnimation.getKeyFrame(stateTime, true);

        setX(x);
        setY(y);
        setSize(24,24);
        direction = dir;
        acceleration = 5;
        power = 1;

        // for actorManager detection
        bounds = new Rectangle(getX()+12, getY()+12, 12, 12);
        actorManager = ActorManager.getInstance();

    }

    @Override
    public void draw(Batch batch, float alpha){
        batch.draw(currentFrame,this.getX(),getY());
    }

    @Override
    public void update() {
        stateTime += Gdx.graphics.getDeltaTime();
        currentFrame = actAnimation.getKeyFrame(stateTime, true);

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
            isRemoved = true;
        }
    }

    public Defines.Actors getActorType() {
        return actorType;
    }

    private void checkCollision() {
        for(BaseBoss enemy: actorManager.getBosses()) {
            if(bounds.overlaps(enemy.getBounds())) {
                enemy.getDamage(power);
                remove();
                isRemoved = true;
            }
        }
    }
}
