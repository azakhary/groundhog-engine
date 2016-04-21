package com.underwater.groundhog.engine.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.underwater.groundhog.engine.components.LabelComponent;
import com.underwater.groundhog.engine.components.ThingComponent;

/**
 * Created by avetiszakharyan on 4/21/16.
 */
public class LabelSystem extends IteratingSystem {

    ComponentMapper<LabelComponent> lblMapper = ComponentMapper.getFor(LabelComponent.class);

    Batch batch;
    BitmapFont font;
    Viewport viewport;
    Vector2 pos = new Vector2();

    public LabelSystem(Batch batch, Viewport viewport) {
        super(Family.all(LabelComponent.class).get());
        this.batch = batch;
        this.viewport = viewport;

        font = new BitmapFont(Gdx.files.internal("font.fnt"), false);
        font.getData().setScale(0.6f);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        LabelComponent labelComponent = lblMapper.get(entity);
        labelComponent.time -=  deltaTime;

        if(labelComponent.time <= 0) {
            getEngine().removeEntity(entity);
        }
    }

    public void draw() {
        for (int i = 0; i < getEntities().size(); ++i) {
            LabelComponent labelComponent = lblMapper.get(getEntities().get(i));
            ThingComponent thingComponent = labelComponent.thing;
            pos.set(thingComponent.x, thingComponent.y);
            viewport.project(pos);
            font.setColor(1, 1, 1, labelComponent.time/3f);
            font.draw(batch, labelComponent.text, pos.x-50, pos.y+40);
            font.setColor(1, 1, 1, 1);
        }
        batch.flush();
    }

    public void addLbl(String text, ThingComponent thing) {
        Entity entity = new Entity();
        LabelComponent labelComponent = new LabelComponent();
        labelComponent.thing = thing;

        labelComponent.text = text;
        entity.add(labelComponent);
        getEngine().addEntity(entity);
    }
}
