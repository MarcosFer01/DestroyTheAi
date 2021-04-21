package com.destroytheai;

import com.badlogic.gdx.Gdx;
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
    private Image title;
    private TextButton nuevoJuego;
    private TextButton cargarJuego;
    private TextButton opciones;

    public MenuScreen(final MainGame game) {
        super(game);

        stage = new Stage(new FitViewport(640, 360));
        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
        title = new Image(game.getManager().get("gameover.png", Texture.class));
        nuevoJuego = new TextButton("Nuevo Juego", skin);
        cargarJuego = new TextButton("Cargar Juego", skin);
        opciones = new TextButton("Opciones", skin);

        nuevoJuego.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(game.gameScreen);
            }
        });
        cargarJuego.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(game.gameScreen);
            }
        });
        opciones.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(game.gameScreen);
            }
        });

        title.setPosition(320 - title.getWidth(), 320 - title.getHeight());
        title.setSize(3,3);
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
