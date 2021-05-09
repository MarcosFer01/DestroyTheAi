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

import static com.destroytheai.Constantes.PIXELS_IN_METERS;

public class SuelosEntidad extends Actor {

    private Texture texture;
    private World world;
    private Body body;
    private Fixture fixture;

    /**
     * En el constructor de esta clase se crea el cuerpo del actor y se posiciona en el mapa.
     * @param world
     * @param texture
     * @param position
     */
    public SuelosEntidad(World world, Texture texture, Vector2 position){
        this.world=world;
        this.texture=texture;

        BodyDef def = new BodyDef();
        def.position.set(position);
        body = world.createBody(def);

        PolygonShape box = new PolygonShape();
        box.setAsBox(0.5f, 0.5f);
        fixture = body.createFixture(box, 0);
        fixture.setUserData("floor");
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

    /**
     * Este método elimina al actor del mundo
     */
    public void detach(){
        body.destroyFixture(fixture);
        world.destroyBody(body);
    }
}
