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

public class EstadisticasScreen extends BaseScreen {
    private Stage stage;
    private Skin skin;
    private TextButton salir;

    public EstadisticasScreen(final MainGame game) {
        super(game);

        stage = new Stage(new FitViewport(640, 360));
        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
        Label Estadisticas = new Label("Partidas jugadas: 0" +
                "\nPartidas completadas: 0" +
                "\nEnemigos muertos: 0" +
                "\nOro recogido: 0" +
                "\nSanaciones usadas: 0", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        salir = new TextButton("Avanzar", skin);

        salir.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(game.opcionesScreen);
            }
        });

        Estadisticas.setPosition(240, 170);
        Estadisticas.setFontScale(1.10f);
        salir.setSize(100, 50);
        salir.setPosition(270, 50);
        stage.addActor(Estadisticas);
        stage.addActor(salir);
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
