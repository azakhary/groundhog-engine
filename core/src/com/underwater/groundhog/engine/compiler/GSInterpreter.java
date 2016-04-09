package com.underwater.groundhog.engine.compiler;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by avetiszakharyan on 4/9/16.
 */
public class GSInterpreter {

    private GSReader reader;

    private HashMap<String, Command> commandsMap = new HashMap<String, Command>();

    private ArrayList<CommandLine> currCommands = new ArrayList<CommandLine>();

    public enum Command {
        CHANGE_STATE,
        SAY
    }

    public class CommandLine {
        public Command command;
        public String[] args;

    }

    public GSInterpreter(GSReader reader) {
        this.reader = reader;
        reader.read();

        init();
    }

    private void init() {
        commandsMap.put("change_state", Command.CHANGE_STATE);
        commandsMap.put("say", Command.SAY);
    }

    public void execute() {
        executeAgenda();
    }

    public void executeAgenda() {
        String microScript = reader.agenda.data;

        executeScript(microScript);
    }

    private void initNewScript(String script) {
        String lines[] = script.split("\\r?\\n");

        currCommands.clear();

        for(int i = 0; i < lines.length; i++) {
            String[] parts = lines[i].split(" ");
            String commandName = parts[0];
            String[] args = new String[parts.length-1];
            System.arraycopy(parts, 1, args, 0, parts.length-1);
            Command command = commandsMap.get(commandName);
            if(command != null) {
                CommandLine line = new CommandLine();
                line.command = command;
                line.args = args;
                currCommands.add(line);
            }
        }
    }

    public void executeScript(String script) {
        initNewScript(script);

        for(CommandLine line : currCommands) {

        }
    }

}
