package org.randoom.setlx.operators;

import org.randoom.setlx.exceptions.SetlException;
import org.randoom.setlx.exceptions.TermConversionException;
import org.randoom.setlx.operatorUtilities.OperatorExpression;
import org.randoom.setlx.operatorUtilities.Stack;
import org.randoom.setlx.types.Term;
import org.randoom.setlx.types.Value;
import org.randoom.setlx.utilities.FragmentList;
import org.randoom.setlx.utilities.State;

/**
 * Operator that checks if the second values on the stack is contained in the first.
 */
public class In extends ABinaryInfixOperator {
    /** Singleton **/
    public static final In I = new In();

    private In() {}

    @Override
    public Value evaluate(State state, Stack<Value> values, OperatorExpression operatorExpression, int currentStackDepth) throws SetlException {
        Value rhs = values.poll();
        Value lhs = values.poll();
        // note: rhs and lhs swapped!
        return rhs.containsMember(state, lhs);
    }

    @Override
    public void appendOperatorSign(State state, StringBuilder sb) {
        sb.append(" in ");
    }

    /**
     * Append the operator represented by a term to the supplied operator stack.
     *
     * @param state                    Current state of the running setlX program.
     * @param term                     Term to convert.
     * @param operatorStack            Operator to append to.
     * @throws TermConversionException If term is malformed.
     */
    public static void appendToOperatorStack(final State state, final Term term, FragmentList<AOperator> operatorStack) throws TermConversionException {
        appendToOperatorStack(state, term, operatorStack, I);
    }

    @Override
    public boolean isLeftAssociative() {
        return true;
    }

    @Override
    public boolean isRightAssociative() {
        return false;
    }

    @Override
    public int precedence() {
        return 1500;
    }

    private final static long COMPARE_TO_ORDER_CONSTANT = generateCompareToOrderConstant(In.class);

    @Override
    public long compareToOrdering() {
        return COMPARE_TO_ORDER_CONSTANT;
    }

    @Override
    public int computeHashCode() {
        return (int) COMPARE_TO_ORDER_CONSTANT;
    }
}
