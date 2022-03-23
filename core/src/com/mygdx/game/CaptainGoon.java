package com.mygdx.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.mygdx.game.Animation.bullets;
import com.mygdx.game.Animation.explosion;
import com.mygdx.game.ship.evilShip1;
import com.mygdx.game.ship.heroShip;
import com.mygdx.game.ship.ship;
import java.util.ArrayList;


public class CaptainGoon implements ApplicationListener {
	private heroShip hs;
	private evilShip1 es;
	ArrayList<heroShip>myShip;
	ArrayList<ship>ships;
	ArrayList<explosion>exp;


	@Override
	public void create() {
		this.hs = new heroShip();
		this.es = new evilShip1(-4, true);
		ships = new ArrayList<>();
		myShip = new ArrayList<>();
		ships.add(es);
		myShip.add(hs);
		exp = new ArrayList<>();
	}


	@Override
	public void dispose() {
		for (ship s : ships){
			s.dispose();
		}
		for (heroShip s : myShip){
			s.dispose();
		}
	}


	@Override
	public void render() {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		//render of textures
		for (heroShip s : myShip){
			s.shoot();
			s.update();
		}
		es.render(Gdx.graphics.getDeltaTime());
		es.evilUpdate();

		//check if a ship is dead
		ArrayList<ship> removeShips = new ArrayList<>();
		for (ship s : ships) {
			if (s.isDead()) {
				removeShips.add(s);
			}
		}
		ships.removeAll(removeShips);
		ArrayList<heroShip>removeMe = new ArrayList<>();
		for (heroShip s : myShip){
			if (s.isDead()){
				removeMe.add(s);
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
						exp.add(new explosion(hs.x, hs.y));
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

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
}