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

    ArrayList<heroShip> myShip;
    ArrayList<ship>ships;
    ArrayList<evilShipSpco>bossArray;
    ArrayList<explosion>exp;

    //background
    Texture background;
    public SpriteBatch back;
    Sprite sprite;
    Camera cam;

    //--- win/lose conditions
    SpriteBatch endBatch;
    BitmapFont endFont;

    //life
    SpriteBatch heroSticker;
    Texture heroShipSticker;
    Sprite heroSprite;
    BitmapFont heroLife;
    SpriteBatch bossSticker;
    Texture bossShipSticker;
    Sprite bossSprite;
    BitmapFont bossLife;


    public void create(){
        //evil ships
        this.es = new evilShip1(-3, true, 2);
        this.es2 = new evilShip1(-3, false, 1);
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
        endBatch = new SpriteBatch();
        endFont = new BitmapFont();

        //life
        heroSticker = new SpriteBatch();
        heroShipSticker = new Texture("sprites/ships/blueships1_small.png");
        heroSprite = new Sprite(heroShipSticker);
        heroLife = new BitmapFont();

        bossSticker = new SpriteBatch();
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

        //render drawing
		back.begin();
		sprite.draw(back);
        sprite.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		back.end();

        heroSticker.begin();
        heroSprite.draw(heroSticker);
        heroSprite.setSize(32, 32);
        heroSprite.setPosition(20, 20);
        heroLife.draw(heroSticker, hs.life +"%", heroSprite.getX()+heroSprite.getWidth(), heroSprite.getY()+10);
        heroSticker.end();

        bossSticker.begin();
        bossSprite.draw(bossSticker);
        bossSprite.setSize(32,32);
        bossLife.draw(bossSticker, boss.life +"%", bossSprite.getX()-heroSprite.getWidth(), bossSprite.getY()+10);
        bossSprite.setPosition(Gdx.graphics.getWidth()-20-bossSprite.getWidth(), 20);
        bossSticker.end();

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
                end = true;
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
                for (heroShip he : myShip) {
                    if (he.isHit(b)) {
                        removeThis.add(b);
                        exp.add(new explosion(1, he.x - 3 * he.width, he.y));
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
                        exp.add(new explosion(1, he.x - 3 * he.width, he.y));
                    }
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
                    for (heroShip he : myShip) {
                        if (boss.isHit(b) && b.y != Gdx.graphics.getHeight() - he.height - he.y) {
                            remove.add(b);
                            exp.add(new explosion(2, boss.x - boss.width, boss.y - 2 * boss.height));
                        } else if (b.y == Gdx.graphics.getHeight() - he.height - he.y + 50) {
                            remove.add(b);
                        }
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

        //winning conditions
        if (ships.size()==0 && !hs.isDead() && bossArray.size()==0){
            endBatch.begin();
            endFont.draw(endBatch, "YOU SAVED THE WORLD !", Gdx.graphics.getWidth()/2f, Gdx.graphics.getHeight()/2f);
            endBatch.end();
            end = true;
        }
        if (myShip.size() == 0){
            endBatch.begin();
            endFont.draw(endBatch, "GAME OVER", Gdx.graphics.getWidth()/2f, Gdx.graphics.getHeight()/2f);
            endBatch.end();
            end = true;
        }

    }

}