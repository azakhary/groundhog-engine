package com.underwater.groundhog.engine.compiler.micro.commands;

import com.underwater.groundhog.engine.compiler.micro.MicroCommand;

/**
 * Created by avetiszakharyan on 4/20/16.
 */
public class DelayCommand extends MicroCommand {

    private float delay;

    @Override
    public void init(String[] args) {
        delay = Float.parseFloat( args[0] );
        super.init();
    }

    public void tick(float delta) {
        super.tick(delta);
        if(time > delay) {
            endCommand();
        }
    }
}
