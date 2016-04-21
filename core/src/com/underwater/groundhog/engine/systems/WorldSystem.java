package com.underwater.groundhog.engine.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Vector2;
import com.underwater.groundhog.engine.TriggerManager;
import com.underwater.groundhog.engine.compiler.GSInterpreter;
import com.underwater.groundhog.engine.compiler.GSReader;
import com.underwater.groundhog.engine.compiler.scopes.HumanScope;
import com.underwater.groundhog.engine.compiler.scopes.ThingScope;
import com.underwater.groundhog.engine.components.*;

/**
 * Created by avetiszakharyan on 4/20/16.
 */
public class WorldSystem extends IteratingSystem {

    ComponentMapper<WorldComponent> worldMapper = ComponentMapper.getFor(WorldComponent.class);
    ComponentMapper<BrainComponent> brainMapper = ComponentMapper.getFor(BrainComponent.class);

    Family brainFamily = Family.all(BrainComponent.class).get();

    public WorldSystem() {
        super(Family.all(WorldComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        WorldComponent world = worldMapper.get(entity);
        BrainComponent worldBrain = brainMapper.get(entity);
        if(!world.worldStarted) {
            world.worldStarted = true;
            worldBrain.gsInterpreter.execute();
            TriggerManager.get().clearAll();
        }
    }

    public void createPerson(Entity worldEntity, String id, Vector2 pos) {
        WorldComponent world = worldMapper.get(worldEntity);
        Entity entity = new Entity();
        PersonComponent person = new PersonComponent();
        person.name = id;
        ThingComponent thing = new ThingComponent(id, worldEntity);
        thing.scope = new HumanScope(id, entity);
        world.worldScope.get("people").addScope(id, thing.scope);
        world.addObject(id, entity);
        thing.setPosition(pos.x, pos.y);
        entity.add(thing);
        entity.add(person);
        getEngine().addEntity(entity);
        setInterpreter(getEngine(), entity, id).execute();
    }

    public void createItem(Entity worldEntity, String id, Vector2 pos) {
        WorldComponent world = worldMapper.get(worldEntity);
        Entity entity = new Entity();
        ItemComponent item = new ItemComponent();
        ThingComponent thing = new ThingComponent(id, worldEntity);
        thing.scope = new ThingScope(id, entity);
        world.worldScope.get("items").addScope(id, thing.scope);
        world.addObject(id, entity);
        thing.setPosition(pos.x, pos.y);
        entity.add(thing);
        entity.add(item);
        getEngine().addEntity(entity);
        setInterpreter(getEngine(), entity, id).execute();
    }

    public static GSInterpreter setInterpreter(Engine engine, Entity entity, String id) {
        FileHandle fileHandle = Gdx.files.internal(id+".gs");
        GSReader gsReader = new GSReader(fileHandle);
        GSInterpreter gsInterpreter = new GSInterpreter(gsReader);
        gsInterpreter.setEngine(engine);
        gsInterpreter.setEntity(entity);

        BrainComponent brain = entity.getComponent(BrainComponent.class);
        if(brain == null) {
            brain = new BrainComponent();
            brain.id = id;
            brain.gsInterpreter = gsInterpreter;
            entity.add(brain);
        }

        return gsInterpreter;
    }
}
