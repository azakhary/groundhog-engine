package com.underwater.groundhog.engine.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.underwater.groundhog.engine.compiler.scopes.DataScope;

import java.util.HashMap;

/**
 * Created by avetiszakharyan on 4/20/16.
 */
public class GameSystem extends EntitySystem {

    public DataScope worldScope = new DataScope("world");

    public HashMap<String, Entity> objects = new HashMap<String, Entity>();

    public GameSystem() {

    }

    public Entity getObject(String name) {
        return objects.get(name);
    }

    public void addObject(String name, Entity entity) {
        objects.put(name, entity);
    }
}
