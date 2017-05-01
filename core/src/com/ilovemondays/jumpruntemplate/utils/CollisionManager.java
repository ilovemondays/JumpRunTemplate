package com.ilovemondays.jumpruntemplate.utils;

import com.ilovemondays.jumpruntemplate.actors.BaseBoss;
import com.ilovemondays.jumpruntemplate.actors.Player;

import java.util.ArrayList;

/**
 * Created by matthias on 16.04.17.
 */
public class CollisionManager {
    private static CollisionManager instance;

    private ArrayList<BaseBoss> enemies = new ArrayList<BaseBoss>();
    private Player player;

    private CollisionManager () {}

    public static CollisionManager getInstance () {
        if (CollisionManager.instance == null) {
            CollisionManager.instance = new CollisionManager ();
        }
        return CollisionManager.instance;
    }

    public ArrayList<BaseBoss> getEnemies() {
        return enemies;
    }

    public void addEnemy(BaseBoss enemy) {
        enemies.add(enemy);
    }

}
