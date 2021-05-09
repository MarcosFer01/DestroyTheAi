package com.destroytheai.Entidades;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;

import static com.destroytheai.Constantes.DAÑO_A;
import static com.destroytheai.Constantes.DAÑO_G;
import static com.destroytheai.Constantes.DAÑO_M;
import static com.destroytheai.Constantes.DAÑO_N;
import static com.destroytheai.Constantes.DAÑO_O;
import static com.destroytheai.Constantes.DAÑO_S;
import static com.destroytheai.Constantes.PIXELS_IN_METERS;
import static com.destroytheai.Constantes.VIDA_A;
import static com.destroytheai.Constantes.VIDA_G;
import static com.destroytheai.Constantes.VIDA_M;
import static com.destroytheai.Constantes.VIDA_N;
import static com.destroytheai.Constantes.VIDA_O;
import static com.destroytheai.Constantes.VIDA_S;

public class EnemigoEntidad extends Actor {
    private Texture texture;
    private World world;
    private Body body;
    private Fixture fixture;
    private int id;
    private int vida;
    private int daño;
    private boolean vivo;
    private boolean movimiento = false;
    private boolean jefe = false;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public boolean isJefe() {
        return jefe;
    }
    public void setJefe(boolean jefe) {
        this.jefe = jefe;
    }
    public int getVida() {
        return vida;
    }
    public void setVida(int vida) {
        this.vida = vida;
    }
    public int getDaño() {
        return daño;
    }
    public void setDaño(int daño) {
        this.daño = daño;
    }
    public boolean isVivo() {
        return vivo;
    }
    public void setVivo(boolean vivo) {
        this.vivo = vivo;
    }

    /**
     * En el constructor de esta clase se crea el cuerpo del actor y se posiciona en el mapa, aparte, se indica que tipo de enemigo es y se le asignan sus estadísticas necesarias
     * @param world
     * @param texture
     * @param position
     * @param selecEne
     */
    public EnemigoEntidad(World world, Texture texture, Vector2 position, int selecEne){
        this.world=world;
        this.texture=texture;
        this.setVivo(true);

        estadisticas(selecEne);
        switch (selecEne){
            case 1:{
                this.setId(1);
                break;
            }
            case 2:{
                this.setId(2);
                break;
            }
            case 3:{
                this.setJefe(true);
                break;
            }
        }

        BodyDef def = new BodyDef();
        def.position.set(position);
        def.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(def);

        PolygonShape box = new PolygonShape();
        box.setAsBox(0.5f, 0.5f);
        fixture = body.createFixture(box, 1);
        fixture.setUserData("enemy");
        box.dispose();

        setSize(PIXELS_IN_METERS, PIXELS_IN_METERS);
    }

    @Override
    /**
     * Este método dibuja a la entidad donde corresponde con su textura
     */
    public void draw(Batch batch, float parentAlpha) {
        setPosition((body.getPosition().x)*PIXELS_IN_METERS,
                    (body.getPosition().y)*PIXELS_IN_METERS);
        batch.draw(texture, getX(), getY(), getWidth(), getHeight());
    }

    @Override
    /**
     * En este método se controla la actuación de la entidad, en este caso, se le impide rebotar por el mapa cuando entra en contacto con otras entidades
     */
    public void act(float delta) {
        if(!movimiento){
            body.setLinearVelocity(0,0);
        }
    }

    /**
     * Este método elimina al actor del mundo
     */
    public void detach(){
        body.destroyFixture(fixture);
        world.destroyBody(body);
    }

    /**
     * Este método resta vida de la entidad y controla cuando el actor muere
     * @param daño
     */
    public void recibirDaño(int daño){
        this.setVida(this.getVida()-daño);
        if (this.getVida()<=0){
            this.setVivo(false);
        }
    }

    /**
     * Este método asigna las estadísticas a la entidad
     * @param selecEne
     */
    public void estadisticas(int selecEne){
        switch (selecEne){
            case 1:{
                this.setVida(VIDA_S);
                this.setDaño(DAÑO_S);
                break;
            }
            case 2:{
                this.setVida(VIDA_N);
                this.setDaño(DAÑO_N);
                break;
            }
            case 3:{
                this.setVida(VIDA_O);
                this.setDaño(DAÑO_O);
                break;
            }
        }
    }
}
