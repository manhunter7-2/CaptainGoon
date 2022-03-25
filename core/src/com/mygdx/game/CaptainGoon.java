package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.mygdx.game.Screens.game;
import com.mygdx.game.Screens.mainMenu;


public class CaptainGoon extends ApplicationAdapter {
	mainMenu main;
	game game;
	int menuSelect;


	@Override
	public void create() {
		main = new mainMenu();
		game = new game();
		menuSelect = 0;
	}


	@Override
	public void dispose() {
		game.dispose();
	}


	@Override
	public void render() {
		if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)){
			if(menuSelect == 0){
				menuSelect = 1;
			}
			if (menuSelect == 1 && game.end == true){
				menuSelect = 0;
			}
		}
		switch(menuSelect){
			case 0 :
				main.render();
				game.end = false;
				break;
			case 1 :
				if (game.back == null){
					game.create();
				}
				game.render();

		}
	}

	public void youLose(){

	}
}