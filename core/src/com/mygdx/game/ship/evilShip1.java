package com.mygdx.game.ship;

import com.badlogic.gdx.Gdx;

public class evilShip1 extends ship {
    public evilShip1(int Speed, boolean pos, int startNb) {
        super(startNb*Gdx.graphics.getWidth()/3, Gdx.graphics.getHeight()-140, "sprites/ships/roundysh_small.png");
        this.SPEED = Speed;
        this.pos = pos;
    }
}
