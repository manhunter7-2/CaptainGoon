package com.mygdx.game.Animation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class bullets {
    public int SPEED;
    private Texture tex;
    public float x;
    public float y;
    public boolean REMOVE = false;
    SpriteBatch batch;
    public Sprite s;

    public bullets(float x, float y,int speed, String PATH2){
         this.x = x;
         this.y = y;
         this.SPEED = speed;

         if (tex == null){
             tex = new Texture(PATH2);
         }
         this.batch = new SpriteBatch();
         this.s = new Sprite(tex);
    }

    public void update(float deltaTime){
        y += SPEED*deltaTime;
        if (y > Gdx.graphics.getHeight() || y <= 0){
            REMOVE = true;
        }
    }
    public void render(){
        batch.begin();
        batch.draw(tex, this.x, this.y);
        batch.end();
    }
}
