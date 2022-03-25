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
import com.mygdx.game.ship.heroShip;
import com.mygdx.game.ship.ship;

import java.util.ArrayList;

public class game extends ApplicationAdapter {
    private heroShip hs;
    private evilShip1 es;
    private evilShip1 es2;
    ArrayList<heroShip> myShip;
    ArrayList<ship>ships;
    ArrayList<explosion>exp;

    //background
    Texture background;
    SpriteBatch back;
    Sprite sprite;
    Camera cam;


    public void create(){
        this.hs = new heroShip();
        this.es = new evilShip1(-4, true);
        this.es2 = new evilShip1(-4, false);
        ships = new ArrayList<>();
        myShip = new ArrayList<>();
        ships.add(es);
        ships.add(es2);
        myShip.add(hs);
        exp = new ArrayList<>();

        background = new Texture(Gdx.files.internal("backgrounds/Blue Nebula 1 - 1024x1024.png"));
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
    }

    public void render(){

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		back.begin();
		sprite.draw(back);
		back.end();

    //render of textures
		for (heroShip s : myShip){
        s.shoot();
        s.update();
    }
		es.render(Gdx.graphics.getDeltaTime());
		es.evilUpdate();
		es2.render(Gdx.graphics.getDeltaTime());
		es2.evilUpdate();

    //check if a ship is dead
    ArrayList<ship> removeShips = new ArrayList<>();
		for (ship s : ships) {
        if (s.isDead()) {
            exp.add(new explosion(3, s.x, s.y));
            removeShips.add(s);
        }
    }
		ships.removeAll(removeShips);
    ArrayList<heroShip>removeMe = new ArrayList<>();
		for (heroShip s : myShip){
        if (s.isDead()){
            removeMe.add(s);
            exp.add(new explosion(3,s.x,s.y));
        }
    }
		myShip.removeAll(removeMe);

    //render ships + bullets of evil guys
		for (ship s : ships) {
        s.renderShip();
        for (bullets b : s.bulletsArray){
            b.render();
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
    }
		hs.bulletsArray.removeAll(remove);

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
