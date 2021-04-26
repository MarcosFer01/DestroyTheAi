package com.destroytheai.Scene2D;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.destroytheai.BaseScreen;
import com.destroytheai.MainGame;

public class Box2DScreen extends BaseScreen {



    public Box2DScreen(MainGame game) {
        super(game);
    }

    private World world;
    private Box2DDebugRenderer renderer;
    private OrthographicCamera camera;
    private Body personajeBody, sueloBody, enemigoBody;
    private Fixture personajeFixture, sueloFixture, enemigoFixture;
    private boolean colision;

    @Override
    public void show() {
        world = new World(new Vector2(0, (float) -9.81), true);
        renderer = new Box2DDebugRenderer();
        camera = new OrthographicCamera(16, 9);
        camera.translate(0,1);

        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                Fixture fixtureA = contact.getFixtureA(), fixtureB = contact.getFixtureB();

                if((fixtureA.getUserData().equals("player") && fixtureB.getUserData().equals("floor")) ||
                        (fixtureA.getUserData().equals("floor") && fixtureB.getUserData().equals("player"))){
                    colision = true;
                }

                if((fixtureA.getUserData().equals("player") && fixtureB.getUserData().equals("foe")) ||
                        (fixtureA.getUserData().equals("foe") && fixtureB.getUserData().equals("player"))){
                    colision = true;
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

        personajeBody = world.createBody(createPersonajeDef());
        sueloBody = world.createBody(createSueloBodyDef());
        enemigoBody  = world.createBody(createEnemigobody(6, 0.5f));

        PolygonShape personajeShape = new PolygonShape();
        personajeShape.setAsBox(0.5f, 0.5f);
        personajeFixture = personajeBody.createFixture(personajeShape, 1);
        personajeShape.dispose();

        PolygonShape enemigoShape = new PolygonShape();
        enemigoShape.setAsBox(0.5f, 0.5f);
        enemigoFixture = enemigoBody.createFixture(enemigoShape, 1);
        enemigoShape.dispose();

        PolygonShape sueloShape = new PolygonShape();
        sueloShape.setAsBox(500, 1);
        sueloFixture = sueloBody.createFixture(sueloShape, 1);
        sueloShape.dispose();

        personajeFixture.setUserData("player");
        sueloFixture.setUserData("floor");
        enemigoFixture.setUserData("foe");
    }

    private BodyDef createEnemigobody(float x, float y) {
        BodyDef def = new BodyDef();
        def.position.set(x, y);
        def.type = BodyDef.BodyType.StaticBody;
        return def;
    }

    private BodyDef createSueloBodyDef() {
        BodyDef def = new BodyDef();
        def.position.set(0, -1);
        def.type = BodyDef.BodyType.StaticBody;
        return def;
    }

    private BodyDef createPersonajeDef() {
        BodyDef def = new BodyDef();
        def.position.set(0, 0.5f);
        def.type = BodyDef.BodyType.DynamicBody;
        return def;
    }

    @Override
    public void dispose() {
        sueloBody.destroyFixture(sueloFixture);
        personajeBody.destroyFixture(personajeFixture);
        enemigoBody.destroyFixture(enemigoFixture);
        world.destroyBody(personajeBody);
        world.destroyBody(sueloBody);
        world.destroyBody(enemigoBody);
        world.dispose();
        renderer.dispose();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if(Gdx.input.justTouched() && colision){
            colision = false;
            saltar();
        }

        personajeBody.setLinearVelocity(5, personajeBody.getLinearVelocity().y);

        world.step(delta, 6, 2);
        camera.update();
        renderer.render(world, camera.combined);
    }

    private void saltar(){
        Vector2 position = personajeBody.getPosition();
        personajeBody.applyLinearImpulse(0, 10, position.x, position.y, true);
    }
}
