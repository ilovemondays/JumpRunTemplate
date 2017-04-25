package com.ilovemondays.jumpruntemplate.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.controllers.Controller;
import com.ilovemondays.jumpruntemplate.conf.Defines;

/**
 * Created by Matthias on 13.04.2017.
 */
public class ControllerMap {
    Controller controller;

    public ControllerMap(Controller controller) {
        this.controller = controller;
    }

    public boolean isLeft () {
        if(controller != null) {
            if (( (Gdx.input.isKeyPressed(Input.Keys.A) ||
                    controller.getPov(Defines.Controller.POV) == Defines.Controller.BUTTON_DPAD_LEFT ||
                    controller.getPov(Defines.Controller.POV) == Defines.Controller.BUTTON_DPAD_UP_LEFT) &&
                    !Gdx.input.isKeyPressed(Input.Keys.D))) {
                return true;
            }
        } else {
            if(Gdx.input.isKeyPressed(Input.Keys.A) && !Gdx.input.isKeyPressed(Input.Keys.D)) return true;
        }

        return false;
    }

    public boolean isRight () {
        if(controller != null) {
            if (((Gdx.input.isKeyPressed(Input.Keys.D) ||
                    controller.getPov(Defines.Controller.POV) == Defines.Controller.BUTTON_DPAD_RIGHT ||
                    controller.getPov(Defines.Controller.POV) == Defines.Controller.BUTTON_DPAD_UP_RIGHT) &&
                    !Gdx.input.isKeyPressed(Input.Keys.A))) {
                return true;
            }
        } else {
            if(Gdx.input.isKeyPressed(Input.Keys.D) && !Gdx.input.isKeyPressed(Input.Keys.A)) return true;
        }
        return false;
    }

    public boolean isUp() {
        if(controller != null) {
            if(Gdx.input.isKeyPressed(Input.Keys.W) ||
                    controller.getPov(Defines.Controller.POV) == Defines.Controller.BUTTON_DPAD_UP ||
                    controller.getPov(Defines.Controller.POV) == Defines.Controller.BUTTON_DPAD_UP_RIGHT ||
                    controller.getPov(Defines.Controller.POV) == Defines.Controller.BUTTON_DPAD_UP_LEFT) {
                return true;
            }
        } else {
            if(Gdx.input.isKeyPressed(Input.Keys.W)) return true;
        }
        return false;
    }

    public boolean isJump() {
        if(controller!=null) {
            if(Gdx.input.isKeyPressed(Input.Keys.K) || controller.getButton(Defines.Controller.BUTTON_A)) return true;
        } else {
            if(Gdx.input.isKeyPressed(Input.Keys.K)) return true;
        }
        return false;
    }

    public boolean isDash() {
        if(controller!=null) {
            if(Gdx.input.isKeyPressed(Input.Keys.J) || controller.getButton(Defines.Controller.BUTTON_B)) return true;
        } else {
            if(Gdx.input.isKeyPressed(Input.Keys.J)) return true;
        }
        return false;
    }

    public boolean isShoot() {
        if(controller!=null) {
            if(Gdx.input.isKeyPressed(Input.Keys.L) || controller.getButton(Defines.Controller.BUTTON_X)) return true;
        } else {
            if(Gdx.input.isKeyPressed(Input.Keys.L)) return true;
        }
        return false;
    }
}
