package com.underwater.groundhog.engine.compiler;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.underwater.groundhog.engine.TriggerManager;
import com.underwater.groundhog.engine.compiler.micro.MicroCommand;
import com.underwater.groundhog.engine.compiler.micro.commands.*;
import com.underwater.groundhog.engine.compiler.scopes.DataScope;
import com.underwater.groundhog.engine.components.PersonComponent;
import com.underwater.groundhog.engine.components.ThingComponent;
import com.underwater.groundhog.engine.systems.GameSystem;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by avetiszakharyan on 4/9/16.
 */
public class GSInterpreter {

    private GSReader reader;

    public ArrayList<CommandLine> currCommands = new ArrayList<CommandLine>();

    private HashMap<String, MicroCommand> microMap = new HashMap<String, MicroCommand>();

    private HashMap<String, Integer> markerMap = new HashMap<String, Integer>();

    private MicroCommand currCommand = null;
    public int currCommandIndex = -1;
    private boolean isScriptRunning = false;
    public Engine engine;
    public Entity entity;

    public GSReader.State currentState;
    private float stateTimer = 0;
    private float stateTimePassed = 0;

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
        microMap.put("walk_to_object", new WalkToObjectCommand());
        microMap.put("operation", new OperationCommand());
        microMap.put("condition_start", new ConditionStartCommand());
        microMap.put("condition_end", new DummyCommand());
        microMap.put("marker", new MarkerCommand());
        microMap.put("goto", new GotoCommand());
    }

    public void execute() {
        executeAgenda();
    }

    public void tick(float delta) {
        // check for triggers
        if(currentState != null) {
            stateTimer+=delta;
            if(stateTimer > 1f) {
                stateTimer = 0;
                stateTimePassed+=1f;
                stateTick(currentState);
            }
            for(GSReader.Trigger trigger: currentState.triggers) {
                if(TriggerManager.get().checkForEvent(trigger.args[0], trigger.args[1])) {
                    executeScript(trigger.script);
                    break;
                }
            }
        }


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
        markerMap.clear();
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
        stateTimer = 0;
        stateTimePassed = 0;
        currentState = reader.states.get(stateName);

        executeScript(currentState.mainScript);
    }

    public DataScope processExpression(String expression) {
        if(expression.equals("state_time")) {
            return new DataScope("", stateTimePassed+"");
        }

        String[] parts = expression.split("\\.");
        DataScope scope = entity.getComponent(ThingComponent.class).scope;
        if(!expression.contains(".")) {
            if(scope.contains(expression)) {
                return scope.get(expression);
            } else if(engine.getSystem(GameSystem.class).worldScope.contains(expression)){
                return engine.getSystem(GameSystem.class).worldScope.get(expression);
            } else {
                return new DataScope("", expression);
            }
        }
        int index = 0;
        if(parts[0].equals("world")) {
            scope = engine.getSystem(GameSystem.class).worldScope;
            index++;
        }
        for(; index < parts.length; index++) {
            scope = scope.get(parts[index]);
        }

        return scope;
    }

    public void stateTick(GSReader.State state) {
        PersonComponent person = entity.getComponent(PersonComponent.class);
        String fullName = person.id + "."+ state.name;
        TriggerManager.get().registerEvent("state_tick", fullName);
    }

    public void setMarker(String marker) {
        markerMap.put(marker, currCommandIndex);
    }

    public void goTo(String marker) {
        currCommandIndex = markerMap.get(marker);
    }
}
