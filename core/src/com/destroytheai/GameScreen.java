package com.destroytheai;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.destroytheai.Entidades.EnemigoEntidad;
import com.destroytheai.Entidades.Habitaciones;
import com.destroytheai.Entidades.NpcEntidad;
import com.destroytheai.Entidades.ParedesEntidad;
import com.destroytheai.Entidades.PersonajeEntidad;
import com.destroytheai.Entidades.SuelosEntidad;
import com.destroytheai.Mecanicas.Controler;

import java.util.ArrayList;
import java.util.List;

import static com.destroytheai.Constantes.PIXELS_IN_METERS;
import static com.destroytheai.Constantes.TAMAÑO_MAPA;

public class GameScreen extends BaseScreen {

    private Stage stage;
    private World world;
    private Controler controler;
    private int nivel;
    private PersonajeEntidad personaje;
    private SuelosEntidad escaleras;
    private NpcEntidad npc;
    private List<ArrayList> mapa = new ArrayList<ArrayList>();
    private ArrayList<Habitaciones> habitaciones = new ArrayList<Habitaciones>();
    private List<ParedesEntidad> listaParedes = new ArrayList<ParedesEntidad>();
    private List<Image> listaSuelos = new ArrayList<Image>();
    private List<EnemigoEntidad> listaEnemigos = new ArrayList<EnemigoEntidad>();

    private Sound cura, moneda, jefeM, magoA, goblinM, slimeM, ataque;

    public PersonajeEntidad getPersonaje() {
        return personaje;
    }

