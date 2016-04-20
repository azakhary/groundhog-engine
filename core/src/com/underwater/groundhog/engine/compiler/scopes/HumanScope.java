package com.underwater.groundhog.engine.compiler.scopes;

import com.badlogic.ashley.core.Entity;

/**
 * Created by avetiszakharyan on 4/20/16.
 */
public class HumanScope extends ThingScope {

    public DataScope stats = new DataScope("stats");

    public HumanScope(String name, Entity entity) {
        super(name, entity);
    }

    @Override
    public DataScope get(String scope) {
        if(scope.equals("stats")) {
            return stats;
        } else {
            return super.get(scope);
        }
    }

    @Override
    public boolean contains(String scope) {
        if(scope.equals("stats")) {
            return true;
        } else {
            return super.contains(scope);
        }
    }
}
