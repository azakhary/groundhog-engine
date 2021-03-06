package com.underwater.groundhog.engine.compiler.scopes;

import com.badlogic.ashley.core.Entity;
import com.underwater.groundhog.engine.components.ThingComponent;

/**
 * Created by avetiszakharyan on 4/20/16.
 */
public class ThingScope extends DataScope {

    protected Entity entity;

    public ThingScope(String name, Entity entity) {
        super(name);
        this.entity = entity;
    }

    @Override
    public DataScope get(String scope) {
        if(scope.equals("position")) {
            ThingComponent thingComponent = entity.getComponent(ThingComponent.class);
            return DataScope.valueObj(thingComponent.getX() + ":" + thingComponent.getY());
        } else {
            return super.get(scope);
        }
    }

    @Override
    public boolean contains(String scope) {
        if(scope.equals("position")) {
            return true;
        } else {
            return super.contains(scope);
        }
    }

}