    /**
     * Esta clase equivale a un piso del juego, y su constructor de encarga de crear e instanciar las demas clases necesarias para hacer que funcione
     * @param game
     * @param piso
     */
    public GameScreen(final MainGame game, int piso) {
        super(game);

        cura = game.getManager().get("bubble2.ogg");
        moneda = game.getManager().get("coin.ogg");
        jefeM = game.getManager().get("giant5.ogg");
        magoA = game.getManager().get("magic.ogg");
        goblinM = game.getManager().get("ogre2.ogg");
        slimeM = game.getManager().get("slime8.ogg");
        ataque = game.getManager().get("swing.ogg");

        nivel=piso;
        stage = new Stage(new FitViewport(640, 360));
        world = new World(new Vector2(0, 0), true);
        crearPiso();
        controler = new Controler(game);

        /*
        En la siguiente sección de código se manejan todas las colisiones del juego
         */
        world.setContactListener(new ContactListener() {

            private boolean hayColision(Contact contact, Object userA, Object userB){
                return (contact.getFixtureA().getUserData().equals(userA) && contact.getFixtureB().getUserData().equals(userB)) ||
                        (contact.getFixtureA().getUserData().equals(userB) && contact.getFixtureB().getUserData().equals(userA));
            }

            @Override
            public void beginContact(Contact contact) {
                /*
                 *Colisión entre jugador y suelos
                 */
                if(hayColision(contact, "player", "floor")){
                    game.estadisticasScreen.setPartJug(game.estadisticasScreen.getPartJug()+1);
                    stage.addAction(
                            Actions.sequence(
                                    Actions.delay(0.5f),
                                    Actions.run(new Runnable(){

                                        @Override
                                        public void run() {
                                            game.setScreen(new GameScreen(game, 3));
                                        }
                                    })
                            )
                    );
                }

                /*
                Colisión entre jugador y NPCs
                 */
                if(hayColision(contact, "player", "medic")){
                    if (personaje.getOro()>10 && personaje.getVida()<personaje.getVidaMax()){
                        game.estadisticasScreen.setCuras(game.estadisticasScreen.getCuras()+1);
                        personaje.curar();
                        controler.setTextoPer();
                        stage.addAction(
                                Actions.sequence(
                                        Actions.run(new Runnable(){

                                            @Override
                                            public void run() {
                                                moneda.play();
                                            }
                                        }),
                                        Actions.delay(0.5f),
                                        Actions.run(new Runnable(){

                                            @Override
                                            public void run() {
                                                cura.play();
                                            }
                                        })
                                )
                        );
                    }
                }
                /*
                 * Colisión entre jugador y enemigos
                 */
                if(hayColision(contact, "player", "enemy")){
                    if (contact.getFixtureB().getUserData().equals("enemy")){
                        Vector2 pos1 = contact.getFixtureB().getBody().getPosition();
                        pos1.set(pos1.x*PIXELS_IN_METERS, pos1.y*PIXELS_IN_METERS);
                        pos1.set((int) pos1.x, (int) pos1.y);
                        for (int i=0; i<listaEnemigos.size(); i++){
                            Vector2 pos2 = new Vector2(listaEnemigos.get(i).getX(), listaEnemigos.get(i).getY());
                            pos2.set((int) pos2.x, (int) pos2.y);
                            if (pos1.equals(pos2)){
                                listaEnemigos.get(i).recibirDaño(personaje.getDaño());
                                switch (personaje.getId()){
                                    case 3:{
                                        magoA.play();
                                        break;
                                    }
                                    default:{
                                        ataque.play();
                                    }
                                }
                                if (listaEnemigos.get(i).isVivo()==false){
                                    if (listaEnemigos.get(i).isJefe()){
                                        game.estadisticasScreen.setPartComp(game.estadisticasScreen.getPartComp()+1);
                                        game.estadisticasScreen.setEneM(game.estadisticasScreen.getEneM()+1);
                                        game.estadisticasScreen.sobreescribirDatos();
                                        stage.addAction(
                                                Actions.sequence(
                                                        Actions.run(new Runnable(){

                                                            @Override
                                                            public void run() {
                                                                jefeM.play();
                                                            }
                                                        }),
                                                        Actions.delay(1f),
                                                        Actions.run(new Runnable(){

                                                            @Override
                                                            public void run() {
                                                                game.setScreen(game.cinematica2screen);
                                                            }
                                                        })
                                                )
                                        );
                                    } else{
                                        switch (listaEnemigos.get(i).getId()){
                                            case 1:{
                                                slimeM.play();
                                                break;
                                            }
                                            case 2:{
                                                goblinM.play();
                                                break;
                                            }
                                        }
                                        int oro = (int) (Math.random()*7+3);
                                        final Body toRemove = contact.getFixtureB().getBody();
                                                Gdx.app.postRunnable(new Runnable() {
                                                @Override
                                                public void run () {
                                                    world.destroyBody(toRemove);
                                                }
                                            });
                                        game.estadisticasScreen.setEneM(game.estadisticasScreen.getEneM()+1);
                                        game.estadisticasScreen.setOroR(game.estadisticasScreen.getOroR()+oro);
                                        listaEnemigos.get(i).remove();
                                        listaEnemigos.remove(i);
                                        personaje.setOro(personaje.getOro()+oro);
                                        controler.setTextoPer();
                                    }
                                } else{
                                    personaje.recibirDaño(listaEnemigos.get(i).getDaño());
                                    controler.setTextoPer();
                                }
                                if(personaje.isVivo()==false){
                                    game.estadisticasScreen.sobreescribirDatos();
                                    stage.addAction(
                                            Actions.sequence(
                                                    Actions.delay(1f),
                                                    Actions.run(new Runnable(){

                                                        @Override
                                                        public void run() {
                                                            game.setScreen(game.gameOverScreen);
                                                        }
                                                    })
                                            )
                                    );
                                }
                            }
                        }
                    } else {
                        Vector2 pos1 = contact.getFixtureA().getBody().getPosition();
                        pos1.set(pos1.x*PIXELS_IN_METERS, pos1.y*PIXELS_IN_METERS);
                        pos1.set((int) pos1.x, (int) pos1.y);
                        for (int i=0; i<listaEnemigos.size(); i++){
                            Vector2 pos2 = new Vector2(listaEnemigos.get(i).getX(), listaEnemigos.get(i).getY());
                            pos2.set((int) pos2.x, (int) pos2.y);
                            if (pos1.equals(pos2)){
                                listaEnemigos.get(i).recibirDaño(personaje.getDaño());
                                switch (personaje.getId()){
                                    case 3:{
                                        magoA.play();
                                        break;
                                    }
                                    default:{
                                        ataque.play();
                                    }
                                }
                                if (listaEnemigos.get(i).isVivo()==false){
                                    if (listaEnemigos.get(i).isJefe()){
                                        game.estadisticasScreen.setPartComp(game.estadisticasScreen.getPartComp()+1);
                                        game.estadisticasScreen.setEneM(game.estadisticasScreen.getEneM()+1);
                                        game.estadisticasScreen.sobreescribirDatos();
                                        stage.addAction(
                                                Actions.sequence(
                                                        Actions.run(new Runnable(){

                                                            @Override
                                                            public void run() {
                                                                jefeM.play();
                                                            }
                                                        }),
                                                        Actions.delay(1f),
                                                        Actions.run(new Runnable(){

                                                            @Override
                                                            public void run() {
                                                                game.setScreen(game.cinematica2screen);
                                                            }
                                                        })
                                                )
                                        );
                                    } else{
                                        switch (listaEnemigos.get(i).getId()){
                                            case 1:{
                                                slimeM.play();
                                                break;
                                            }
                                            case 2:{
                                                goblinM.play();
                                                break;
                                            }
                                        }
                                        int oro = (int) (Math.random()*7+3);
                                        final Body toRemove = contact.getFixtureA().getBody();
                                        Gdx.app.postRunnable(new Runnable() {
                                            @Override
                                            public void run () {
                                                world.destroyBody(toRemove);
                                            }
                                        });
                                        game.estadisticasScreen.setEneM(game.estadisticasScreen.getEneM()+1);
                                        game.estadisticasScreen.setOroR(game.estadisticasScreen.getOroR()+oro);
                                        listaEnemigos.get(i).remove();
                                        listaEnemigos.remove(i);
                                        personaje.setOro(personaje.getOro()+oro);
                                        controler.setTextoPer();
                                    }
                                } else{
                                    personaje.recibirDaño(listaEnemigos.get(i).getDaño());
                                    controler.setTextoPer();
                                }
                                if(personaje.isVivo()==false){
                                    game.estadisticasScreen.sobreescribirDatos();
                                    stage.addAction(
                                            Actions.sequence(
                                                    Actions.delay(1.5f),
                                                    Actions.run(new Runnable(){

                                                        @Override
                                                        public void run() {
                                                            game.setScreen(game.gameOverScreen);
                                                        }
                                                    })
                                            )
                                    );
                                }

                            }
                        }
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
    /**
     * Esta clase se encarga de recorrer el mapa y cargar y mostrar por pantalla todas las entidades y elementos que contiene el mapa
     */
    public void show() {
        Gdx.input.setInputProcessor(stage);
        Texture cabTexture = game.getManager().get("knight_idle_anim_f0.png");
        Texture aseTexture = game.getManager().get("elf_f_hit_anim_f0.png");
        Texture magTexture = game.getManager().get("wizzard_f_hit_anim_f0.png");
        Texture wallTexture = game.getManager().get("wall_1.png");
        Texture rockTexture = game.getManager().get("floor_5.png");
        Texture slimeTexture = game.getManager().get("slime_idle_anim_f0.png");
        Texture goblinTexture = game.getManager().get("goblin_idle_anim_f0.png");
        Texture eyeTexture = game.getManager().get("fly_anim_f1.png");
        Texture stairsTexture = game.getManager().get("stair_nextlevel.png");
        Texture medicTexture = game.getManager().get("medic.png");
        Texture keyTexture = game.getManager().get("key_silver.png");


        for(int i=0; i<mapa.size(); i++){
            for(int j=0; j<mapa.size();j++){
                switch((int) mapa.get(i).get(j)){
                    case 0:{
                        listaParedes.add(new ParedesEntidad(world, rockTexture, new Vector2(i,j)));
                        break;
                    }
                    case 1:{
                        Image suelo = new Image(new Texture("floor_1.png"));
                        suelo.setPosition(i*PIXELS_IN_METERS, j*PIXELS_IN_METERS);
                        suelo.setSize(PIXELS_IN_METERS, PIXELS_IN_METERS);
                        listaSuelos.add(suelo);
                        break;
                    }
                    case 2:{
                        listaParedes.add(new ParedesEntidad(world, wallTexture, new Vector2(i,j)));
                        break;
                    }
                    case 3:{
                        Image suelo = new Image(new Texture("floor_1.png"));
                        suelo.setPosition(i*PIXELS_IN_METERS, j*PIXELS_IN_METERS);
                        suelo.setSize(PIXELS_IN_METERS, PIXELS_IN_METERS);
                        listaSuelos.add(suelo);
                        int selecPer = game.personajesScreen.getSelecPer();
                        switch (selecPer){
                            case 1:{
                                personaje = new PersonajeEntidad(world, cabTexture, new Vector2(i, j), 1, game);
                                break;
                            }
                            case 2:{
                                personaje = new PersonajeEntidad(world, aseTexture, new Vector2(i, j), 2, game);
                                break;
                            }
                            case 3:{
                                personaje = new PersonajeEntidad(world, magTexture, new Vector2(i, j), 3, game);
                                break;
                            }
                        }
                        personaje.setOro(game.gameScreen.getPersonaje().getOro());
                        controler.setPersonaje(personaje);
                        controler.setTextoPer();
                        break;
                    }
                    case 4:{
                        escaleras = new SuelosEntidad(world, stairsTexture, new Vector2(i, j));
                        break;
                    }
                    case 5:{
                        Image suelo = new Image(new Texture("floor_1.png"));
                        suelo.setPosition(i*PIXELS_IN_METERS, j*PIXELS_IN_METERS);
                        suelo.setSize(PIXELS_IN_METERS, PIXELS_IN_METERS);
                        listaSuelos.add(suelo);
                        switch (nivel){
                            case 1:{
                                listaEnemigos.add(new EnemigoEntidad(world, slimeTexture, new Vector2(i, j), 1));
                                break;
                            }
                            default:{
                                listaEnemigos.add(new EnemigoEntidad(world, goblinTexture, new Vector2(i, j), 2));
                            }
                        }
                        break;
                    }
                    case 6:{
                        Image suelo = new Image(new Texture("floor_1.png"));
                        suelo.setPosition(i*PIXELS_IN_METERS, j*PIXELS_IN_METERS);
                        suelo.setSize(PIXELS_IN_METERS, PIXELS_IN_METERS);
                        listaSuelos.add(suelo);
                        listaEnemigos.add(new EnemigoEntidad(world, eyeTexture, new Vector2(i, j), 3));
                        break;
                    }
                    case 7:{
                        Image suelo = new Image(new Texture("floor_1.png"));
                        suelo.setPosition(i*PIXELS_IN_METERS, j*PIXELS_IN_METERS);
                        suelo.setSize(PIXELS_IN_METERS, PIXELS_IN_METERS);
                        listaSuelos.add(suelo);
                        npc = new NpcEntidad(world, medicTexture, new Vector2(i, j));
                        break;
                    }
                }
            }
        }
        for(ParedesEntidad pared : listaParedes){
            stage.addActor(pared);
        }
        for(Image suelo : listaSuelos){
            stage.addActor(suelo);
        }
        for(EnemigoEntidad enemigo : listaEnemigos){
            stage.addActor(enemigo);
        }
        stage.addActor(personaje);
        if (nivel!=3){
            stage.addActor(escaleras);
        }
        stage.addActor(npc);
    }

    @Override
    /**
     * Esta clase elimina las entidades una vez que no se usan para ahorrar recursos
     */
    public void hide() {
        personaje.detach();
        personaje.remove();
        for(Image suelo : listaSuelos){
            suelo.remove();
        }
        for(ParedesEntidad pared : listaParedes){
            pared.detach();
            pared.remove();
        }
        for(EnemigoEntidad enemigo : listaEnemigos){
            enemigo.detach();
            enemigo.remove();
        }
        npc.detach();
        npc.remove();
        Gdx.input.setInputProcessor(null);
    }

    @Override
    /**
     * Este método se ejecuta varias veces por segundo y es donde se maneja la cámara del juego y el escenario que ve el jugador
     */
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
    /**
     * Este método elimina se encarga de eliminar el mundo y la escena
     */
    public void dispose() {
        stage.dispose();
        world.dispose();
    }

    /**
     * Este método crea una matriz rellena de 0 sobre la que se dibujaran las habitaciones y pasillos del piso
     */
    public void crearMapa(){
        for(int i=0;i<TAMAÑO_MAPA;i++){
            ArrayList<Integer> fila = new ArrayList<Integer>();
            for (int j=0; j<TAMAÑO_MAPA; j++){
                fila.add(0);
            }
            mapa.add(fila);
        }
    }

    /**
     * Este método se encarga de rellenar un ArrayList con habitaciones situadas aleatoriamente por el mapa. Así mismo, se añaden propiedades como el numero de enemigos, si la habitación contiene el jefe,
     * las escaleras para cambiar de piso, o el inicio del nivel
     */
    public void crearHabitaciones(){
        int cont=1;
        while(habitaciones.size()<=9){
            int x = (int) (Math.random()*(TAMAÑO_MAPA-10));
            int y = (int) (Math.random()*(TAMAÑO_MAPA-10));
            int w = (int) (Math.random()*(9-5+1)+5);
            int h = (int) (Math.random()*(9-5+1)+5);
            Habitaciones hab = new Habitaciones(x, y, w, h);
            switch (cont){
                case 1:{
                    hab.setSpawner(true);
                    break;
                }
                case 9:{
                    switch (nivel){
                        case 3:{
                            hab.setJefe(true);
                            break;
                        }
                        default:{
                            hab.setFin(true);
                        }
                    }
                    break;
                }
                default:{
                    int numEn = (int) (Math.random()*3+1);
                    hab.setNumEn(numEn);
                }
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
            cont++;
        }
    }

    /**
     * Este método recorre el array mapa y cambia los 0 por el numero que corresponda a las paredes y suelos de las habitaciones.
     */
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
                                    mapa.get(j).set(k, 1);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Este método se encarga de unir los centros de dos habitaciones, cambiando en el arrayList mapa los numeros que equivaldrían a los pasillos y a las paredes que los rodean
     */
    public void dibujarPasillos(){
        for(int i = 0; i<(habitaciones.size()-1);i++){
            int[] centro1 = {(habitaciones.get(i).getX()+habitaciones.get(i).getW()/2), (habitaciones.get(i).getY()+habitaciones.get(i).getH()/2)};
            int[] centro2 = {(habitaciones.get((i+1)).getX()+habitaciones.get((i+1)).getW()/2), (habitaciones.get((i+1)).getY()+habitaciones.get((i+1)).getH()/2)};
            for(int j=0; j<mapa.size(); j++){
                for(int k=0; k<mapa.size(); k++){
                    if(centro2[1]>centro1[1]){
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
                            if (j==centro2[1] && (k>=centro1[0] && k<=centro2[0])){
                                mapa.get(j).set(k,1);
                                if((int) mapa.get(j+1).get(k) != 1){
                                    mapa.get(j+1).set(k, 2);
                                }
                                if((int) mapa.get(j-1).get(k) != 1){
                                    mapa.get(j-1).set(k, 2);
                                }
                            }
                        } else {
                            if (j==centro2[1] && (k<=centro1[0] && k>=centro2[0])){
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
                            if (j==centro2[1] && (k>=centro1[0] && k<=centro2[0])){
                                mapa.get(j).set(k,1);
                                if((int) mapa.get(j+1).get(k) != 1){
                                    mapa.get(j+1).set(k, 2);
                                }
                                if((int) mapa.get(j-1).get(k) != 1){
                                    mapa.get(j-1).set(k, 2);
                                }
                            }
                        } else {
                            if (j==centro2[1] && (k<=centro1[0] && k>=centro2[0])){
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
                }
            }
        }
    }

    /**
     * Este método cambia los valores de las habitaciones del mapa para rellenarlas, dependendiendo del piso y de la habitación, de las entidades que necesite.
     */
    public void rellenarHabitaciones(){
        for(int i=0; i<habitaciones.size();i++){
            Habitaciones hab = habitaciones.get(i);
            int[] centro = {(habitaciones.get(i).getX()+habitaciones.get(i).getW()/2), (habitaciones.get(i).getY()+habitaciones.get(i).getH()/2)};
            for(int j=0; j<mapa.size(); j++){
                for(int k=0; k<mapa.size(); k++){
                    switch (i){
                        case 0:{
                            if(k==hab.getX()+1 && j==hab.getY()+1){
                                mapa.get(j).set(k, 3);
                            }
                            if (k==hab.getX()+1 && j==(hab.getY()+hab.getH()-1)){
                                mapa.get(j).set(k,7);
                            }
                            break;
                        }
                        case 8:{
                            if (hab.isFin()){
                                if (k==hab.getX()+1 && j==hab.getY()+1){
                                    mapa.get(j).set(k, 4);
                                }
                            }
                            if (hab.isJefe()){
                                if (k==centro[0] && j==centro[1]){
                                    mapa.get(j).set(k, 6);
                                }
                            }
                            break;
                        }
                        default:{
                            for (int l=0; l<hab.getNumEn();l++){
                                int eX = (int) (Math.random()*((hab.getX()+(hab.getW()-1))-(hab.getX()+1)+1)+(hab.getX()+1));
                                int eY = (int) (Math.random()*((hab.getY()+(hab.getH()-1))-(hab.getY()+1)+1)+(hab.getY()+1));
                                if(k==eX && j==eY){
                                    mapa.get(j).set(k, 5);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Este método junta todos los métodos necesarios para crear un piso en su totalidad
     */
    public void crearPiso(){
        crearMapa();
        crearHabitaciones();
        dibujarHabitaciones();
        dibujarPasillos();
        rellenarHabitaciones();
    }
}
