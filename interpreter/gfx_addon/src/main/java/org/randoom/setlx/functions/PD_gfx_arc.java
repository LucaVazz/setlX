package org.randoom.setlx.functions;

import org.randoom.setlx.exceptions.SetlException;
import org.randoom.setlx.types.SetlBoolean;
import org.randoom.setlx.types.Value;
import org.randoom.setlx.utilities.ParameterDef;
import org.randoom.setlx.utilities.State;
import org.randoom.setlx.gfx.utilities.StdDraw;

import java.util.HashMap;

public class PD_gfx_arc extends GfxFunction{
    private final static ParameterDef        X          = createParameter("x");
    private final static ParameterDef        Y          = createParameter("y");
    private final static ParameterDef        R          = createParameter("r");
    private final static ParameterDef        ANGLE_1    = createParameter("angle1");
    private final static ParameterDef        ANGLE_2    = createParameter("angle2");

    public  final static PreDefinedProcedure DEFINITION = new PD_gfx_arc();

    private PD_gfx_arc() {
        super();
        addParameter(X);
        addParameter(Y);
        addParameter(R);
        addParameter(ANGLE_1);
        addParameter(ANGLE_2);
    }

    @Override
    protected Value execute(final State state, final HashMap<ParameterDef, Value> args) throws SetlException{
        StdDraw.arc( doubleFromValue(state, args.get(X)),
                     doubleFromValue(state, args.get(Y)),
                     doubleFromValue(state, args.get(R)),
                     doubleFromValue(state, args.get(ANGLE_1)),
                     doubleFromValue(state, args.get(ANGLE_2))
                   );
        return SetlBoolean.TRUE;
    }

}
