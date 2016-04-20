package com.underwater.groundhog.engine.compiler.micro.commands;

import com.underwater.groundhog.engine.compiler.micro.MicroCommand;
import com.underwater.groundhog.engine.compiler.scopes.DataScope;

/**
 * Created by avetiszakharyan on 4/20/16.
 */
public class OperationCommand extends MicroCommand {

    @Override
    public void init(String[] args) {
        String leftExpression = args[0];
        String operation = args[1];
        String rightExpression = args[2];

        DataScope leftScope = interpreter.processExpression(leftExpression);
        DataScope rightScope = interpreter.processExpression(rightExpression);

        if(operation.equals("=")) {
            leftScope.setValue(rightScope.value());
        }
        if(operation.equals("+=")) {
            float leftVal = Float.parseFloat(leftScope.value());
            float rightVal = Float.parseFloat(rightScope.value());
            leftScope.setValue("" + (leftVal+rightVal));
        }
        if(operation.equals("-=")) {
            float leftVal = Float.parseFloat(leftScope.value());
            float rightVal = Float.parseFloat(rightScope.value());
            leftScope.setValue("" + (leftVal-rightVal));
        }
        if(operation.equals("*=")) {
            float leftVal = Float.parseFloat(leftScope.value());
            float rightVal = Float.parseFloat(rightScope.value());
            leftScope.setValue("" + (leftVal*rightVal));
        }
        if(operation.equals("/=")) {
            float leftVal = Float.parseFloat(leftScope.value());
            float rightVal = Float.parseFloat(rightScope.value());
            leftScope.setValue("" + (leftVal/rightVal));
        }

        endCommand();
    }
}
