package com.underwater.groundhog.engine.compiler.micro.commands;

import com.underwater.groundhog.engine.compiler.micro.MicroCommand;

/**
 * Created by avetiszakharyan on 4/20/16.
 */
public class ChangeStateCommand extends MicroCommand {

    @Override
    public void init(String[] args) {
        String state = args[0];

        interpreter.changeState(state);
    }
}
