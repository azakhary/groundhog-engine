package com.underwater.groundhog.engine.compiler.micro.commands;

import com.underwater.groundhog.engine.compiler.GSInterpreter;
import com.underwater.groundhog.engine.compiler.micro.MicroCommand;

/**
 * Created by avetiszakharyan on 4/20/16.
 */
public class ConditionStartCommand extends MicroCommand {

    @Override
    public void init(String[] args) {
        String leftExpression = interpreter.processExpression(args[0]).value();
        String comparator = args[1];
        String rightExpression = interpreter.processExpression(args[2]).value();
        if(compare(leftExpression, rightExpression, comparator)) {
            endCommand();
        } else {
            int index = interpreter.currCommandIndex+1;
            int nesting = 1;
            for(; index < interpreter.currCommands.size(); index++) {
                GSInterpreter.CommandLine line = interpreter.currCommands.get(index);
                if(line.command.equals("condition_start")) {
                    nesting++;
                }
                if(line.command.equals("condition_end")) {
                    nesting--;
                }
                if(nesting == 0) {
                    interpreter.currCommandIndex = index;
                    endCommand();
                    break;
                }
            }
        }
    }

    private boolean compare(String left, String right, String comparator) {
        if(comparator.equals("==")) {
            return left.equals(right);
        } else if(comparator.equals("!=")) {
            return !left.equals(right);
        } else {
            if(left.isEmpty()) left = "0";
            if(right.isEmpty()) right = "0";

            float leftVal = Float.parseFloat(left);
            float rightVal = Float.parseFloat(right);
            if (comparator.equals("<=")) {
                return leftVal <= rightVal;
            }
            if (comparator.equals("<")) {
                return leftVal < rightVal;
            }
            if (comparator.equals(">=")) {
                return leftVal >= rightVal;
            }
            if (comparator.equals(">")) {
                return leftVal > rightVal;
            }
        }

        return false;
    }
}
