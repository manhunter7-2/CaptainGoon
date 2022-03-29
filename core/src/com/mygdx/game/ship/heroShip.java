package com.mygdx.game.ship;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.mygdx.game.Animation.allyBullet;
import com.mygdx.game.Animation.bullets;

public class heroShip extends ship{
    public heroShip() {
        super(100, 10, "sprites/ships/blueships1_small.png");
    }

    //updating hero ship
    public void update(){
        if (Gdx.input.getX() < Gdx.graphics.getWidth()-this.width)
        {
            this.x = Gdx.input.getX();
        }
        for (bullets a : bulletsArray){
            a.render();
            a.update(5);
        }

        if (Gdx.input.getY()>=500){
            this.y = Gdx.graphics.getHeight()-Gdx.input.getY();
            System.out.println(Gdx.input.getY());
        }
    }

    //shooting command
    public void shoot(){
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT) && !isDead()){
            this.bulletsArray.add(new allyBullet(this.x+this.width/2f, this.y+50));
        }
    }
}
