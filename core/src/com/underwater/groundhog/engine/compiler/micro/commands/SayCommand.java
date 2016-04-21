package com.underwater.groundhog.engine.compiler.micro.commands;

import com.underwater.groundhog.engine.compiler.micro.MicroCommand;
import com.underwater.groundhog.engine.components.PersonComponent;

/**
 * Created by avetiszakharyan on 4/20/16.
 */
public class SayCommand extends MicroCommand {

    @Override
    public void init(String[] args) {
        PersonComponent personComponent = interpreter.entity.getComponent(PersonComponent.class);
        if(personComponent != null) {
            System.out.println(personComponent.name + ":" + args[0]);
        } else {
            System.out.println("LOG: " + args[0]);
        }
        endCommand();
    }
}
