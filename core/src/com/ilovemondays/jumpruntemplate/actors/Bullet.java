package com.ilovemondays.jumpruntemplate.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Rectangle;
import com.ilovemondays.jumpruntemplate.conf.Defines;
import com.ilovemondays.jumpruntemplate.utils.CollisionManager;
import com.ilovemondays.jumpruntemplate.utils.SpriteAnimation;

/**
 * Created by Matthias on 08.04.2017.
 */
public class Bullet extends BaseActor {
    int power;
    // SHADER TEST
    private ShaderProgram shader;
    private FileHandle fragmentShader, vertexShader;

    public Bullet(float x, float y, Defines.Direction dir) {
        actorType = Defines.Actors.BULLET;

        spriteAnimation = SpriteAnimation.create("bullets/neonBullet.png", 1, 4, 0.05f);
        actAnimation = spriteAnimation;

        setX(x);
        setY(y);
        setSize(24,24);
        direction = dir;
        acceleration = 5;
        power = 1;

        // for collision detection
        bounds = new Rectangle(getX()+12, getY()+12, 12, 12);
        collision = CollisionManager.getInstance();

        // SHADER TEST
        vertexShader = Gdx.files.internal("shader/vertex.glsl");
        fragmentShader = Gdx.files.internal("shader/blur-h.glsl");
        shader = new ShaderProgram(vertexShader, fragmentShader);
    }

    @Override
    public void draw(Batch batch, float alpha){
        stateTime += Gdx.graphics.getDeltaTime();
        currentFrame = actAnimation.getKeyFrame(stateTime, true);
        //batch.enableBlending();
        //batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE);
        batch.draw(currentFrame,this.getX(),getY());
        //batch.setShader(shader);
        //batch.draw(currentFrame,this.getX(),getY());
        //batch.setShader(null);

        // batch.draw(currentFrame,this.getX(),getY());

        update();
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
