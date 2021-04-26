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

public class OpcionesScreen extends BaseScreen {
    private Stage stage;
    private Skin skin;
    private TextButton estadisticas;
    private TextButton atras;
    private TextButton musicaBoton;
    private TextButton sonidoBoton;

    public OpcionesScreen(final MainGame game) {
        super(game);

        stage = new Stage(new FitViewport(640, 360));
        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
        Label musica = new Label("Música", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        Label sonido = new Label("Sonido", new Label.LabelStyle(new BitmapFont(),Color.WHITE));
        estadisticas = new TextButton("Estadísticas", skin);
        atras = new TextButton("Salir", skin);
        musicaBoton = new TextButton("", skin, "toggle");
        sonidoBoton = new TextButton("", skin, "toggle");

        estadisticas.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(game.estadisticasScreen);
            }
        });
        atras.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(game.menuScreen);
            }
        });

        musica.setPosition(60, 82);
        sonido.setPosition(60, 226);
        musica.setFontScale(3.0f);
        sonido.setFontScale(3.0f);
        estadisticas.setSize(200, 72);
        estadisticas.setPosition(380, 216);
        atras.setSize(200, 72);
        atras.setPosition(380, 72);
        musicaBoton.setSize(40, 40);
        musicaBoton.setPosition(220, 72);
        sonidoBoton.setSize(40, 40);
        sonidoBoton.setPosition(220, 216);
        stage.addActor(estadisticas);
        stage.addActor(atras);
        stage.addActor(musica);
        stage.addActor(sonido);
        stage.addActor(musicaBoton);
        stage.addActor(sonidoBoton);
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
