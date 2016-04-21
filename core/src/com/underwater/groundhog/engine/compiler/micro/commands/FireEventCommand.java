package com.underwater.groundhog.engine.compiler.micro.commands;

import com.underwater.groundhog.engine.TriggerManager;
import com.underwater.groundhog.engine.compiler.micro.MicroCommand;

/**
 * Created by avetiszakharyan on 4/21/16.
 */
public class FireEventCommand extends MicroCommand {

    @Override
    public void init(String[] args) {
        TriggerManager.get().registerEvent("event", args[0]);
        endCommand();
    }
}
