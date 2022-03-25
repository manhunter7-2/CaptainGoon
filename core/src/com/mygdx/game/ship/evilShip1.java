package com.mygdx.game.ship;

import com.badlogic.gdx.Gdx;

public class evilShip1 extends ship {
    public evilShip1(int Speed, boolean pos) {
        super(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()-80, "sprites/ships/roundysh_small.png");
        this.SPEED = Speed;
        this.pos = pos;
    }
}
