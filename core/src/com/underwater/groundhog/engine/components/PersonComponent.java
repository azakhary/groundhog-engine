package com.underwater.groundhog.engine.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.files.FileHandle;
import com.underwater.groundhog.engine.compiler.GSInterpreter;
import com.underwater.groundhog.engine.compiler.GSReader;

/**
 * Created by azakhary on 4/8/2016.
 */
public class PersonComponent implements Component {

    private String name;

    /**
     * BRAIN
     */
    private GSInterpreter gsInterpreter;

    /**
     * END BRAIN
     */

    public PersonComponent(Entity entity, Engine engine, FileHandle script) {
        GSReader gsReader = new GSReader(script);
        gsInterpreter = new GSInterpreter(gsReader);
        gsInterpreter.setEngine(engine);
        gsInterpreter.setEntity(entity);
        gsInterpreter.execute();
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void tick(float deltaTime) {
        gsInterpreter.tick(deltaTime);
    }
}
