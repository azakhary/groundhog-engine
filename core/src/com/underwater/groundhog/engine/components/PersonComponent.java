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
    public String name;
}
