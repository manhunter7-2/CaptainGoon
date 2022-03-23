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
    int height = 70;
    int width = 40;
    int life = 100;
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

    public void dispose(){
        this.b.dispose();
        this.tx.dispose();
    }

    public void renderShip(){
        this.b.begin();
        this.b.draw(this.tx, this.x, this.y, this.width, this.height);
        this.b.end();
    }

    public boolean isHit(bullets bullet){
        if (this.y==bullet.y && this.x<=bullet.x && this.x>=bullet.x-this.width){
            this.life -= 10;
            return true;
        }
        return false;
    }

    public boolean isDead(){
        return this.life == 0;
    }

    public void bulletDespawn(){
        ArrayList<bullets>bin = new ArrayList<>();
        if (bulletsArray != null) {
            for (bullets a : bulletsArray) {
                if (a.y > Gdx.graphics.getHeight() || a.y < 0 || isHit(a)) {
                    bin.add(a);
                }
            }
            bulletsArray.removeAll(bin);
        }
    }

    //--- FOR EVIL PURPOSE ONLY ---

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