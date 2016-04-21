package com.underwater.groundhog.engine.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.underwater.groundhog.engine.compiler.scopes.DataScope;

import java.util.HashMap;

/**
 * Created by avetiszakharyan on 4/21/16.
 */
public class WorldComponent implements Component {

    public DataScope worldScope = new DataScope("world");

    public HashMap<String, Entity> objects = new HashMap<String, Entity>();

    public boolean worldStarted = false;

    public Entity getObject(String name) {
        return objects.get(name);
    }

    public void addObject(String name, Entity entity) {
        objects.put(name, entity);
    }
}
