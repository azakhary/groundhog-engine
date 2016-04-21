package com.underwater.groundhog.engine.compiler.micro.commands;

import com.underwater.groundhog.engine.TriggerManager;
import com.underwater.groundhog.engine.compiler.micro.MicroCommand;

/**
 * Created by avetiszakharyan on 4/21/16.
 */
public class ItemInteractCommand extends MicroCommand {

    private float delay;

    @Override
    public void init(String[] args) {
        super.init();
        interruptable = false;
        delay = 0.2f;
        TriggerManager.get().registerEvent("interaction", args[0]+"."+args[1]);
    }

    public void tick(float delta) {
        super.tick(delta);
        if(time > delay) {
            endCommand();
        }
    }
}
