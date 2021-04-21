package com.destroytheai.Entidades;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.destroytheai.Constantes;
import com.destroytheai.Mecanicas.Controler;
import com.destroytheai.Mecanicas.SimpleDirectionGestureDetector;

import static com.destroytheai.Constantes.PIXELS_IN_METERS;

public class PersonajeEntidad extends Actor {

    private Texture texture;
    private World world;
    private Body body;
    private Fixture fixture;
    private int vida = 10;
    private boolean movimiento = true;
    private boolean colision = false;
    private boolean vivo = true;
    Controler controler = new Controler();

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

    public PersonajeEntidad(World world, Texture texture, Vector2 position){
        this.world=world;
        this.texture=texture;

        BodyDef def = new BodyDef();
        def.position.set(position);
        def.type = BodyDef.BodyType.KinematicBody;
        body = world.createBody(def);

        PolygonShape box = new PolygonShape();
        box.setAsBox(0.5f, 0.5f);
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
        if(vida<=0){
            vivo=false;
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
}
