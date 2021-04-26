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

public class PersonajesScreen extends BaseScreen {
    private Stage stage;
    private Skin skin;
    private TextButton cabSel;
    private TextButton aseSel;
    private TextButton magSel;

    public PersonajesScreen(final MainGame game) {
        super(game);

        stage = new Stage(new FitViewport(640, 360));
        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
        Image cabImg = new Image(new Texture("knight_idle_anim_f0.png"));
        Image aseImg = new Image(new Texture("elf_f_hit_anim_f0.png"));
        Image magImg = new Image(new Texture("wizzard_f_hit_anim_f0.png"));
        Label cabAtri = new Label("- Vida Máxima: +++" +
                "\n- Ataque: +", new Label.LabelStyle(new BitmapFont(),Color.WHITE));
        Label aseAtri = new Label("- Vida Máxima: ++" +
                "\n- Ataque: ++", new Label.LabelStyle(new BitmapFont(),Color.WHITE));
        Label magAtri = new Label("- Vida Máxima: +" +
                "\n- Ataque: +++", new Label.LabelStyle(new BitmapFont(),Color.WHITE));
        cabSel = new TextButton("Caballero", skin);
        aseSel = new TextButton("Asesino", skin);
        magSel = new TextButton("Mago", skin);

        cabSel.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(game.gameScreen);
            }
        });
        aseSel.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(game.gameScreen);
            }
        });
        magSel.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(game.gameScreen);
            }
        });

        cabImg.setPosition(56, 200);
        cabImg.setSize(100,100);
        aseImg.setPosition(269, 200);
        aseImg.setSize(100,160);
        magImg.setPosition(483, 200);
        magImg.setSize(100,160);
        cabAtri.setPosition(56, 125);
        aseAtri.setPosition(269, 125);
        magAtri.setPosition(483, 125);
        cabSel.setSize(133, 75);
        cabSel.setPosition(40, 25);
        aseSel.setSize(134, 75);
        aseSel.setPosition(253, 25);
        magSel.setSize(133, 75);
        magSel.setPosition(467, 25);
        stage.addActor(cabImg);
        stage.addActor(aseImg);
        stage.addActor(magImg);
        stage.addActor(cabAtri);
        stage.addActor(aseAtri);
        stage.addActor(magAtri);
        stage.addActor(cabSel);
        stage.addActor(aseSel);
        stage.addActor(magSel);
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