package com.mygdx.game.Screens;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Animation.bullets;
import com.mygdx.game.Animation.explosion;
import com.mygdx.game.ship.evilShip1;
import com.mygdx.game.ship.evilShipSpco;
import com.mygdx.game.ship.heroShip;
import com.mygdx.game.ship.ship;

import java.util.ArrayList;

public class game extends ApplicationAdapter {
    private heroShip hs;
    private evilShip1 es;
    private evilShip1 es2;
    private evilShipSpco boss;

    ArrayList<heroShip> myShip;
    ArrayList<ship>ships;
    ArrayList<evilShipSpco>bossArray;
    ArrayList<explosion>exp;

    //background
    Texture background;
    public SpriteBatch back;
    Sprite sprite;
    Camera cam;


    public void create(){
        //evil ships
        this.es = new evilShip1(-4, true);
        this.es2 = new evilShip1(-4, false);
        this.boss = new evilShipSpco();
        ships = new ArrayList<>();
        bossArray = new ArrayList<>();
        ships.add(es);
        ships.add(es2);
        bossArray.add(boss);

        //hero ship
        this.hs = new heroShip();
        myShip = new ArrayList<>();
        myShip.add(hs);
        exp = new ArrayList<>();

        //background
        background = new Texture(Gdx.files.internal("backgrounds/Planets.jpg"));
        sprite = new Sprite(background);
        back = new SpriteBatch();
        cam = new OrthographicCamera();
    }


    public void dispose() {
        for (ship s : ships){
            s.dispose();
        }
        for (heroShip s : myShip){
            s.dispose();
        }
        for (evilShipSpco b : bossArray){
            b.dispose();
        }
    }

    public void render(){
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //render drawing
		back.begin();
		sprite.draw(back);
        sprite.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		back.end();

        //render of heroShip
		for (heroShip s : myShip){
            s.shoot();
            s.update();
        }

        //render of evil ships
		es.evilRender(Gdx.graphics.getDeltaTime());
		es.evilUpdate();
		es2.evilRender(Gdx.graphics.getDeltaTime());
		es2.evilUpdate();
        boss.evilRender(Gdx.graphics.getDeltaTime());
        boss.update();

        //CHECK FOR DEAD SHIPS
        //evil
        ArrayList<ship> removeShips = new ArrayList<>();
		for (ship s : ships) {
            if (s.isDead()) {
                exp.add(new explosion(3, s.x, s.y));
                removeShips.add(s);
            }
        }

        //hero
		ships.removeAll(removeShips);
        ArrayList<heroShip>removeMe = new ArrayList<>();
		for (heroShip s : myShip){
            if (s.isDead()){
                removeMe.add(s);
                exp.add(new explosion(3,s.x,s.y));
            }
        }
		myShip.removeAll(removeMe);

        //boss
        ArrayList<evilShipSpco>removeBoss = new ArrayList<>();
        if (boss.isDead()){
            removeBoss.add(boss);
        }
        bossArray.removeAll(removeBoss);

        //render ships + bullets of evil guys
		for (ship s : ships) {
            s.renderShip();
            for (bullets b : s.bulletsArray){
                b.render();
            }
        }
        for (evilShipSpco b : bossArray){
            b.renderShip();
            for (bullets bs : b.bulletsArray){
                bs.render();
            }
        }

        //render ship's bullets
		for (heroShip s : myShip) {
            s.renderShip();
            for (bullets b : s.bulletsArray) {
                b.render();
            }
        }

        //Check if we're hit
        ArrayList<bullets>removeThis = new ArrayList<>(); //to remove bullets of evil ships
		for (ship s : ships){
            for (bullets b : s.bulletsArray){
                if (hs.isHit(b)){
                    removeThis.add(b);
                    exp.add(new explosion(1, hs.x-3*hs.width, hs.y));
                }
            }
            s.bulletsArray.removeAll(removeThis);
        }
        for (evilShipSpco boss : bossArray){
            for (bullets b : boss.bulletsArray){
                if (hs.isHit(b)){
                    removeThis.add(b);
                    exp.add(new explosion(1, hs.x-3*hs.width, hs.y));
                }
            }
            boss.bulletsArray.removeAll(removeThis);
        }

        //Check if we hit ship
        ArrayList<bullets>remove = new ArrayList<>();
        for (heroShip hs : myShip) {
            for (ship s : ships) {
                for (bullets b : hs.bulletsArray) {
                    if (s.isHit(b) && b.y != Gdx.graphics.getHeight() - hs.height - hs.y) {
                        remove.add(b);
                        exp.add(new explosion(2 ,s.x-s.width ,s.y-2*s.height));
                    } else if (b.y == Gdx.graphics.getHeight() - hs.height - hs.y + 50) {
                        remove.add(b);
                    }
                }
            }
            for(evilShipSpco boss : bossArray){
                for (bullets b : hs.bulletsArray){
                    if (boss.isHit(b) && b.y != Gdx.graphics.getHeight() - hs.height - hs.y) {
                        remove.add(b);
                        exp.add(new explosion(2 ,boss.x-boss.width ,boss.y-2*boss.height));
                    } else if (b.y == Gdx.graphics.getHeight() - hs.height - hs.y + 50) {
                        remove.add(b);
                    }
                }
            }
        }
		hs.bulletsArray.removeAll(remove);

        //remove explosions
        ArrayList<explosion>removeExplosions = new ArrayList<>();
		for (explosion e : exp){
            e.render();
            if (e.time >= 1/3f) {
                removeExplosions.add(e);
            }
        }
		exp.removeAll(removeExplosions);
    }
}
