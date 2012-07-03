package org.randoom.setlx.functions;

import org.randoom.setlx.exceptions.JVMIOException;
import org.randoom.setlx.types.Om;
import org.randoom.setlx.types.SetlString;
import org.randoom.setlx.types.Value;
import org.randoom.setlx.utilities.Environment;

import java.util.List;

// read(message, ...)            : prompts the user with `message', then reads from
//                                 stdin until user enters at least one character,
//                                 converts input into integer or real if possible

public class PD_read extends PreDefinedFunction {
    public final static PreDefinedFunction DEFINITION = new PD_read();

    private PD_read() {
        super("read");
        addParameter("message");
        enableUnlimitedParameters();
        allowFewerParameters();
        doNotChangeScope();
    }

    public Value execute(List<Value> args, List<Value> writeBackVars) {
        Value          inputValue = Om.OM;
        String         input      = null;
        String         prompt     = "";
        for (Value arg : args) {
            prompt += arg.getUnquotedString();
        }
        try {
            do {
                Environment.prompt(prompt + ": ");
                input = Environment.inReadLine();
                if (input != null) {
                    input = input.trim();
                }
            } while (input != null && input.equals(""));
        } catch (JVMIOException ioe) {
            Environment.errWriteLn("IO error trying to read from stdin!");
        }

        if (input != null) {
            inputValue = new SetlString(input);
            if (inputValue.toInteger() != Om.OM) {
                inputValue = inputValue.toInteger();
            } else if (inputValue.toReal() != Om.OM) {
                inputValue = inputValue.toReal();
            }
        } else {
            inputValue = Om.OM;
        }

        return inputValue;
    }
}
