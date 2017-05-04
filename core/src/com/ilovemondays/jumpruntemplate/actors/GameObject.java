package com.ilovemondays.jumpruntemplate.actors;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.ilovemondays.jumpruntemplate.utils.CollisionManager;

/**
 * Created by Matthias on 25.04.2017.
 */
public class GameObject extends Actor {
    protected Rectangle bounds;
    protected CollisionManager collision;

    public Rectangle getBounds() {
        return bounds;
    }

    public void setBounds() {
        bounds = new Rectangle(getX(), getY(), getWidth(), getHeight());
    }

    public void updateBounds() {
        bounds.setPosition(getX()+12, getY()+12);
    }
}
