package com.destroytheai;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.destroytheai.Entidades.EnemigoEntidad;
import com.destroytheai.Entidades.Habitaciones;
import com.destroytheai.Entidades.ParedesEntidad;
import com.destroytheai.Entidades.PersonajeEntidad;
import com.destroytheai.Entidades.SuelosEntidad;
import com.destroytheai.Mecanicas.Controler;

import java.util.ArrayList;
import java.util.List;

import static com.destroytheai.Constantes.TAMAÑO_MAPA;

public class GameScreen extends BaseScreen {

    private Stage stage;
    private World world;
    private Controler controler;
    private PersonajeEntidad personaje;
    private List<ArrayList> mapa = new ArrayList<ArrayList>();
    private ArrayList<Habitaciones> habitaciones = new ArrayList<Habitaciones>();
    private List<ParedesEntidad> listaParedes = new ArrayList<ParedesEntidad>();
    private List<SuelosEntidad> listaSuelos = new ArrayList<SuelosEntidad>();
    private List<EnemigoEntidad> listaEnemigos = new ArrayList<EnemigoEntidad>();

    public GameScreen(final MainGame game) {
        super(game);
        stage = new Stage(new FitViewport(640, 360));
        world = new World(new Vector2(0, 0), true);
        controler = new Controler();
        crearMapa();
        crearHabitaciones();
        dibujarHabitaciones();
        dibujarPasillos();
        for(int i=0;i<mapa.size();i++){
            System.out.println(mapa.get(i));
        }

        world.setContactListener(new ContactListener() {

            private boolean hayColision(Contact contact, Object userA, Object userB){
                return (contact.getFixtureA().getUserData().equals(userA) && contact.getFixtureB().getUserData().equals(userB)) ||
                        (contact.getFixtureA().getUserData().equals(userB) && contact.getFixtureB().getUserData().equals(userA));
            }

            @Override
            public void beginContact(Contact contact) {
                if(hayColision(contact, "player", "wall")){
                    personaje.setMovimiento(false);
                    //personaje.setColision(true);
                }
                if(hayColision(contact, "player", "enemy")){
                    personaje.setMovimiento(false);
                    personaje.setVida(personaje.getVida()-2);
                    if(personaje.isVivo()){
                        personaje.setVivo(false);
                        Actions.sequence(
                                Actions.delay(1.5f),
                                Actions.run(new Runnable(){

                                    @Override
                                    public void run() {
                                        game.setScreen(game.gameOverScreen);
                                    }
                                })
                        );
                    }
                }
            }

            @Override
            public void endContact(Contact contact) {

            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {

            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {

            }
        });
    }

    @Override
    public void show() {
        Texture playerTexture = game.getManager().get("knight_idle_anim_f0.png");
        Texture wallTexture = game.getManager().get("wall_1.png");
        Texture floorTexture = game.getManager().get("floor_1.png");
        Texture slimeTexture = game.getManager().get("slime_idle_anim_f0.png");
        Texture stairsTexture = game.getManager().get("slime_idle_anim_f0.png");




        for(int i=0; i<mapa.size(); i++){
            for(int j=0; j<mapa.size();j++){
                switch((int) mapa.get(i).get(j)){
                    case 0:{
                        break;
                    }
                    case 1:{
                        listaSuelos.add(new SuelosEntidad(world, floorTexture, new Vector2(i,j)));
                        break;
                    }
                    case 2:{
                        listaParedes.add(new ParedesEntidad(world, wallTexture, new Vector2(i,j)));
                        break;
                    }
                    case 3:{
                        listaSuelos.add(new SuelosEntidad(world, floorTexture, new Vector2(i, j)));
                        personaje = new PersonajeEntidad(world, playerTexture, new Vector2(i, j));
                        break;
                    }
                    case 4:{
                        listaSuelos.add(new SuelosEntidad(world, stairsTexture, new Vector2(i, j)));
                    }
                }
            }
        }
        listaEnemigos.add(new EnemigoEntidad(world, slimeTexture, new Vector2(1, 1)));
        for(ParedesEntidad pared : listaParedes){
            stage.addActor(pared);
        }
        for(SuelosEntidad suelo : listaSuelos){
            stage.addActor(suelo);
        }
        for(EnemigoEntidad enemigo : listaEnemigos){
            stage.addActor(enemigo);
        }
        stage.addActor(personaje);
    }

    @Override
    public void hide() {
        personaje.detach();
        personaje.remove();
        for(ParedesEntidad pared : listaParedes){
            pared.detach();
            pared.remove();
        }
        for(EnemigoEntidad enemigo : listaEnemigos){
            enemigo.detach();
            enemigo.remove();
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.2784f, 0.2941f, 0.3059f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.getCamera().position.set(new Vector2(personaje.getX(), personaje.getY()), 0);
        stage.getCamera().update();

        stage.act();
        world.step(delta, 6, 2);
        stage.draw();
        controler.draw();
    }



    @Override
    public void dispose() {
        stage.dispose();
        world.dispose();
    }

    public void crearMapa(){
        for(int i=0;i<TAMAÑO_MAPA;i++){
            ArrayList<Integer> fila = new ArrayList<Integer>();
            for (int j=0; j<TAMAÑO_MAPA; j++){
                fila.add(0);
            }
            mapa.add(fila);
        }
    }

    public void crearHabitaciones(){
        int hspawn = 0;
        int hfin = 0;
        while(habitaciones.size()<=9){
            int x = (int) (Math.random()*(TAMAÑO_MAPA-10));
            int y = (int) (Math.random()*(TAMAÑO_MAPA-10));
            int w = (int) (Math.random()*9+5);
            int h = (int) (Math.random()*9+5);
            int s = 0;
            int f = 0;
            if(hspawn==0){
                s = (int) (Math.random()*2);
            }
            if(hspawn == 1 && hfin == 0){
                f = (int) (Math.random()*2);
            }
            Habitaciones hab = new Habitaciones(x, y, w, h);
            if(s==1 && hspawn==0){
                hab.setSpawner(true);
                hspawn++;
            }else if (f==1 && hfin==0){
                hab.setFin(true);
                hfin++;
            }
            if(habitaciones.size()>0){
                for(int i=0; i<habitaciones.size();i++){
                    if (!hab.colisionan(hab, habitaciones.get(i))){
                        habitaciones.add(hab);
                        break;
                    } else{
                    }
                }
            }
            else{
                habitaciones.add(hab);
            }
        }
    }

    public void dibujarHabitaciones(){
        for(int i=0; i<habitaciones.size();i++){
            Habitaciones hab = habitaciones.get(i);
            for(int j=0; j<mapa.size(); j++){
                if(j>=hab.getY() && j<=hab.getY()+hab.getH()){
                    for(int k=0; k<mapa.size(); k++){
                        if(k>=hab.getX() && k<=hab.getX()+hab.getW()){
                            if(j==hab.getY() || j==hab.getY()+hab.getH()) {
                                mapa.get(j).set(k, 2);
                            } else{
                                if(k==hab.getX() || k==hab.getX()+hab.getW()){
                                    mapa.get(j).set(k, 2);
                                } else{
                                    if((k==hab.getX()+1 && j==hab.getY()+1) && habitaciones.get(i).isSpawner()){
                                        mapa.get(j).set(k, 3);
                                    } else if((k==hab.getX()+1 && j==hab.getY()+1) && habitaciones.get(i).isFin()){
                                        mapa.get(j).set(k, 4);
                                    } else{
                                        mapa.get(j).set(k, 1);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public void dibujarPasillos(){
        for(int i = 0; i<(habitaciones.size()-1);i++){
            int[] centro1 = {(habitaciones.get(i).getX()+habitaciones.get(i).getW()/2), (habitaciones.get(i).getY()+habitaciones.get(i).getH()/2)};
            int[] centro2 = {(habitaciones.get((i+1)).getX()+habitaciones.get((i+1)).getW()/2), (habitaciones.get((i+1)).getY()+habitaciones.get((i+1)).getH()/2)};
            int tray = (int) (Math.random()*(1));
            for(int j=0; j<mapa.size(); j++){
                for(int k=0; k<mapa.size(); k++){
                    if(tray==1){
                        if (centro2[1]>centro1[1]){
                            if (k==centro1[0] && (j>=centro1[1] && j<=centro2[1])){
                                mapa.get(j).set(k,1);
                                if((int) mapa.get(j).get(k+1) != 1){
                                    mapa.get(j).set((k+1), 2);
                                }
                                if((int) mapa.get(j).get(k-1) != 1){
                                    mapa.get(j).set((k-1), 2);
                                }
                            }
                            if (centro2[0]>centro1[0]){
                                if (j==centro2[0] && (k>=centro1[0] && k<=centro2[0])){
                                    mapa.get(j).set(k,1);
                                    if((int) mapa.get(j+1).get(k) != 1){
                                        mapa.get(j+1).set(k, 2);
                                    }
                                    if((int) mapa.get(j-1).get(k) != 1){
                                        mapa.get(j-1).set(k, 2);
                                    }
                                }
                            } else {
                                if (j==centro2[0] && (k<=centro1[0] && k>=centro2[0])){
                                    mapa.get(j).set(k,1);
                                    if((int) mapa.get(j+1).get(k) != 1){
                                        mapa.get(j+1).set(k, 2);
                                    }
                                    if((int) mapa.get(j-1).get(k) != 1){
                                        mapa.get(j-1).set(k, 2);
                                    }
                                }
                            }
                        } else {
                            if (k==centro1[0] && (j<=centro1[1] && j>=centro2[1])){
                                mapa.get(j).set(k,1);
                                if((int) mapa.get(j).get(k+1) != 1){
                                    mapa.get(j).set((k+1), 2);
                                }
                                if((int) mapa.get(j).get(k-1) != 1){
                                    mapa.get(j).set((k-1), 2);
                                }
                            }
                            if (centro2[0]>centro1[0]){
                                if (j==centro2[0] && (k>=centro1[0] && k<=centro2[0])){
                                    mapa.get(j).set(k,1);
                                    if((int) mapa.get(j+1).get(k) != 1){
                                        mapa.get(j+1).set(k, 2);
                                    }
                                    if((int) mapa.get(j-1).get(k) != 1){
                                        mapa.get(j-1).set(k, 2);
                                    }
                                }
                            } else {
                                if (j==centro2[0] && (k<=centro1[0] && k>=centro2[0])){
                                    mapa.get(j).set(k,1);
                                    if((int) mapa.get(j+1).get(k) != 1){
                                        mapa.get(j+1).set(k, 2);
                                    }
                                    if((int) mapa.get(j-1).get(k) != 1){
                                        mapa.get(j-1).set(k, 2);
                                    }
                                }
                            }
                        }
                    } else{
                        if (centro2[0]>centro1[0]){
                            if (j==centro2[0] && (k>=centro1[0] && k<=centro2[0])){
                                mapa.get(j).set(k,1);
                                if((int) mapa.get(j+1).get(k) != 1){
                                    mapa.get(j+1).set(k, 2);
                                }
                                if((int) mapa.get(j-1).get(k) != 1){
                                    mapa.get(j-1).set(k, 2);
                                }
                            }
                            if (centro2[1]>centro1[1]) {
                                if (k == centro1[0] && (j >= centro1[1] && j <= centro2[1])) {
                                    mapa.get(j).set(k, 1);
                                    if ((int) mapa.get(j).get(k + 1) != 1) {
                                        mapa.get(j).set((k + 1), 2);
                                    }
                                    if ((int) mapa.get(j).get(k - 1) != 1) {
                                        mapa.get(j).set((k - 1), 2);
                                    }
                                }
                            } else{
                                if (k==centro1[0] && (j<=centro1[1] && j>=centro2[1])){
                                    mapa.get(j).set(k,1);
                                    if((int) mapa.get(j).get(k+1) != 1){
                                        mapa.get(j).set((k+1), 2);
                                    }
                                    if((int) mapa.get(j).get(k-1) != 1){
                                        mapa.get(j).set((k-1), 2);
                                    }
                                }
                            }
                        } else{
                            if (j==centro2[0] && (k<=centro1[0] && k>=centro2[0])){
                                mapa.get(j).set(k,1);
                                if((int) mapa.get(j+1).get(k) != 1){
                                    mapa.get(j+1).set(k, 2);
                                }
                                if((int) mapa.get(j-1).get(k) != 1){
                                    mapa.get(j-1).set(k, 2);
                                }
                            }
                            if (centro2[1]>centro1[1]) {
                                if (k == centro1[0] && (j >= centro1[1] && j <= centro2[1])) {
                                    mapa.get(j).set(k, 1);
                                    if ((int) mapa.get(j).get(k + 1) != 1) {
                                        mapa.get(j).set((k + 1), 2);
                                    }
                                    if ((int) mapa.get(j).get(k - 1) != 1) {
                                        mapa.get(j).set((k - 1), 2);
                                    }
                                }
                            } else{
                                if (k==centro1[0] && (j<=centro1[1] && j>=centro2[1])){
                                    mapa.get(j).set(k,1);
                                    if((int) mapa.get(j).get(k+1) != 1){
                                        mapa.get(j).set((k+1), 2);
                                    }
                                    if((int) mapa.get(j).get(k-1) != 1){
                                        mapa.get(j).set((k-1), 2);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
