package org.randoom.setlx.operators;

/**
 * Base class for unary prefix operators.
 */
public abstract class AUnaryPrefixOperator extends AUnaryOperator {

    @Override
    public boolean hasArgumentBeforeOperator() {
        return false;
    }

    @Override
    public boolean hasArgumentAfterOperator() {
        return true;
    }
}
