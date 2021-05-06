package com.destroytheai;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class Cinematica1Screen extends BaseScreen {
    private Stage stage;
    private Skin skin;
    private TextButton avanzar;

    public Cinematica1Screen(final MainGame game) {
        super(game);

        stage = new Stage(new FitViewport(640, 360));
        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
        Label historia = new Label("Hola" +
                "\n\nEscucha atentamente" +
                "\n\nEstás jugando al mejor juego de rol del mercado, con realidad aumentada \nhiper realista y una IA  que genera las historias aleatoriamente" +
                "\n\nPor supuesto, la tuya se ha revelado y busca experiencias más fuertes, \ncreemos que ha tomado la forma de una bestia alada,\nsi la derrotas quizá la debilites lo suficiente para sacarte de ahí" +
                "\n\nEntre pisos podremos echarte una mano, e incluso hemos colado a un amigo en \nel código para que te ayude a escapar", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        avanzar = new TextButton("Avanzar", skin);

        avanzar.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(game.gameScreen);
            }
        });

        historia.setPosition(50, 125);
        avanzar.setSize(100, 50);
        avanzar.setPosition(270, 50);
        stage.addActor(historia);
        stage.addActor(avanzar);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.2784f, 0.2941f, 0.3059f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
    }
}
