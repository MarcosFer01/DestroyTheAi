package com.destroytheai.Mecanicas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.destroytheai.Entidades.PersonajeEntidad;
import com.destroytheai.GameScreen;
import com.destroytheai.MainGame;

public class Controler {
    Viewport viewport;
    Stage stage;
    boolean up, down, left, right;
    Label vida;
    Label oro;
    Label medico;
    private PersonajeEntidad personaje;

    public void setPersonaje(PersonajeEntidad personaje) {
        this.personaje = personaje;
    }
    public boolean isUp() {
        return up;
    }
    public void setUp(boolean up) {
        this.up = up;
    }
    public boolean isDown() {
        return down;
    }
    public void setDown(boolean down) {
        this.down = down;
    }
    public boolean isLeft() {
        return left;
    }
    public void setLeft(boolean left) {
        this.left = left;
    }
    public boolean isRight() {
        return right;
    }
    public void setRight(boolean right) {
        this.right = right;
    }

    /**
     * En el contructor de esta clase se crean los Label e imágenes necesarios para la pantalla, aparte, se les da funcionalidad a las imágenes de los controles.
     * @param game
     */
    public Controler(final MainGame game){
        viewport = new FitViewport(640, 360);
        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);

        vida = new Label("Vida: ", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        oro = new Label("Oro: ", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        medico = new Label("Debería ir a ver al médico", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        vida.setPosition(10, 340);
        oro.setPosition(10, 320);
        medico.setPosition(260,340);
        medico.setVisible(false);

        Image opciones = new Image(new Texture("menu.png"));
        opciones.setSize(50,50);
        opciones.setPosition(590, 310);
        opciones.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                game.setGameScreen(new GameScreen(game, 1));
                game.setScreen(game.gameScreen);
                return true;
            }
        });

        Image upImg = new Image(new Texture("up.png"));
        upImg.setSize(50,50);
        upImg.setPosition(50, 100);
        upImg.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                up = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                up = false;
            }
        });

        Image downImg = new Image(new Texture("down.png"));
        downImg.setSize(50,50);
        downImg.setPosition(50, 0);
        downImg.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                down = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                down = false;
            }
        });

        Image rightImg = new Image(new Texture("right.png"));
        rightImg.setSize(50,50);
        rightImg.setPosition(100, 50);
        rightImg.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                right = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                right = false;
            }
        });

        Image leftImg = new Image(new Texture("left.png"));
        leftImg.setSize(50,50);
        leftImg.setPosition(0,50);
        leftImg.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                left = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                left = false;
            }
        });

        stage.addActor(upImg);
        stage.addActor(downImg);
        stage.addActor(rightImg);
        stage.addActor(leftImg);
        stage.addActor(opciones);
        stage.addActor(vida);
        stage.addActor(oro);
        stage.addActor(medico);
    }

    /**
     * Este método se encarga de dibujar los controles
     */
    public void draw(){
        stage.draw();
    }

    /**
     * Este método actualiza los label para mostrar la información del personaje, aparte, avisa al jugador cuando debería curarse
     */
    public void setTextoPer(){
        if (personaje.getVida()<5){
            medico.setVisible(true);
        } else{
            medico.setVisible(false);
        }
        if (personaje.getVida()<0){
            vida.setText("Vida: "+0+"/"+personaje.getVidaMax());
        } else{
            vida.setText("Vida: "+personaje.getVida()+"/"+personaje.getVidaMax());
        }
        oro.setText("Oro: "+personaje.getOro());
    }
}
