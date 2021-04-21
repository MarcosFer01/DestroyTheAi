package com.destroytheai.Scene2D;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class actorJugador extends Actor {

    private Texture jugador;
    private int vida = 10;

    public actorJugador(Texture jugador){
        this.jugador = jugador;
        setSize(jugador.getWidth(), jugador.getHeight());
    }

    @Override
    public void act(float delta) {

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(jugador, getX(), getY());
    }

    public int getVida() {
        return vida;
    }

    public void setVida(int vida) {
        this.vida = vida;
    }
}
