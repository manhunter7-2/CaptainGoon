package com.mygdx.game.ship;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.mygdx.game.Animation.allyBullet;
import com.mygdx.game.Animation.bullets;

import java.util.ArrayList;

public class heroShip extends ship{
    public heroShip() {
        super(100, 10, "sprites/ships/blueships1_small.png");
    }

    public void update(){
        this.x = Gdx.input.getX();
        for (bullets a : bulletsArray){
            a.render();
            a.update(5);
        }
    }

    public void shoot(){
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)){
            bulletsArray.add(new allyBullet(this.x+10, this.y+40));
        }
        bulletDespawn();
    }
}
