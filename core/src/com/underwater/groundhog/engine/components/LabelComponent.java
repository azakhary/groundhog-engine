package com.underwater.groundhog.engine.components;

import com.badlogic.ashley.core.Component;

/**
 * Created by avetiszakharyan on 4/21/16.
 */
public class LabelComponent implements Component {

    public String text;
    public ThingComponent thing;

    public float time = 3f;
}
