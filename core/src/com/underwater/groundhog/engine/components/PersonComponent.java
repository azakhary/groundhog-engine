package com.underwater.groundhog.engine.components;

import com.badlogic.ashley.core.Component;

/**
 * Created by azakhary on 4/8/2016.
 */
public class PersonComponent implements Component {

    private String name;


    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
