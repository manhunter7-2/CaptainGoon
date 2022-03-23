package com.mygdx.game.Animation;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/* --- COMMENT SECTION ---
allyShip touched : pack3;
other touched : pack1;
ship dead : pack 2;
--- END OF COMMENT SECTION --- */


public class explosion extends ApplicationAdapter {
    public SpriteBatch batch;
    public Texture tex;
    public TextureRegion[] animFrames;
    public Animation anim;
    public float time;
    float x;
    float y;

    public explosion(int path, float x, float y){
        String PATH = selectPath(path);
        batch = new SpriteBatch();
        tex = new Texture(PATH);
        animFrames = new TextureRegion[9];
        this.x = x;
        this.y = y;

        TextureRegion[][]tmp = TextureRegion.split(tex, 256, 256);

        int cpt = 0;
        for (int cpt2=0; cpt2<3; cpt2++){
            for (int cpt3=0; cpt3<3; cpt3++){
                animFrames[cpt++] = tmp[cpt3][cpt2];
            }
        }
        anim = new Animation(1f/9f, animFrames);
    }

    private String selectPath(int nb) {
        switch (nb){
            case 1: //allyShip touched
                return "sprites/kisspng-sprite-explosion_pack/kisspng-sprite-explosion_pack3.png";
            case 2: //evil ship touched
                return "sprites/kisspng-sprite-explosion_pack/kisspng-sprite-explosion_pack.png";
            case 3: //ship dead
                return "sprites/kisspng-sprite-explosion_pack/kisspng-sprite-explosion_pack2.png";
            default: //wrong number m8
                return "not_good_file";
        }
    }

    public void render(){
        time += Gdx.graphics.getDeltaTime();
        batch.begin();
        batch.draw((TextureRegion) anim.getKeyFrame(time, false), x, y);
        batch.end();
    }

}
