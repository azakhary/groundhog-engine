package com.underwater.groundhog.engine.compiler;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.underwater.groundhog.engine.compiler.micro.MicroCommand;
import com.underwater.groundhog.engine.compiler.micro.commands.*;
import com.underwater.groundhog.engine.compiler.scopes.DataScope;
import com.underwater.groundhog.engine.systems.GameSystem;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by avetiszakharyan on 4/9/16.
 */
public class GSInterpreter {

    private GSReader reader;

    private ArrayList<CommandLine> currCommands = new ArrayList<CommandLine>();

    private HashMap<String, MicroCommand> microMap = new HashMap<String, MicroCommand>();

    private MicroCommand currCommand = null;
    private int currCommandIndex = -1;
    private boolean isScriptRunning = false;
    public Engine engine;
    public Entity entity;

    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
    }

    public class CommandLine {
        public String command;
        public String[] args;

    }

    public GSInterpreter(GSReader reader) {
        this.reader = reader;
        reader.read();

        init();
    }

    private void init() {
        microMap.put("delay", new DelayCommand());
        microMap.put("say", new SayCommand());
        microMap.put("change_state", new ChangeStateCommand());
        microMap.put("walk_to_position", new WalkToPosCommand());
        microMap.put("walk_to_item", new WalkToItemCommand());
    }

    public void execute() {
        executeAgenda();
    }

    public void tick(float delta) {
        if(isScriptRunning) {
            currCommand.tick(delta);
        }
    }

    public void endCommand() {
        currCommandIndex++;
        if(currCommandIndex >= currCommands.size()) {
            isScriptRunning = false;
            return;
        }
        executeCommand(currCommandIndex);
    }

    public void executeAgenda() {
        String microScript = reader.agenda.data;

        executeScript(microScript);
    }


    public void executeScript(String script) {
        initNewScript(script);
        executeCommand(0);
    }


    private void initNewScript(String script) {
        String lines[] = script.split("\\r?\\n");

        currCommand = null;
        currCommandIndex = -1;
        currCommands.clear();

        for(int i = 0; i < lines.length; i++) {
            String[] parts = processLine(lines[i]);
            String commandName = parts[0];
            String[] args = new String[parts.length-1];
            System.arraycopy(parts, 1, args, 0, parts.length-1);
            if(microMap.containsKey(commandName)) {
                CommandLine line = new CommandLine();
                line.command = commandName;
                line.args = args;
                currCommands.add(line);
            }
        }
    }

    private String[] processLine(String line) {
        String[] tmp = new String[10];
        String buffer = "";
        int iter = 0;
        boolean escaping = false;
        boolean stringMode = false;
        for(int i = 0; i < line.length(); i++) {
            char chr = line.charAt(i);
            if(chr == '\\') {
                escaping = true;
                continue;
            }
            if(chr == '"' && !escaping) {
                stringMode = !stringMode;
                continue;
            }
            escaping = false;
            if(chr == ' ' && !stringMode) {
                tmp[iter++] = buffer;
                buffer = "";
            } else {
                buffer += chr;
            }
        }
        if(buffer.length() > 0) {
            tmp[iter++] = buffer;
        }

        String[] parts = new String[iter];
        System.arraycopy( tmp, 0, parts, 0, iter );

        return parts;
    }

    public void executeCommand(int index) {
        isScriptRunning = true;
        currCommandIndex = index;
        CommandLine line = currCommands.get(currCommandIndex);
        currCommand = microMap.get(line.command);
        currCommand.setInterpreter(this);
        currCommand.init(line.args);
    }

    public void changeState(String stateName) {
        GSReader.State state = reader.states.get(stateName);

        executeScript(state.mainScript);
    }

    public String processExpression(String expression) {
        String[] parts = expression.split("\\.");
        DataScope scope = new DataScope();
        int index = 0;
        if(parts[0].equals("world")) {
            scope = engine.getSystem(GameSystem.class).worldScope;
            index++;
        }
        for(; index < parts.length; index++) {
            scope = scope.get(parts[index]);
        }

        return scope.value();
    }

}
