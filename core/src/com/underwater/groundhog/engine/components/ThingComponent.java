package com.underwater.groundhog.engine.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.underwater.groundhog.engine.compiler.scopes.DataScope;

/**
 * Created by azakhary on 4/8/2016.
 */
public class ThingComponent implements Component {

    public float x;
    public float y;

    private float width;
    private float height;

    public DataScope scope;

    public Entity parentWorld;

    public ThingComponent(String id, Entity world) {
        scope = new DataScope(id);
        parentWorld = world;
    }

    public void setSize(float width, float height) {
        this.width = width;
        this.height = height;
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }
}
