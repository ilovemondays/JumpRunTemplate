package com.ilovemondays.jumpruntemplate.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by Matthias on 08.04.2017.
 */
public class Bullet extends BaseActor {
    Texture plasmaSheet;
    float stateTime;
    private static final int FRAME_COLS = 1, FRAME_ROWS = 7;
    public Animation plasmaAnimation;
    TextureRegion currentFrame;
    int direction;

    public Bullet(float x, float y, int dir) {
        plasmaSheet = new Texture(Gdx.files.internal("bullets/plasma.png"));

        // Use the split utility method to create a 2D array of TextureRegions. This is
        // possible because this sprite sheet contains frames of equal size and they are
        // all aligned.
        TextureRegion[][] tmp = TextureRegion.split(plasmaSheet,
                plasmaSheet.getWidth() / FRAME_COLS,
                plasmaSheet.getHeight() / FRAME_ROWS);

        // Place the regions into a 1D array in the correct order, starting from the top
        // left, going across first. The Animation constructor requires a 1D array.
        TextureRegion[] plasmaFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        int index = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                plasmaFrames[index++] = tmp[i][j];
            }
        }

        plasmaAnimation = new Animation(0.025f, plasmaFrames);
        stateTime = 0f;

        setX(x);
        setY(y);
        direction = dir;
        speed = 6;
    }

    @Override
    public void draw(Batch batch, float alpha){
        stateTime += Gdx.graphics.getDeltaTime();
        currentFrame = plasmaAnimation.getKeyFrame(stateTime, true);
        batch.draw(currentFrame,this.getX(),getY());
        update();
    }

    public void update() {
        move();
        checkBorder();
    }

    public void move() {
        setX((getX() + speed)*direction);
    }

    public void checkBorder() {
        if(getX()> Gdx.graphics.getWidth() || getX() < 0) {
            remove();
        }
    }
}
