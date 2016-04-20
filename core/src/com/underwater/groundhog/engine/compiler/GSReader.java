package com.underwater.groundhog.engine.compiler;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by azakhary on 4/9/16.
 */
public class GSReader {

    public TagData agenda;
    public HashMap<String, State> states = new HashMap<String, State>();

    private FileHandle file;

    public GSReader(FileHandle file) {
        this.file = file;
    }

    public class State {
        public String name;
        public String mainScript;
        public Array<Trigger> triggers = new Array<Trigger>();
    }

    public class Trigger {
        public String[] args;
        public String script;
    }

    public class TagData {
        public String tag;
        public String param;
        public String data;
    }

    public void read() {
        String data = file.readString();
        data = data.replaceAll("\t", "");
        data = data.replaceAll("    ", "");

        agenda = readTag(data, "agenda");
        Array<TagData> stateTags = readTags(data, "state");

        for(TagData stateData: stateTags) {
            String stateName = stateData.param.replaceAll("\"", "");
            State state = new State();
            state.name = stateName;
            TagData mainScript = readTag(stateData.data, "main");
            if(mainScript != null) {
                state.mainScript = mainScript.data;
            }
            Array<TagData> triggersArr = readTags(stateData.data, "trigger");
            for(TagData triggerData: triggersArr) {
                Trigger trigger = new Trigger();
                String[] parts = triggerData.param.split(" ");
                trigger.args = parts;
                trigger.script = triggerData.data;
                state.triggers.add(trigger);
            }
            states.put(stateName, state);
        }
    }

    private TagData readTag(String source, String tag) {
        Array<TagData> result = readTags(source, tag);
        if(result.size > 0) {
            return result.get(0);
        } else {
            return null;
        }
    }

    private Array<TagData> readTags(String source, String tag) {
        Pattern pattern = Pattern.compile("\\["+tag+"(.*?)\\](.*?)\\[\\/"+tag+"\\]", Pattern.DOTALL);
        Matcher matcher = pattern.matcher(source);

        Array<TagData> result = new Array<TagData>();

        while(matcher.find()) {
            TagData tagData = new TagData();
            tagData.tag = tag;
            tagData.param = matcher.group(1).trim();
            tagData.data = matcher.group(2).trim();

            result.add(tagData);
        }
        return result;
    }

}
