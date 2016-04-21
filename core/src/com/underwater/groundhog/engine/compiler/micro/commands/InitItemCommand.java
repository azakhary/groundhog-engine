package com.underwater.groundhog.engine.compiler.micro.commands;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.underwater.groundhog.engine.compiler.micro.MicroCommand;
import com.underwater.groundhog.engine.systems.WorldSystem;

/**
 * Created by avetiszakharyan on 4/21/16.
 */
public class InitItemCommand extends MicroCommand {
    @Override
    public void init(String[] args) {
        String id = args[0];
        Vector2 pos = interpreter.parsePos(args[1]);
        Entity world = interpreter.getParentWorld();
        interpreter.engine.getSystem(WorldSystem.class).createItem(world, id, pos);
        endCommand();
    }
}
