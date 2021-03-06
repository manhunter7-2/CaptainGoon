package com.mygdx.game.ship;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Animation.bullets;
import com.mygdx.game.Animation.enemyBulletBig;
import com.mygdx.game.Animation.enemyBulletSmall;

import java.util.ArrayList;


public abstract class ship {
    public float x;
    public float y;
    public int height = 120;
    public int width = 70;
    public int life = 100;
    double SPEED; // FOR EVIL USE ONLY
    boolean pos; //true=right, false=left || FOR EVIL USE ONLY TOO
    float deltaSpawn; //for evil use only
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
        if (this.life <=0){
            this.life = 0;
            return true;
        }
        return false;
    }

    public boolean isHit(bullets s){
        if (this.getClass().getSimpleName().equals("heroShip")){
            if ((s.y<=this.y+this.height || s.y >= Gdx.graphics.getHeight()-this.height) && s.x<=this.x+(this.width/2f) && s.x>=this.x-(this.width/2f)) {
                this.life -= s.dmg;
                this.b.setColor(1, 1 + ((this.life - 100) / 100f), 1 + ((this.life - 100) / 100f), 1);
                return true;
            }
        }
        if (!this.getClass().getSimpleName().equals("heroShip")) {
            if (s.y <= this.y + this.height && s.y >= Gdx.graphics.getHeight() - this.height && s.x <= this.x + (this.width / 2f) && s.x >= this.x - (this.width / 2f)) {
                this.life -= s.dmg;
                this.b.setColor(1, 1 + ((this.life - 100) / 100f), 1 + ((this.life - 100) / 100f), 1);
                return true;
            }
        }
        return false;
    }

    //--- FOR EVIL PURPOSE ONLY ---
    //updates for evil ships only
    public void evilUpdate() {
        this.x += this.SPEED;
        if (this.pos){
            if (this.x <= Gdx.graphics.getWidth()/2f-1 || this.x >= Gdx.graphics.getWidth()+1-this.width){
                this.SPEED = -this.SPEED;
            }
        }
        if (!this.pos){
            if (this.x <= 0 || this.x >= Gdx.graphics.getWidth()/2f+10){
                this.SPEED = -this.SPEED;
            }
        }
        for (bullets b : bulletsArray){
            b.update(5);
        }

    }

    //auto-shoot
    public void evilRender(float deltaTime){
        if (deltaSpawn >= 1){
            if (this.getClass().getSimpleName().equals("evilShip1")) {
                this.bulletsArray.add(new enemyBulletSmall(this.x - 10, this.y - 50));
            }else{
                this.bulletsArray.add(new enemyBulletBig(this.x-10, this.y-50));
            }
            deltaSpawn = 0;
        }
        else{
            deltaSpawn = deltaSpawn+deltaTime;
        }
    }
}