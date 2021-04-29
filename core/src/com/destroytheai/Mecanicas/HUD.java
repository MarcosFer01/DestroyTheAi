package com.destroytheai.Mecanicas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.destroytheai.Entidades.PersonajeEntidad;

public class HUD {
    Viewport viewport;
    Stage stage;
    Label vida;
    Label oro;

    public HUD(PersonajeEntidad personaje){
        viewport = new FitViewport(640, 360);
        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);

        vida = new Label("Vida: "+personaje.getVida()+"/"+personaje.getVidaMax(), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        oro = new Label("Oro: "+personaje.getOro(), new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        stage.addActor(vida);
        stage.addActor(oro);
    }

    public void draw(){
        stage.draw();
    }

    public void resize(int width, int height){
        viewport.update(width, height);
    }
}
