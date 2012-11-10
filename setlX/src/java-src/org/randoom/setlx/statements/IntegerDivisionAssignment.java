package org.randoom.setlx.statements;

import org.randoom.setlx.exceptions.SetlException;
import org.randoom.setlx.exceptions.TermConversionException;
import org.randoom.setlx.expressions.Expr;
import org.randoom.setlx.expressions.Variable;
import org.randoom.setlx.types.Om;
import org.randoom.setlx.types.Term;
import org.randoom.setlx.types.Value;
import org.randoom.setlx.utilities.Environment;
import org.randoom.setlx.utilities.State;
import org.randoom.setlx.utilities.TermConverter;

import java.util.List;

/*
grammar rule:
assignmentOther
    : assignable ('\\=' | [...] ) anyExpr
    ;

implemented here as:
      ==========                  =======
         mLhs                      mRhs
*/

public class IntegerDivisionAssignment extends StatementWithPrintableResult {
    // functional character used in terms
    public  final static String     FUNCTIONAL_CHARACTER    = "^integerDivisionAssignment";
    // Trace all assignments. MAY ONLY BE SET BY ENVIRONMENT CLASS!
    public        static boolean    sTraceAssignments       = false;

    // precedence level in SetlX-grammar
    private final static int        PRECEDENCE              = 1000;

    private final Expr    mLhs;
    private final Expr    mRhs;
    private       boolean mPrintAfterEval;

    public IntegerDivisionAssignment(final Expr lhs, final Expr rhs) {
        mLhs            = lhs;
        mRhs            = rhs;
        mPrintAfterEval = false;
    }

    /*package*/ void setPrintAfterEval() {
        mPrintAfterEval = true;
    }

    protected Value exec(final State state) throws SetlException {
        final Value assigned = mLhs.eval(state).integerDivisionAssign(state, mRhs.eval(state).clone());
        mLhs.assignUncloned(state, assigned);

        if (sTraceAssignments) {
            Environment.outWriteLn("~< Trace: " + mLhs + " := " + assigned + " >~");
        } else if (mPrintAfterEval && (assigned != Om.OM || !Om.OM.isHidden()) ) {
            Environment.outWriteLn("~< Result: " + assigned + " >~");
        }

        return null;
    }

    /* Gather all bound and unbound variables in this statement and its siblings
          - bound   means "assigned" in this expression
          - unbound means "not present in bound set when used"
          - used    means "present in bound set when used"
       Optimize sub-expressions during this process by calling optimizeAndCollectVariables()
       when adding variables from them.
    */
    public void collectVariablesAndOptimize (
        final List<Variable> boundVariables,
        final List<Variable> unboundVariables,
        final List<Variable> usedVariables
    ) {
        // first we evaluate lhs and rhs as usual
        mLhs.collectVariablesAndOptimize(boundVariables, unboundVariables, usedVariables);
        mRhs.collectVariablesAndOptimize(boundVariables, unboundVariables, usedVariables);

        // then assing to mLhs
        // add all variables found to bound by not suppliying unboundVariables
        // as this expression is now used in an assignment
        mLhs.collectVariablesAndOptimize(boundVariables, boundVariables, boundVariables);
    }

    /* string operations */

    public void appendString(final StringBuilder sb, final int tabs) {
        Environment.getLineStart(sb, tabs);
        mLhs.appendString(sb, tabs);
        sb.append(" \\= ");
        mRhs.appendString(sb, tabs);
        sb.append(";");
    }

    /* term operations */

    public Term toTerm(final State state) {
        final Term result = new Term(FUNCTIONAL_CHARACTER, 2);
        result.addMember(mLhs.toTerm(state));
        result.addMember(mRhs.toTerm(state));
        return result;
    }

    public static IntegerDivisionAssignment termToStatement(final Term term) throws TermConversionException {
        if (term.size() != 2) {
            throw new TermConversionException("malformed " + FUNCTIONAL_CHARACTER);
        } else {
            final Expr lhs = TermConverter.valueToExpr(term.firstMember());
            final Expr rhs = TermConverter.valueToExpr(PRECEDENCE, false, term.lastMember());
            return new IntegerDivisionAssignment(lhs, rhs);
        }
    }

    // precedence level in SetlX-grammar
    public int precedence() {
        return PRECEDENCE;
    }
}

