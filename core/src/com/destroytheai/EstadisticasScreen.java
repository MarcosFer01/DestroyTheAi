
package com.destroytheai;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStreamWriter;
import java.io.BufferedReader;

public class EstadisticasScreen extends BaseScreen {
    private Stage stage;
    private Skin skin;
    private TextButton salir;
    Label Estadisticas;
    private int partJug;
    private int partComp;
    private int eneM;
    private int oroR;
    private int curas;

    public int getPartJug() {
        return partJug;
    }
    public void setPartJug(int partJug) {
        this.partJug = partJug;
    }
    public int getPartComp() {
        return partComp;
    }
    public void setPartComp(int partComp) {
        this.partComp = partComp;
    }
    public int getEneM() {
        return eneM;
    }
    public void setEneM(int eneM) {
        this.eneM = eneM;
    }
    public int getOroR() {
        return oroR;
    }
    public void setOroR(int oroR) {
        this.oroR = oroR;
    }
    public int getCuras() {
        return curas;
    }
    public void setCuras(int curas) {
        this.curas = curas;
    }

    /**
     * En el constructor de esta clase se crea la pantalla entera y se da funcionalidad al botón que posee. Antes de mostrar los datos se cargan con el método introducir datos
     * @param game
     */
    public EstadisticasScreen(final MainGame game) {
        super(game);

        introducirDatos();

        stage = new Stage(new FitViewport(640, 360));
        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
        Estadisticas = new Label("Pisos explorados: "+this.getPartJug() +
                "\nPartidas completadas: "+this.getPartComp() +
                "\nEnemigos muertos: "+this.getEneM() +
                "\nOro recogido: "+this.getOroR() +
                "\nSanaciones usadas: "+this.getCuras(), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        salir = new TextButton("Salir", skin);

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
    /**
     * Se encarga de dibujar la pantalla para hacerla visible
     */
    public void render(float delta) {
        Gdx.gl.glClearColor(0.2784f, 0.2941f, 0.3059f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
    }

    /**
     * En este método se lee el documento con los datos de las estadísticas, y se guardan en las variable correspondientes
     */
    public void introducirDatos(){
        try{
            BufferedReader miFichero = new BufferedReader(new FileReader("estadisticas.txt"));

            String linea = miFichero.readLine();
            while(linea != null){
                if(linea!="\n"){
                    String[] datos = linea.split(";");
                    this.setPartJug(Integer.getInteger(datos[0]));
                    this.setPartComp(Integer.getInteger(datos[1]));
                    this.setEneM(Integer.getInteger(datos[2]));
                    this.setOroR(Integer.getInteger(datos[3]));
                    this.setCuras(Integer.getInteger(datos[4]));
                }
                linea = miFichero.readLine();
            }
            miFichero.close();
        }catch(Exception e){}
    }

    /**
     * Este método se encarga de sobreescribir en el documento los datos de las estadísticas
     */
    public void sobreescribirDatos(){
        try{
            BufferedWriter miFichero = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("estadisitcas.txt")));
            String estadisticas = this.getPartJug()+";"+this.getPartComp()+";"+this.getEneM()+";"+this.getOroR()+";"+this.getCuras();
            miFichero.write(estadisticas);
            miFichero.close();
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Este método se encarga de mostrar los datos actualizados en la pantalla de estadísticas
     */
    public void actualizarEstadisticas(){
        Estadisticas.setText("Pisos explorados: "+this.getPartJug() +
                "\nPartidas completadas: "+this.getPartComp() +
                "\nEnemigos muertos: "+this.getEneM() +
                "\nOro recogido: "+this.getOroR() +
                "\nSanaciones usadas: "+this.getCuras());
    }
}
