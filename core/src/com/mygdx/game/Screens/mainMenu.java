package com.mygdx.game.Screens;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class mainMenu extends ApplicationAdapter {
    SpriteBatch textBatch;
    BitmapFont font;
    CharSequence str = "Welcome to Captain Goon !!! \n Tap anywhere to begin";

    public void create(){
        textBatch = new SpriteBatch();
        font = new BitmapFont();
        font.getData().setScale(1.5f);
    }

    public void render() {
        this.create();
        ScreenUtils.clear(0, 0, 0.2f, 1);
        textBatch.begin();
        font.draw(textBatch, str, 275, Gdx.graphics.getHeight() / 2f);
        textBatch.end();
    }

}
