package com.underwater.groundhog.engine.compiler.scopes;

import java.util.HashMap;

/**
 * Created by avetiszakharyan on 4/20/16.
 */
public class DataScope {

    private HashMap<String, DataScope> items = new HashMap<String, DataScope>();

    private String value = null;

    public DataScope() {

    }

    public DataScope(String value) {
        this.value = value;
    }

    public DataScope get(String scope) {
        return items.get(scope);
    }

    public void addScope(String name) {
        items.put(name, new DataScope());
    }

    public void addScope(String name, DataScope scope) {
        items.put(name, scope);
    }

    public String value() {
        return value;
    }

    public static DataScope valueObj(String str) {
        DataScope scope = new DataScope(str);
        return scope;
    }
}