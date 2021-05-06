package com.destroytheai;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.compression.lzma.Base;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class MenuScreen extends BaseScreen {
    private Stage stage;
    private Skin skin;
    private TextButton nuevoJuego;
    private TextButton cargarJuego;
    private TextButton opciones;
    private Music musica;

    public Music getMusica() {
        return musica;
    }
    public void setMusica(Music musica) {
        this.musica = musica;
    }

    public MenuScreen(final MainGame game) {
        super(game);

        musica = game.getManager().get("musica.ogg");

        stage = new Stage(new FitViewport(640, 360));
        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
        Image title = new Image(new Texture("logoE.png"));
        nuevoJuego = new TextButton("Nuevo Juego", skin);
        cargarJuego = new TextButton("Cargar Juego", skin);
        opciones = new TextButton("Opciones", skin);

        nuevoJuego.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(game.personajesScreen);
            }
        });
        cargarJuego.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(game.cinematica2screen);
            }
        });
        opciones.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(game.opcionesScreen);
            }
        });

        title.setSize(340,50);
        title.setPosition(150, 250);
        nuevoJuego.setSize(200, 100);
        nuevoJuego.setPosition(20, 50);
        cargarJuego.setSize(200, 100);
        cargarJuego.setPosition(220, 50);
        opciones.setSize(200, 100);
        opciones.setPosition(420, 50);
        stage.addActor(title);
        stage.addActor(nuevoJuego);
        stage.addActor(cargarJuego);
        stage.addActor(opciones);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        musica.setVolume(0.75f);
        musica.setLooping(true);
        musica.play();
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
