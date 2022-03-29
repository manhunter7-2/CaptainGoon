package com.mygdx.game.Screens;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
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
    public boolean end = false;

    private heroShip hs;
    private evilShip1 es;
    private evilShip1 es2;
    private evilShipSpco boss;

    public ArrayList<heroShip> myShip;
    public ArrayList<ship>ships;
    public ArrayList<evilShipSpco>bossArray;
    ArrayList<explosion>exp;

    //background
    Texture background;
    public SpriteBatch back;
    Sprite sprite;
    Camera cam;

    //win/lose conditions
    BitmapFont endFont;

    //life
    Texture heroShipSticker;
    Sprite heroSprite;
    BitmapFont heroLife;
    Texture bossShipSticker;
    Sprite bossSprite;
    BitmapFont bossLife;


    public void create(){
        //evil ships
        this.es = new evilShip1( -4,true, 2);
        this.es2 = new evilShip1(4, false, 1);
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

        //winning conditions
        endFont = new BitmapFont();

        //life
        heroShipSticker = new Texture("sprites/ships/blueships1_small.png");
        heroSprite = new Sprite(heroShipSticker);
        heroLife = new BitmapFont();

        bossShipSticker = new Texture("sprites/ships/spco_small.png");
        bossSprite = new Sprite(bossShipSticker);
        bossLife = new BitmapFont();
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

        //RENDER BACKGROUND
		back.begin();
		sprite.draw(back);
        sprite.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        heroSprite.draw(back);
        heroSprite.setSize(32, 32);
        heroSprite.setPosition(20, 20);
        heroLife.draw(back, hs.life +"%", heroSprite.getX()+heroSprite.getWidth(), heroSprite.getY()+10);

        bossSprite.draw(back);
        bossSprite.setSize(32,32);
        bossLife.draw(back, boss.life +"%", bossSprite.getX()-heroSprite.getWidth(), bossSprite.getY()+10);
        bossSprite.setPosition(Gdx.graphics.getWidth()-20-bossSprite.getWidth(), 20);
        back.end();

        //RENDER OF PLAYER'S SHIP
		for (heroShip s : myShip){
            s.shoot();
            s.update();
        }

        //RENDER OF EVIL SHIPS
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
        ships.removeAll(removeShips);
        removeShips.clear();
        //hero
		ships.removeAll(removeShips);
		for (heroShip s : myShip){
            if (s.isDead()){
                removeShips.add(s);
                exp.add(new explosion(3,s.x,s.y));
            }
        }
		myShip.removeAll(removeShips);
        removeShips.clear();
        //boss
        if (boss.isDead()){
            removeShips.add(boss);
        }
        bossArray.removeAll(removeShips);
        removeShips.clear();


        //RENDER SHIPS + BULLETS OF EVIL GUYS
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

        //RENDER PLAYER'S SHIP BULLETS
		for (heroShip s : myShip) {
            s.renderShip();
            for (bullets b : s.bulletsArray) {
                b.render();
            }
        }

        //CHECK IF PLAYER IS HIT
        ArrayList<bullets>removeThis = new ArrayList<>(); //to remove bullets of evil ships
		for (ship s : ships){
            for (bullets b : s.bulletsArray){
                for (heroShip he : myShip) {
                    if (he.isHit(b)) {
                        removeThis.add(b);
                        exp.add(new explosion(1, he.x-he.width, he.y-(he.height/2f)));
                    }
                    if (b.y<=0){
                        removeThis.add(b);
                    }
                }
            }
            s.bulletsArray.removeAll(removeThis);
        }
        for (evilShipSpco boss : bossArray){
            for (bullets b : boss.bulletsArray){
                for (heroShip he : myShip) {
                    if (he.isHit(b)) {
                        removeThis.add(b);
                        exp.add(new explosion(1, he.x-he.width, he.y-(he.height/2f)));
                    }
                    if (b.y<=0){
                        removeThis.add(b);
                    }
                }
            }
            boss.bulletsArray.removeAll(removeThis);
        }

        //CHECK IF PLAYER SHOOTS AT SHIP
        ArrayList<bullets>remove = new ArrayList<>();
        for (heroShip hs : myShip) {
            for (ship s : ships) {
                for (bullets b : hs.bulletsArray) {
                    if (s.isHit(b) && b.y != Gdx.graphics.getHeight() - hs.height - hs.y) {
                        remove.add(b);
                        exp.add(new explosion(2 ,s.x-s.width ,s.y-20-(s.height/2f)));
                    } else if (b.y == Gdx.graphics.getHeight()) {
                        remove.add(b);
                    }
                }
            }
            for(evilShipSpco boss : bossArray){
                for (bullets b : hs.bulletsArray){
                    if (boss.isHit(b)) {
                        remove.add(b);
                        exp.add(new explosion(2, boss.x, boss.y - 20 - (boss.height/2f)));
                    } else if (b.y >=600) {
                        remove.add(b);
                    }
                }
            }
        }
		hs.bulletsArray.removeAll(remove);

        //REMOVE EXPLOSIONS
        ArrayList<explosion>removeExplosions = new ArrayList<>();
		for (explosion e : exp){
            e.render();
            if (e.time >= 1/3f) {
                removeExplosions.add(e);
            }
        }
		exp.removeAll(removeExplosions);

        //WINNING CONDITIONS
        if (ships.size()==0 && !hs.isDead() && bossArray.size()==0){
            back.begin();
            endFont.draw(back, "YOU SAVED THE WORLD !", Gdx.graphics.getWidth()/2f-70, Gdx.graphics.getHeight()/2f);
            back.end();
            end = true;
        }
        if (myShip.size() == 0){
            back.begin();
            endFont.draw(back, "GAME OVER", Gdx.graphics.getWidth()/2f, Gdx.graphics.getHeight()/2f);
            back.end();
            end = true;
        }

    }

}