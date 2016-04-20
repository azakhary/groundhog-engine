package com.underwater.groundhog.engine.compiler.micro.commands;

import com.underwater.groundhog.engine.compiler.micro.MicroCommand;

/**
 * Created by avetiszakharyan on 4/20/16.
 */
public class GotoCommand extends MicroCommand {
    @Override
    public void init(String[] args) {
        interpreter.goTo(args[0]);
        endCommand();
    }
}
