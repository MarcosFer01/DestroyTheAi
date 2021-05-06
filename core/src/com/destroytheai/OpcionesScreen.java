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
    private TextButton playBoton;
    private TextButton pausaBoton;

    public OpcionesScreen(final MainGame game) {
        super(game);

        stage = new Stage(new FitViewport(640, 360));
        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
        Label musica = new Label("Música", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        estadisticas = new TextButton("Estadísticas", skin);
        atras = new TextButton("Salir", skin);
        playBoton = new TextButton("Si", skin);
        pausaBoton = new TextButton("No", skin);

        pausaBoton.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.menuScreen.getMusica().pause();
            }
        });
        playBoton.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.menuScreen.getMusica().play();
            }
        });
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

        musica.setPosition(110, 216);
        musica.setFontScale(3.0f);
        estadisticas.setSize(200, 72);
        estadisticas.setPosition(380, 216);
        atras.setSize(200, 72);
        atras.setPosition(380, 72);
        pausaBoton.setSize(60, 40);
        pausaBoton.setPosition(60, 144);
        playBoton.setSize(60, 40);
        playBoton.setPosition(220, 144);
        stage.addActor(estadisticas);
        stage.addActor(atras);
        stage.addActor(musica);
        stage.addActor(pausaBoton);
        stage.addActor(playBoton);
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
