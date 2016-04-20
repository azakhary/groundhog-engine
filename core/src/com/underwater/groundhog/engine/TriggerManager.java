package com.underwater.groundhog.engine;

import com.badlogic.gdx.utils.Array;
import java.util.HashMap;

/**
 * Created by avetiszakharyan on 4/21/16.
 */
public class TriggerManager {

    private static TriggerManager instance = null;

    private HashMap<String, Array<String>> pendingEvents = new HashMap<String, Array<String>>();
    private HashMap<String, Array<String>> events = new HashMap<String, Array<String>>();

    private TriggerManager() {

    }

    public static TriggerManager get() {
        if(instance == null) {
            instance = new TriggerManager();
        }

        return instance;
    }


    public void registerEvent(String type, String arg) {
        //System.out.println("event: " + type + " " + arg);
        if(!pendingEvents.containsKey(type)) {
            pendingEvents.put(type, new Array<String>());
        }

        pendingEvents.get(type).add(arg);
    }

    public void resetEvents() {
        events = new HashMap<String, Array<String>>(pendingEvents);
        pendingEvents.clear();
    }

    public boolean checkForEvent(String type, String arg) {
        if(events.get(type) == null) return false;

        if(events.get(type).contains(arg, false)) {
            return true;
        }

        return false;
    }

}
