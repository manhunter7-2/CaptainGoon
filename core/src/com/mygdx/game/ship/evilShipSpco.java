package com.mygdx.game.ship;

import com.badlogic.gdx.Gdx;
import com.mygdx.game.Animation.bullets;

public class evilShipSpco extends ship{
    public evilShipSpco() {
        super(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()-140, "sprites/ships/spco_small.png");
        this.SPEED = 3;
    }

    public void update(){
        if (this.x >= Gdx.graphics.getWidth()-this.width || this.x <= this.width){
            this.SPEED = -SPEED;
        }
        this.x += SPEED;
        for (bullets b : bulletsArray){
            b.update(5);
        }
    }
}
