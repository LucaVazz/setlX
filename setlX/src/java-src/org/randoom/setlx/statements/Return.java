package org.randoom.setlx.statements;

import org.randoom.setlx.exceptions.SetlException;
import org.randoom.setlx.exceptions.TermConversionException;
import org.randoom.setlx.expressions.Expr;
import org.randoom.setlx.types.Om;
import org.randoom.setlx.types.SetlString;
import org.randoom.setlx.types.Term;
import org.randoom.setlx.types.Value;
import org.randoom.setlx.utilities.Environment;
import org.randoom.setlx.utilities.TermConverter;

/*
grammar rule:
statement
    : [...]
    | 'return' anyExpr? ';'
    ;

implemented here as:
               =======
               mResult
*/

public class Return extends Statement {
    // functional character used in terms (MUST be class name starting with lower case letter!)
    private final static String FUNCTIONAL_CHARACTER = "^return";

    private final Expr mResult;

    public Return(final Expr result) {
        mResult = result;
    }

    protected Value exec() throws SetlException {
        if (mResult != null) {
            return mResult.eval();
        } else {
            return Om.OM;
        }
    }

    /* string operations */

    public void appendString(final StringBuilder sb, final int tabs) {
        Environment.getLineStart(sb, tabs);
        sb.append("return");
        if (mResult != null){
            sb.append(" ");
            mResult.appendString(sb, 0);
        }
        sb.append(";");
    }

    /* term operations */

    public Term toTerm() {
        final Term result = new Term(FUNCTIONAL_CHARACTER, 1);
        if (mResult != null) {
            result.addMember(mResult.toTerm());
        } else {
            result.addMember(new SetlString("nil"));
        }
        return result;
    }

    public static Return termToStatement(final Term term) throws TermConversionException {
        if (term.size() != 1) {
            throw new TermConversionException("malformed " + FUNCTIONAL_CHARACTER);
        } else {
            Expr expr = null;
            if (! term.firstMember().equals(new SetlString("nil"))) {
                expr = TermConverter.valueToExpr(term.firstMember());
            }
            return new Return(expr);
        }
    }
}
