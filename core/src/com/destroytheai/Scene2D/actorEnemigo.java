package com.destroytheai.Scene2D;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class actorEnemigo extends Actor {

    private Texture enemigo;

    public actorEnemigo(Texture enemigo){
        this.enemigo = enemigo;
        setSize(enemigo.getWidth(), enemigo.getHeight());
    }

    @Override
    public void act(float delta) {
        setX(getX() - 250 * delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(enemigo, getX(), getY());
    }
}
