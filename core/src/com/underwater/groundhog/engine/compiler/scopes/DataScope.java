package com.underwater.groundhog.engine.compiler.scopes;

import com.underwater.groundhog.engine.TriggerManager;

import java.util.HashMap;

/**
 * Created by avetiszakharyan on 4/20/16.
 */
public class DataScope {

    private HashMap<String, DataScope> items = new HashMap<String, DataScope>();

    private String value = "";

    public String name = "";

    public String parent = "";

    public DataScope(String name) {
        this.name = name;
    }

    public DataScope(String name, String value) {
        this.value = value;
        this.name = name;
    }

    public DataScope get(String scope) {
        if(!items.containsKey(scope)) {
            addScope(scope);
        }
        return items.get(scope);
    }

    public boolean contains(String scope) {
        return items.containsKey(scope);
    }

    public void addScope(String name) {
        DataScope newScope = new DataScope(name);
        addScope(name, newScope);
    }

    public void addScope(String name, DataScope scope) {
        scope.parent = parent+this.name+".";
        items.put(name, scope);
    }

    public String value() {
        return value;
    }

    public void setValue(String string) {
        if(value.equals(string)) return;
        String packageName = parent + name;
        TriggerManager.get().registerEvent("value", packageName);
        value = new String(string);
    }

    public static DataScope valueObj(String str) {
        DataScope scope = new DataScope("", str);
        return scope;
    }
}
