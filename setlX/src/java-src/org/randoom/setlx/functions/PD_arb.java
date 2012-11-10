package org.randoom.setlx.functions;

import org.randoom.setlx.exceptions.SetlException;
import org.randoom.setlx.types.Value;
import org.randoom.setlx.utilities.State;

import java.util.List;

// arb(collectionValue)          : Selects an arbitrary member from `collectionValue'.
//                                 Note that 'arb' is deterministic, while rnd is not.

public class PD_arb extends PreDefinedFunction {
    public final static PreDefinedFunction DEFINITION = new PD_arb();

    private PD_arb() {
        super("arb");
        addParameter("collectionValue");
    }

    public Value execute(final State state, List<Value> args, List<Value> writeBackVars) throws SetlException {
        return args.get(0).arbitraryMember();
    }
}

