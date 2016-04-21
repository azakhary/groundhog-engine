package com.underwater.groundhog.engine.compiler.micro.commands;

import com.badlogic.ashley.core.Entity;
import com.underwater.groundhog.engine.components.ThingComponent;
import com.underwater.groundhog.engine.components.WorldComponent;

/**
 * Created by avetiszakharyan on 4/21/16.
 */
public class WalkToObjectCommand extends WalkToPosCommand {

    private Entity targetEntity;

    @Override
    public void init(String[] args) {
        String expression = args[0];
        String name = interpreter.processExpression(expression).name;
        targetEntity = interpreter.getParentWorldComponent().getObject(name);
    }

    @Override
    public void tick(float delta) {
        ThingComponent thingComponent = thingComponentMapper.get(targetEntity);
        target.set(thingComponent.x, thingComponent.y);
        super.tick(delta);
    }
}
