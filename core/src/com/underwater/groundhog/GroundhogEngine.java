package com.underwater.groundhog;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.underwater.groundhog.engine.TriggerManager;
import com.underwater.groundhog.engine.compiler.GSInterpreter;
import com.underwater.groundhog.engine.compiler.GSReader;
import com.underwater.groundhog.engine.compiler.scopes.HumanScope;
import com.underwater.groundhog.engine.compiler.scopes.ThingScope;
import com.underwater.groundhog.engine.components.*;
import com.underwater.groundhog.engine.systems.WorldSystem;
import com.underwater.groundhog.engine.systems.BrainSystem;

public class GroundhogEngine extends ApplicationAdapter {

	private Viewport viewport;
	private SpriteBatch batch;
	private Engine engine;
	private ShapeRenderer shapeRenderer;


	private WorldSystem gameSystem;
	private WorldComponent world;
	private Entity worldEntity;

	private Family thingFamily = Family.all(ThingComponent.class).get();

	@Override
	public void create () {
		batch = new SpriteBatch();
		engine = new Engine();
		viewport = new FitViewport(400f, 400f);
		shapeRenderer = new ShapeRenderer();

		viewport.getCamera().position.set(0, 0, 0);

		BrainSystem personSystem = new BrainSystem();
		gameSystem = new WorldSystem();
		engine.addSystem(personSystem);
		engine.addSystem(gameSystem);

		createWorld();
		world.worldScope.addScope("items");
		world.worldScope.addScope("people");
	}

	private void createWorld() {
		worldEntity = new Entity();
		world = new WorldComponent();
		worldEntity.add(world);
		engine.addEntity(worldEntity);
		WorldSystem.setInterpreter(engine, worldEntity, "world");
	}

	@Override
	public void render () {
		viewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		engine.update(Gdx.graphics.getDeltaTime());

		if(world.worldScope.get("light").value().equals("on")) {
			Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
		} else {
			Gdx.gl.glClearColor(0, 0, 0, 1);
		}
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.begin();
		shapeRenderer.setProjectionMatrix(viewport.getCamera().combined);
		shapeRenderer.begin(ShapeRenderer.ShapeType.Line);

		ImmutableArray<Entity> entities = engine.getEntitiesFor(thingFamily);

		for(Entity entity : entities) {
			ThingComponent thing = entity.getComponent(ThingComponent.class);
			BrainComponent brain = entity.getComponent(BrainComponent.class);
			PersonComponent person = entity.getComponent(PersonComponent.class);
			ItemComponent item = entity.getComponent(ItemComponent.class);
			shapeRenderer.setColor(0, 1, 0, 1);
			if(person != null) {
				shapeRenderer.circle(thing.getX(), thing.getY(), 10f);
			} else if(item != null) {
				Rectangle rect = new Rectangle(thing.getX()-10f, thing.getY()-10f, 20f, 20f);
				if(Gdx.input.justTouched()) {
					Vector2 point = new Vector2(Gdx.input.getX(), Gdx.input.getY());
					viewport.unproject(point);
					if(rect.contains(point)) {
						TriggerManager.get().registerEvent("interaction", brain.id +"."+"button_press");
					}
				}
				shapeRenderer.rect(rect.x, rect.y, rect.width, rect.height);
			}
		}
		shapeRenderer.end();
		batch.end();

		TriggerManager.get().resetEvents();
	}
}
