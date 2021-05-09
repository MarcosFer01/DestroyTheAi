package com.destroytheai;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class LoadingScreen extends BaseScreen{

    private Stage stage;
    private Skin skin;
    private Label loading;
    private Label porcentage;

    /**
     * En el constructor de esta clase se crea la pantalla entera
     * @param game
     */
    public LoadingScreen(MainGame game) {
        super(game);

        stage = new Stage(new FitViewport(640, 360));
        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
        loading = new Label("Loading...", skin);
        loading.setPosition(320-loading.getWidth()/2, 180-loading.getHeight()/2);
        porcentage = new Label("", skin);
        porcentage.setPosition(320-loading.getWidth()/2, 140-loading.getHeight()/2);
        stage.addActor(loading);
        stage.addActor(porcentage);
    }

    @Override
    /**
     * Se encarga de dibujar la pantalla para hacerla visible y a su vez, cambia al menú principal una vez que detecta que la app ha cargado todos los elementos necesarios
     */
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if(game.getManager().update()){
            game.cargaCompleta();
        } else{
            int progress = (int) (game.getManager().getProgress() * 100);
            porcentage.setText("" + progress + "%");
        }

        stage.act();
        stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }
}
