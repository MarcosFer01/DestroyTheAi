package com.destroytheai.Entidades;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.destroytheai.Mecanicas.Controler;

import static com.destroytheai.Constantes.DAÑO_A;
import static com.destroytheai.Constantes.DAÑO_G;
import static com.destroytheai.Constantes.DAÑO_M;
import static com.destroytheai.Constantes.PIXELS_IN_METERS;
import static com.destroytheai.Constantes.VIDA_A;
import static com.destroytheai.Constantes.VIDA_G;
import static com.destroytheai.Constantes.VIDA_M;

public class PersonajeEntidad extends Actor {

    private Texture texture;
    private World world;
    private Body body;
    private Fixture fixture;
    private int id;
    private int vida;
    private int vidaMax;
    private int oro = 0;
    private int daño;
    private boolean movimiento = true;
    private boolean colision = false;
    private boolean vivo = true;
    Controler controler = new Controler();

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getVida() {
        return vida;
    }
    public void setVida(int vida) {
        this.vida = vida;
    }
    public boolean isMovimiento() {
        return movimiento;
    }
    public void setMovimiento(boolean movimiento) {
        this.movimiento = movimiento;
    }
    public boolean isVivo() {
        return vivo;
    }
    public void setVivo(boolean vivo) {
        this.vivo = vivo;
    }
    public boolean isColision() {
        return colision;
    }
    public void setColision(boolean colision) {
        this.colision = colision;
    }
    public int getVidaMax() {
        return vidaMax;
    }
    public void setVidaMax(int vidaMax) {
        this.vidaMax = vidaMax;
    }
    public int getOro() {
        return oro;
    }
    public void setOro(int oro) {
        this.oro = oro;
    }
    public int getDaño() {
        return daño;
    }
    public void setDaño(int daño) {
        this.daño = daño;
    }

    public PersonajeEntidad(World world, Texture texture, Vector2 position, int selecPer){
        this.world=world;
        this.texture=texture;
        this.setVivo(true);

        estadisticas(selecPer);
        switch (selecPer){
            case 1:{
                this.setId(1);
                break;
            }
            case 2:{
                this.setId(2);
                break;
            }
            case 3:{
                this.setId(3);
                break;
            }
        }

        BodyDef def = new BodyDef();
        def.position.set(position);
        def.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(def);

        PolygonShape box = new PolygonShape();
        box.setAsBox(0.4f, 0.4f);
        fixture = body.createFixture(box, 1);
        fixture.setUserData("player");
        box.dispose();

        setSize(PIXELS_IN_METERS, PIXELS_IN_METERS);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        setPosition((body.getPosition().x)*PIXELS_IN_METERS,
                    (body.getPosition().y)*PIXELS_IN_METERS);
        batch.draw(texture, getX(), getY(), getWidth(), getHeight());
    }

    @Override
    public void act(float delta) {
        if(!movimiento){
            body.setLinearVelocity(0,0);
        }
        handleInput();
    }

    public void handleInput(){
        if(controler.isUp() && colision==false){
            moverse(0);
        } else{
            movimiento = false;
        }
        if(controler.isRight() && colision==false){
            moverse(1);
        } else{
            movimiento = false;
        }
        if(controler.isDown() && colision==false){
            moverse(2);
        } else{
            movimiento = false;
        }
        if(controler.isLeft() && colision==false){
            moverse(3);
        } else{
            movimiento = false;
        }
    }

    public void moverse(int dir) {
        switch(dir){
            case 0:{
                body.setLinearVelocity(0,8);
                break;
            }
            case 1:{
                body.setLinearVelocity(8,0);
                break;
            }
            case 2:{
                body.setLinearVelocity(0,-8);
                break;
            }
            case 3:{
                body.setLinearVelocity(-8,0);
                break;
            }
        }
    }

    public void detach(){
        body.destroyFixture(fixture);
        world.destroyBody(body);
    }

    public void recibirDaño(int daño){
        this.setVida(this.getVida()-daño);
        if (this.getVida()<=0){
            this.setVivo(false);
        }
    }

    public void curar(){
        this.setVida(this.getVidaMax());
        this.setOro(this.getOro()-10);
    }

    public void estadisticas(int selecPer){
        switch (selecPer){
            case 1:{
                this.setVidaMax(VIDA_G);
                this.setVida(VIDA_G);
                this.setDaño(DAÑO_G);
                break;
            }
            case 2:{
                this.setVidaMax(VIDA_A);
                this.setVida(VIDA_A);
                this.setDaño(DAÑO_A);
                break;
            }
            case 3:{
                this.setVidaMax(VIDA_M);
                this.setVida(VIDA_M);
                this.setDaño(DAÑO_M);
                break;
            }
        }
    }
}
