package com.destroytheai.Scene2D;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.destroytheai.BaseScreen;
import com.destroytheai.MainGame;

public class JuegoScreen extends BaseScreen {

    public JuegoScreen(MainGame game) {
        super(game);
        texturaJugador = new Texture("knight_idle_anim_f0.png");
        texturaEnemigo = new Texture("slime_idle_anim_f0.png");
    }

    private Stage stage;
    private actorJugador jugador;
    private actorEnemigo enemigo;
    private Texture texturaJugador, texturaEnemigo;

    @Override
    public void show() {
        stage = new Stage();
        stage.setDebugAll(true);

        jugador = new actorJugador(texturaJugador);
        enemigo = new actorEnemigo(texturaEnemigo);
        stage.addActor(jugador);
        stage.addActor(enemigo);

        jugador.setPosition(20, 100);
        enemigo.setPosition(500, 100);
    }

    @Override
    public void hide() {
        stage.dispose();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.2784f, 0.2941f, 0.3059f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        comprobarColisiones();
        stage.draw();
    }

    private void comprobarColisiones(){
        if (jugador.getVida()==10 && jugador.getX() + jugador.getWidth() > enemigo.getX()){
            System.out.println("Enfrentamiento");
            jugador.setVida(jugador.getVida()-2);
            System.out.println(jugador.getVida());
        }
    }

    @Override
    public void dispose() {
        texturaJugador.dispose();
    }
}
