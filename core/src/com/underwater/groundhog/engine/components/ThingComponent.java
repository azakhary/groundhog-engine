package com.underwater.groundhog.engine.components;

import com.badlogic.ashley.core.Component;

/**
 * Created by azakhary on 4/8/2016.
 */
public class ThingComponent implements Component {

    private float x;
    private float y;

    private float width;
    private float height;

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
