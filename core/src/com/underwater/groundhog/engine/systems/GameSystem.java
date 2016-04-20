package com.underwater.groundhog.engine.systems;

import com.badlogic.ashley.core.EntitySystem;
import com.underwater.groundhog.engine.compiler.scopes.DataScope;

/**
 * Created by avetiszakharyan on 4/20/16.
 */
public class GameSystem extends EntitySystem {

    public DataScope worldScope = new DataScope();

    public GameSystem() {

    }
}
