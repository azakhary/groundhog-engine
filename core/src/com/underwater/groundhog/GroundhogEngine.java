package com.underwater.groundhog;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.underwater.groundhog.engine.TriggerManager;
import com.underwater.groundhog.engine.compiler.scopes.DataScope;
import com.underwater.groundhog.engine.compiler.scopes.HumanScope;
import com.underwater.groundhog.engine.compiler.scopes.ThingScope;
import com.underwater.groundhog.engine.components.ItemComponent;
import com.underwater.groundhog.engine.components.PersonComponent;
import com.underwater.groundhog.engine.components.ThingComponent;
import com.underwater.groundhog.engine.systems.GameSystem;
import com.underwater.groundhog.engine.systems.PersonSystem;

public class GroundhogEngine extends ApplicationAdapter {

	private Viewport viewport;
	private SpriteBatch batch;
	private Engine engine;
	private ShapeRenderer shapeRenderer;

	private Vector2[] humans;
	private Vector2[] items;

	private GameSystem gameSystem;

	@Override
	public void create () {
		batch = new SpriteBatch();
		engine = new Engine();
		viewport = new FitViewport(400f, 400f);
		shapeRenderer = new ShapeRenderer();

		viewport.getCamera().position.set(0, 0, 0);

		PersonSystem personSystem = new PersonSystem();
		gameSystem = new GameSystem();
		engine.addSystem(personSystem);
		engine.addSystem(gameSystem);

		gameSystem.worldScope.addScope("items");
		gameSystem.worldScope.addScope("people");

		createHuman("bob", new Vector2(-100, 100));
		createHuman("jake", new Vector2(0, 100));
		createHuman("phill", new Vector2(100, 100));

		createItem("boxA", new Vector2(-50, -100));
		createItem("boxB", new Vector2(50, -100));

	}

	private void createHuman(String id, Vector2 pos) {
		Entity entity = new Entity();
		PersonComponent person = new PersonComponent(entity, engine, Gdx.files.internal(id+".gs"));
		person.id = id;
		ThingComponent thing = new ThingComponent(id);
		thing.scope = new HumanScope(id, entity);
		gameSystem.worldScope.get("people").addScope(id, thing.scope);
		gameSystem.addObject(id, entity);
		thing.setPosition(pos.x, pos.y);
		entity.add(thing);
		entity.add(person);
		engine.addEntity(entity);
	}

	private void createItem(String id, Vector2 pos) {
		Entity entity = new Entity();
		ItemComponent item = new ItemComponent();
		ThingComponent thing = new ThingComponent(id);
		thing.scope = new ThingScope(id, entity);
		gameSystem.worldScope.get("items").addScope(id, thing.scope);
		gameSystem.addObject(id, entity);
		thing.setPosition(pos.x, pos.y);
		entity.add(thing);
		entity.add(item);
		engine.addEntity(entity);
	}

	@Override
	public void render () {
		viewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		engine.update(Gdx.graphics.getDeltaTime());
		TriggerManager.get().resetEvents();

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.begin();
		shapeRenderer.setProjectionMatrix(viewport.getCamera().combined);
		shapeRenderer.begin(ShapeRenderer.ShapeType.Line);

		ImmutableArray<Entity> entities = engine.getEntities();
		for(Entity entity : entities) {
			ThingComponent thing = entity.getComponent(ThingComponent.class);
			PersonComponent person = entity.getComponent(PersonComponent.class);
			ItemComponent item = entity.getComponent(ItemComponent.class);
			shapeRenderer.setColor(0, 1, 0, 1);
			if(person != null) {
				shapeRenderer.circle(thing.getX(), thing.getY(), 10f);
			} else if(item != null) {
				shapeRenderer.rect(thing.getX()-10f, thing.getY()-10f, 20f, 20f);
			}
		}
		shapeRenderer.end();
		batch.end();
	}
}
