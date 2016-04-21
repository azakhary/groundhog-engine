package com.underwater.groundhog.engine.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.underwater.groundhog.engine.compiler.GSInterpreter;
import com.underwater.groundhog.engine.components.BrainComponent;
import com.underwater.groundhog.engine.components.PersonComponent;

/**
 * Created by azakhary on 4/8/2016.
 */
public class BrainSystem extends IteratingSystem {

    private ComponentMapper<BrainComponent> brainMapper = ComponentMapper.getFor(BrainComponent.class);

    public BrainSystem() {
        super(Family.all(BrainComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        BrainComponent brain = brainMapper.get(entity);
        GSInterpreter gsInterpreter = brain.gsInterpreter;
        if(gsInterpreter != null) {
            gsInterpreter.tick(deltaTime);
        }
    }
}
