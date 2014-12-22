package org.randoom.setlx.functions;

import org.randoom.setlx.exceptions.SetlException;
import org.randoom.setlx.types.SetlBoolean;
import org.randoom.setlx.types.SetlDouble;
import org.randoom.setlx.types.Value;
import org.randoom.setlx.utilities.ParameterDef;
import org.randoom.setlx.utilities.State;
import org.randoom.setlx.utilities.StdDraw;

import java.util.HashMap;

public class PD_gfx_setYscale extends GfxFunction {
    private final static ParameterDef        MIN        = createOptionalParameter("min", SetlDouble.ZERO);
    private final static ParameterDef        MAX        = createOptionalParameter("max", SetlDouble.ONE);

    public  final static PreDefinedProcedure DEFINITION = new PD_gfx_setYscale();

    private PD_gfx_setYscale(){
        super();
        addParameter(MIN);
        addParameter(MAX);
    }

    @Override
    protected Value execute(final State state, final HashMap<ParameterDef, Value> args) throws SetlException{
        StdDraw.setYscale(doubleFromValue(state, args.get(MIN)),doubleFromValue(state, args.get(MAX)));
        return SetlBoolean.TRUE;
    }
}