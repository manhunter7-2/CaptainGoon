package com.mygdx.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.mygdx.game.Animation.bullets;
import com.mygdx.game.ship.evilShip1;
import com.mygdx.game.ship.heroShip;
import com.mygdx.game.ship.ship;
import java.util.ArrayList;


public class CaptainGoon implements ApplicationListener {
	private heroShip hs;
	private evilShip1 es;
	ArrayList<ship>ships;

	@Override
	public void create() {
		this.hs = new heroShip();
		this.es = new evilShip1(-4, true);
		ships = new ArrayList<>();
		ships.add(hs);
		ships.add(es);
	}

	@Override
	public void dispose() {
		for (ship s : ships){
			s.dispose();
		}
	}

	@Override
	public void render() {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		hs.shoot();
		hs.update();
		es.render(Gdx.graphics.getDeltaTime());
		es.evilUpdate();

		ArrayList<ship> removeShips = new ArrayList<>();
		for (ship s : ships) {
			if (s.isDead()) {
				removeShips.add(s);
			}
		}
		ships.removeAll(removeShips);

			for (ship s : ships) {
				s.renderShip();
				for (bullets b : s.bulletsArray){
					b.render();
					s.bulletDespawn();
				}
			}
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