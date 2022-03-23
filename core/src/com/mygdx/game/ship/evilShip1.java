package com.mygdx.game.ship;

import com.badlogic.gdx.Gdx;
import com.mygdx.game.Animation.bullets;
import com.mygdx.game.Animation.enemyBulletSmall;

import java.util.ArrayList;

public class evilShip1 extends ship {
    float deltaSpawn;
    public evilShip1(int Speed, boolean pos) {
        super(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()-80, "sprites/ships/roundysh_small.png");
        this.SPEED = Speed;
        this.pos = pos;
    }
    public void render(float deltaTime){
        if (deltaSpawn >= 1){
            this.bulletsArray.add(new enemyBulletSmall(this.x-10, this.y-50));
            deltaSpawn = 0;
        }
        else{
            deltaSpawn = deltaSpawn+deltaTime;
        }
    }
}
