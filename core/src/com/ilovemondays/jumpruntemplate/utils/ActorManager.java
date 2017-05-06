package com.ilovemondays.jumpruntemplate.utils;

import com.ilovemondays.jumpruntemplate.actors.BaseActor;
import com.ilovemondays.jumpruntemplate.actors.BaseBoss;
import com.ilovemondays.jumpruntemplate.actors.Player;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by matthias on 16.04.17.
 */
public class ActorManager {
    private static ActorManager instance;

    private ArrayList<BaseBoss> bosses = new ArrayList<BaseBoss>();
    private ArrayList<BaseActor> bullets = new ArrayList<BaseActor>();
    private Player player;

    private Iterator<BaseActor> iteratorBullets;
    private Iterator<BaseBoss> iteratorBosses;

    private ActorManager () {}

    public static ActorManager getInstance () {
        if (ActorManager.instance == null) {
            ActorManager.instance = new ActorManager();
        }
        return ActorManager.instance;
    }

    public ArrayList<BaseBoss> getBosses () {
        return bosses;
    }

    public void addEnemy(BaseBoss enemy) {
        bosses.add(enemy);
    }

    public ArrayList<BaseActor> getBullets () {
        return bullets;
    }

    public void addBullet(BaseActor bullet) {
        bullets.add(bullet);
    }

    public void cleanUp() {
        iteratorBullets = bullets.iterator();
        while (iteratorBullets.hasNext()) {
            if (iteratorBullets.next().getIsRemoved()) {
                iteratorBullets.remove();
            }
        }
        iteratorBosses = bosses.iterator();
        while (iteratorBosses.hasNext()) {
            if (iteratorBosses.next().getIsRemoved()) {
                iteratorBosses.remove();
            }
        }
    }

}
