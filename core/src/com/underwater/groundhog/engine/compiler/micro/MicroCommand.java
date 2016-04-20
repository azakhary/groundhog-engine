package com.underwater.groundhog.engine.compiler.micro;

import com.underwater.groundhog.engine.compiler.GSInterpreter;

/**
 * Created by avetiszakharyan on 4/20/16.
 */
public abstract class MicroCommand {

    protected GSInterpreter interpreter;

    protected float time = 0;

    public void setInterpreter(GSInterpreter interpreter) {
        this.interpreter = interpreter;
    }

    public void tick(float delta) {
        time += delta;
    }

    public abstract void init(String[] args);

    public void init() {
        time = 0;
    }

    protected void endCommand() {
        interpreter.endCommand();
    }


}
