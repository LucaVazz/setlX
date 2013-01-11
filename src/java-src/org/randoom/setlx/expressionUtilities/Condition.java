package org.randoom.setlx.expressionUtilities;

import org.randoom.setlx.exceptions.SetlException;
import org.randoom.setlx.exceptions.IncompatibleTypeException;
import org.randoom.setlx.expressions.Expr;
import org.randoom.setlx.expressions.Variable;
import org.randoom.setlx.types.SetlBoolean;
import org.randoom.setlx.types.Value;
import org.randoom.setlx.utilities.CodeFragment;
import org.randoom.setlx.utilities.State;

import java.util.List;

/*
grammar rule:
condition
    : boolExpr
    ;

implemented here as:
      ========
       mExpr
*/

public class Condition extends CodeFragment {
    private final Expr mExpr;

    public Condition(final Expr expr) {
        mExpr = expr;
    }

    public SetlBoolean eval(final State state) throws SetlException {
        final Value v = mExpr.eval(state);
        if (v == SetlBoolean.TRUE || v == SetlBoolean.FALSE) { // is Boolean value?
            return (SetlBoolean) v;
        } else {
            throw new IncompatibleTypeException("'" + v + "' is not a Boolean value.");
        }
    }

    public boolean evalToBool(final State state) throws SetlException {
        return eval(state) == SetlBoolean.TRUE;
    }

    /* Gather all bound and unbound variables in this expression and its siblings
          - bound   means "assigned" in this expression
          - unbound means "not present in bound set when used"
          - used    means "present in bound set when used"
       NOTE: Use optimizeAndCollectVariables() when adding variables from
             sub-expressions
    */
    @Override
    public void collectVariablesAndOptimize (
        final List<Variable> boundVariables,
        final List<Variable> unboundVariables,
        final List<Variable> usedVariables
    ) {
        mExpr.collectVariablesAndOptimize(boundVariables, unboundVariables, usedVariables);
    }

    /* string operations */

    public void appendString(final State state, final StringBuilder sb, final int tabs) {
        mExpr.appendString(state, sb, tabs);
    }

    /* term operations */

    public Value toTerm(final State state) {
        return mExpr.toTerm(state);
    }
}
