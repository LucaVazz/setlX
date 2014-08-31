package org.randoom.setlx.functions;

import java.util.List;

import org.randoom.setlx.exceptions.IncompatibleTypeException;
import org.randoom.setlx.exceptions.SetlException;
import org.randoom.setlx.types.SetlMatrix;
import org.randoom.setlx.types.Value;
import org.randoom.setlx.utilities.ParameterDef;
import org.randoom.setlx.utilities.State;

/**
 * @author Patrick Robinson
 *         X := solve(A,B)
 *         solves A*X = B
 */
public class PD_la_solve extends PreDefinedProcedure {
    public final static PreDefinedProcedure DEFINITION = new PD_la_solve();

    private PD_la_solve() {
        super();
        addParameter("MatrixA", ParameterDef.ParameterType.READ_ONLY);
        addParameter("MatrixB", ParameterDef.ParameterType.READ_ONLY);
    }

    @Override
    public Value execute(State state, List<Value> args, List<Value> writeBackVars) throws SetlException {
        if (!(args.get(0) instanceof SetlMatrix)) {
            throw new IncompatibleTypeException("The first parameter needs to be a matrix.");
        }
        if (!(args.get(1) instanceof SetlMatrix)) {
            throw new IncompatibleTypeException("The second parameter needs to be a matrix.");
        }
        SetlMatrix A = (SetlMatrix) args.get(0);
        SetlMatrix B = (SetlMatrix) args.get(1);
        return A.solve(B);
    }
}