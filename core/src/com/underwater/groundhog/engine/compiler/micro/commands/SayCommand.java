package com.underwater.groundhog.engine.compiler.micro.commands;

import com.underwater.groundhog.engine.compiler.micro.MicroCommand;
import com.underwater.groundhog.engine.components.PersonComponent;
import com.underwater.groundhog.engine.components.ThingComponent;
import com.underwater.groundhog.engine.systems.LabelSystem;

/**
 * Created by avetiszakharyan on 4/20/16.
 */
public class SayCommand extends MicroCommand {

    @Override
    public void init(String[] args) {
        PersonComponent personComponent = interpreter.entity.getComponent(PersonComponent.class);
        ThingComponent thing = interpreter.entity.getComponent(ThingComponent.class);
        String text = "LOG: " + args[0];
        if(personComponent != null) {
            text = personComponent.name + ":" + args[0];
        }
        System.out.println(text);

        if(thing != null) {
            interpreter.engine.getSystem(LabelSystem.class).addLbl(args[0], thing);
        }

        endCommand();
    }
}
