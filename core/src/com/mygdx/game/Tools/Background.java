package com.mygdx.game.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Background {
        OrthographicCamera camera = new OrthographicCamera(1, Gdx.graphics.getHeight()/Gdx.graphics.getWidth());
        SpriteBatch backBatch = new SpriteBatch();
        Texture tex = new Texture(Gdx.files.internal("backgrounds/Blue Nebula 1 - 1024x1024.png"));
        TextureRegion texR = new TextureRegion(tex, 0, 0, 1024, 1024);
        Sprite sp = new Sprite(texR);

        public void setSprite() {
            sp.setSize(1.0f, 1.0f);
            sp.setOrigin(sp.getWidth() / 2, sp.getHeight() / 2);
            sp.setPosition(-sp.getWidth() / 2, -sp.getHeight() / 2);
        }
    public void render(){
        backBatch.setProjectionMatrix(camera.combined);
        backBatch.begin();
        sp.draw(backBatch);
        backBatch.end();
    }
}
