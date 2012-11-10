package org.randoom.setlx.statements;

import org.randoom.setlx.exceptions.SetlException;
import org.randoom.setlx.expressions.Expr;
import org.randoom.setlx.expressions.Variable;
import org.randoom.setlx.types.Value;
import org.randoom.setlx.utilities.CodeFragment;
import org.randoom.setlx.utilities.DebugPrompt;
import org.randoom.setlx.utilities.Environment;
import org.randoom.setlx.utilities.State;

import java.util.List;

public abstract class Statement extends CodeFragment {
    // is debug mode active? MAY ONLY BE SET BY ENVIRONMENT CLASS!
    public static   boolean sDebugModeActive = false;

    public          Value execute(final State state) throws SetlException {
        if (sDebugModeActive && ! Environment.isDebugPromptActive()) {
            DebugPrompt.prompt(state, this);
            final Value result = exec(state);
            Expr.sStepNext = false;
            return result;
        } else {
            return exec(state);
        }
    }

    protected abstract Value exec(final State state) throws SetlException;

    /* Gather all bound and unbound variables in this statement and its siblings
          - bound   means "assigned" in this expression
          - unbound means "not present in bound set when used"
          - used    means "present in bound set when used"
       Optimize sub-expressions during this process by calling optimizeAndCollectVariables()
       when adding variables from them.
    */
    public abstract void collectVariablesAndOptimize (
        final List<Variable> boundVariables,
        final List<Variable> unboundVariables,
        final List<Variable> usedVariables
    );

    /* string operations */

    public abstract void appendString(final StringBuilder sb, final int tabs);

    /* term operations */

    public abstract Value toTerm(final State state);
}

