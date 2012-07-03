package org.randoom.setlx.expressions;

import org.randoom.setlx.exceptions.SetlException;
import org.randoom.setlx.types.Term;
import org.randoom.setlx.types.Value;
import org.randoom.setlx.utilities.TermConverter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/*
grammar rule:
term
    : TERM '(' termArguments ')'
    ;

implemented here as:
      ====     ==============
     mFChar        mArgs
*/

public class TermConstructor extends Expr {
    // precedence level in SetlX-grammar
    private final static int    PRECEDENCE           = 9999;

    private final String     mFChar;     // functional character of the term
    private final List<Expr> mArgs;      // list of arguments

    public TermConstructor(final String fChar, final List<Expr> args) {
        mFChar  = fChar;
        mArgs   = args;
    }

    protected Term evaluate() throws SetlException {
        final Term result = new Term(mFChar, mArgs.size());

        for (final Expr arg: mArgs) {
            result.addMember(arg.eval().toTerm()); // evaluate arguments at runtime
        }

        return result;
    }

    /* string operations */

    public void appendString(final StringBuilder sb, final int tabs) {
        sb.append(mFChar);
        sb.append("(");

        final Iterator<Expr> iter = mArgs.iterator();
        while (iter.hasNext()) {
            iter.next().appendString(sb, 0);
            if (iter.hasNext()) {
                sb.append(", ");
            }
        }

        sb.append(")");
    }

    /* term operations */

    public Term toTerm() {
        final Term result = new Term(mFChar, mArgs.size());

        for (final Expr arg: mArgs) {
            result.addMember(arg.toTerm()); // do not evaluate here
        }

        return result;
    }

    public Term toTermQuoted() throws SetlException {
        return this.evaluate();
    }

    public static Expr termToExpr(final Term term) {
        final String        functionalCharacter = term.functionalCharacter().getUnquotedString();
        final List<Expr>    args                = new ArrayList<Expr>(term.size());
        for (final Value v : term) {
            args.add(TermConverter.valueToExpr(v));
        }
        return new TermConstructor(functionalCharacter, args);
    }

    // precedence level in SetlX-grammar
    public int precedence() {
        return PRECEDENCE;
    }
}
