package org.randoom.setlx.statements;

import org.randoom.setlx.exceptions.TermConversionException;
import org.randoom.setlx.expressions.Variable;
import org.randoom.setlx.types.Om;
import org.randoom.setlx.types.Term;
import org.randoom.setlx.utilities.Environment;
import org.randoom.setlx.utilities.State;

import java.util.List;

/*
grammar rule:
statement
    : [...]
    | 'continue' ';'
    ;
*/

public class Continue extends Statement {
    // functional character used in terms (MUST be class name starting with lower case letter!)
    private final static String     FUNCTIONAL_CHARACTER    = "^continue";

    public  final static Continue   C                       = new Continue();

    private Continue() {  }

    protected Om exec(final State state) {
        return Om.OM.setContinue();
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
    ) { /* nothing to collect */ }

    /* string operations */

    public void appendString(final StringBuilder sb, final int tabs) {
        Environment.getLineStart(sb, tabs);
        sb.append("continue;");
    }

    /* term operations */

    public Term toTerm(final State state) {
        return new Term(FUNCTIONAL_CHARACTER, 0);
    }

    public static Continue termToStatement(final Term term) throws TermConversionException {
        if (term.size() != 0) {
            throw new TermConversionException("malformed " + FUNCTIONAL_CHARACTER);
        } else {
            return C;
        }
    }
}

