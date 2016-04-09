package com.underwater.groundhog;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.underwater.groundhog.engine.compiler.GSInterpreter;
import com.underwater.groundhog.engine.compiler.GSReader;
import com.underwater.groundhog.engine.components.ItemComponent;
import com.underwater.groundhog.engine.components.PersonComponent;
import com.underwater.groundhog.engine.components.ThingComponent;
import com.underwater.groundhog.engine.systems.PersonSystem;

public class GroundhogEngine extends ApplicationAdapter {

	private Viewport viewport;
	private SpriteBatch batch;
	private Engine engine;
	private ShapeRenderer shapeRenderer;

	private Vector2[] humans;
	private Vector2[] items;

	@Override
	public void create () {
		batch = new SpriteBatch();
		engine = new Engine();
		viewport = new FitViewport(400f, 400f);
		shapeRenderer = new ShapeRenderer();

		GSReader gsReader = new GSReader(Gdx.files.internal("test.gs"));
		GSInterpreter interpreter = new GSInterpreter(gsReader);
		interpreter.execute();

		viewport.getCamera().position.set(0, 0, 0);

		PersonSystem personSystem = new PersonSystem();
		engine.addSystem(personSystem);

		humans = new Vector2[3];
		items = new Vector2[2];


		humans[0] = new Vector2(-100, 120);
		humans[1] = new Vector2(0, 120);
		humans[2] = new Vector2(100, 120);


		items[0] = new Vector2(-80, -80);
		items[1] = new Vector2(80, -80);


		for(Vector2 pos: humans) {
			createHuman(pos);
		}
		for(Vector2 pos: items) {
			createItem(pos);
		}
	}

	private void createHuman(Vector2 pos) {
		Entity entity = new Entity();
		PersonComponent person = new PersonComponent();
		person.setName("Gogi");
		ThingComponent thing = new ThingComponent();
		thing.setPosition(pos.x, pos.y);
		entity.add(thing);
		entity.add(person);
		engine.addEntity(entity);
	}

	private void createItem(Vector2 pos) {
		Entity entity = new Entity();
		ItemComponent item = new ItemComponent();
		ThingComponent thing = new ThingComponent();
		thing.setPosition(pos.x, pos.y);
		entity.add(thing);
		entity.add(item);
		engine.addEntity(entity);
	}

	@Override
	public void render () {
		viewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		engine.update(Gdx.graphics.getDeltaTime());

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
