package org.randoom.setlx.operators;

import org.randoom.setlx.exceptions.SetlException;
import org.randoom.setlx.exceptions.TermConversionException;
import org.randoom.setlx.operatorUtilities.OperatorExpression;
import org.randoom.setlx.operatorUtilities.Stack;
import org.randoom.setlx.types.Om;
import org.randoom.setlx.types.Term;
import org.randoom.setlx.types.Value;
import org.randoom.setlx.utilities.CodeFragment;
import org.randoom.setlx.utilities.FragmentList;
import org.randoom.setlx.utilities.State;

/**
 * Operator that computes the sum of the members of the next value from the stack.
 */
public class SumOfMembers extends AUnaryPrefixOperator {
    /** Singleton **/
    public static final SumOfMembers SOM = new SumOfMembers();

    private SumOfMembers() {}


    @Override
    public Value evaluate(State state, Stack<Value> values, OperatorExpression operatorExpression, int currentStackDepth) throws SetlException {
        return values.poll().sumOfMembers(state, Om.OM);
    }

    @Override
    public void appendOperatorSign(State state, StringBuilder sb) {
        sb.append("*/");
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
        appendToOperatorStack(state, term, operatorStack, SOM);
    }

    @Override
    public boolean isLeftAssociative() {
        return false;
    }

    @Override
    public boolean isRightAssociative() {
        return false;
    }

    @Override
    public int precedence() {
        return 1900;
    }

    private final static long COMPARE_TO_ORDER_CONSTANT = generateCompareToOrderConstant(SumOfMembers.class);

    @Override
    public int compareTo(CodeFragment other) {
        if (this == other || other.getClass() == SumOfMembers.class) {
            return 0;
        } else {
            return (this.compareToOrdering() < other.compareToOrdering())? -1 : 1;
        }
    }

    @Override
    public long compareToOrdering() {
        return COMPARE_TO_ORDER_CONSTANT;
    }

    @Override
    public boolean equals(Object obj) {
        return this == obj || obj.getClass() == SumOfMembers.class;
    }

    @Override
    public int computeHashCode() {
        return (int) COMPARE_TO_ORDER_CONSTANT;
    }
}
