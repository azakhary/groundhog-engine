package com.underwater.groundhog.engine.compiler.micro.commands;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.underwater.groundhog.engine.compiler.micro.MicroCommand;
import com.underwater.groundhog.engine.components.PersonComponent;
import com.underwater.groundhog.engine.components.ThingComponent;

/**
 * Created by avetiszakharyan on 4/20/16.
 */
public class WalkToItemCommand extends MicroCommand {

    Vector2 target = new Vector2();
    Vector2 tmp = new Vector2();
    Vector2 tmp2 = new Vector2();

    ComponentMapper<ThingComponent> thingComponentMapper = ComponentMapper.getFor(ThingComponent.class);

    @Override
    public void init(String[] args) {
        String expression = args[0];
        String value = interpreter.processExpression(expression);
        String[] parts = value.split(":");
        target.set(Float.parseFloat(parts[0]), Float.parseFloat(parts[1]));
    }

    @Override
    public void tick(float delta) {
        super.tick(delta);

        ThingComponent thingComponent = thingComponentMapper.get(interpreter.entity);
        boolean reached = moveTowardsTarget(thingComponent, target, 100f, delta);

        if(reached) {
            endCommand();
        }

    }

    public boolean moveTowardsTarget(ThingComponent pos, Vector2 target, float speed, float deltaTime) {
        Vector2 moveVector = tmp;
        Vector2 newMoveVector = tmp2;
        moveVector.set(target.x - pos.x, target.y - pos.y);
        pos.x += MathUtils.cos(moveVector.angleRad()) * speed * deltaTime;
        pos.y += MathUtils.sin(moveVector.angleRad()) * speed * deltaTime;
        newMoveVector.set(target.x - pos.x, target.y - pos.y);

        if(moveVector.dot(newMoveVector) < 0) {
            pos.x = target.x;
            pos.y = target.y;

            return true;
        }

        return false;
    }
}
