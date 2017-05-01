package com.ilovemondays.jumpruntemplate.conf;

import com.badlogic.gdx.controllers.PovDirection;

/**
 * Created by Matthias on 11.04.2017.
 */
public class Defines {
    public final static float GRAVITY = 0.2f;

    public enum Actors {
        PLAYER, BOSS, BULLET
    }

    public enum Direction {
        LEFT, RIGHT, UP, DOWN
    }


    public static class Controller {
        // XBOX
        public static final int BUTTON_A = 0;
        public static final int BUTTON_B = 1;
        public static final int BUTTON_X = 2;
        public static final int BUTTON_Y = 3;
        public static final int BUTTON_LB = 4;
        public static final int BUTTON_RB = 5;
        public static final int BUTTON_BACK = 6;
        public static final int BUTTON_START = 7;
        public static final int BUTTON_LS = 8; //Left Stick pressed down
        public static final int BUTTON_RS = 9; //Right Stick pressed down

        public static final int POV = 0;
        public static final PovDirection BUTTON_DPAD_UP = PovDirection.north;
        public static final PovDirection BUTTON_DPAD_DOWN = PovDirection.south;
        public static final PovDirection BUTTON_DPAD_RIGHT = PovDirection.east;
        public static final PovDirection BUTTON_DPAD_LEFT = PovDirection.west;
        public static final PovDirection BUTTON_DPAD_CENTER = PovDirection.center;
        public static final PovDirection BUTTON_DPAD_UP_RIGHT = PovDirection.northEast;
        public static final PovDirection BUTTON_DPAD_UP_LEFT = PovDirection.northWest;

        public static final int AXIS_LY = 0; //-1 is up | +1 is down
        public static final int AXIS_LX = 1; //-1 is left | +1 is right
        public static final int AXIS_RY = 2; //-1 is up | +1 is down
        public static final int AXIS_RX = 3; //-1 is left | +1 is right
        public static final int AXIS_TRIGGER = 4; //LT and RT are on the same Axis! LT > 0 | RT < 0
    }
}
