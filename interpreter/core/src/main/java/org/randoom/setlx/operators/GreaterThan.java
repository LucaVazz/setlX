package org.randoom.setlx.operators;

import org.randoom.setlx.exceptions.SetlException;
import org.randoom.setlx.exceptions.TermConversionException;
import org.randoom.setlx.operatorUtilities.OperatorExpression;
import org.randoom.setlx.operatorUtilities.OperatorExpression.ExpressionFragment;
import org.randoom.setlx.types.Term;
import org.randoom.setlx.types.Value;
import org.randoom.setlx.utilities.FragmentList;
import org.randoom.setlx.utilities.State;

import java.util.ArrayDeque;
import java.util.List;

/**
 * Operator that checks if the second values on the stack is greater then the first.
 */
public class GreaterThan extends ABinaryInfixOperator {
    /** Singleton **/
    public static final GreaterThan GT = new GreaterThan();

    private GreaterThan() {}

    @Override
    public Value evaluate(State state, ArrayDeque<Value> values, OperatorExpression operatorExpression, int currentStackDepth) throws SetlException {
        Value rhs = values.poll();
        Value lhs = values.poll();
        try {
            // note: rhs and lhs swapped!
            return rhs.isLessThan(state, lhs);
        } catch (final SetlException se) {
            ArrayDeque<ExpressionFragment> stack = operatorExpression.computeExpressionFragmentStack(state, currentStackDepth);
            String rhsString = stack.poll().getExpression();
            String lhsString = stack.poll().getExpression();
            se.addToTrace("Error in substitute comparison \"" + rhsString + " < " + lhsString +  "\":");
            throw se;
        }
    }

    @Override
    public void appendOperatorSign(State state, StringBuilder sb, List<String> expressions) {
        sb.append(" > ");
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
        appendToOperatorStack(state, term, operatorStack, GT);
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

    private final static long COMPARE_TO_ORDER_CONSTANT = generateCompareToOrderConstant(GreaterThan.class);

    @Override
    public long compareToOrdering() {
        return COMPARE_TO_ORDER_CONSTANT;
    }

    @Override
    public int computeHashCode() {
        return (int) COMPARE_TO_ORDER_CONSTANT;
    }
}
