package com.underwater.groundhog.engine.components;

import com.badlogic.ashley.core.Component;
import com.underwater.groundhog.engine.compiler.GSInterpreter;

/**
 * Created by avetiszakharyan on 4/21/16.
 */
public class BrainComponent implements Component {

    public String id;

    public GSInterpreter gsInterpreter;

}
