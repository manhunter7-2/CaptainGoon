package com.mygdx.game.ship;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Animation.bullets;

import java.util.ArrayList;


public abstract class ship {
    public float x;
    public float y;
    public int height = 70;
    public int width = 40;
    public int life = 100;
    int SPEED; // FOR EVIL USE ONLY
    boolean pos; //true=right, false=left || FOR EVIL USE ONLY TOO
    Texture tx;
    SpriteBatch b;
    Sprite s;
    public ArrayList<bullets>bulletsArray = new ArrayList<>();

    public ship (int x, int y, String PATH_SHIP){
        this.x = x;
        this.y = y;
        this.b = new SpriteBatch();
        this.tx = new Texture(Gdx.files.internal(PATH_SHIP));
        this.s = new Sprite(tx);

    }

    //dispose of ship's texture
    public void dispose(){
        this.b.dispose();
        this.tx.dispose();
    }

    //rendering of ship
    public void renderShip(){
        this.b.begin();
        this.b.draw(this.tx, this.x, this.y, this.width, this.height);
        this.b.end();
    }


    //check if a ship is dead (life == 0)
    public boolean isDead(){
        return this.life == 0;
    }

    public boolean isHit(bullets s){
        if ((this.y==s.y-this.height || s.y >= Gdx.graphics.getHeight()-this.height) && s.x<=this.x+this.width && s.x>=this.x-this.width){
            this.life -= 10;
            return true;
        }
        return false;
    }

    //--- FOR EVIL PURPOSE ONLY ---
    //updates for evil ships only
    public void evilUpdate() {
        this.x += this.SPEED;
        if (this.pos){
            if (this.x <= Gdx.graphics.getWidth()/2-1 || this.x >= Gdx.graphics.getWidth()-this.width){
                this.SPEED = -this.SPEED;
            }
        }
        if (!this.pos){
            if (this.x <= 0 || this.x >= Gdx.graphics.getWidth()/2+1){
                this.SPEED = -this.SPEED;
            }
        }
        for (bullets b : bulletsArray){
            b.update(5);
        }
    }
}