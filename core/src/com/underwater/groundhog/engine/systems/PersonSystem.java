package com.underwater.groundhog.engine.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.underwater.groundhog.engine.components.PersonComponent;

/**
 * Created by azakhary on 4/8/2016.
 */
public class PersonSystem extends IteratingSystem {

    public PersonSystem() {
        super(Family.all(PersonComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {

    }
}
