package edu.ucsd.fccr.telemetry;

/**
 * Created by alex on 4/25/14.
 */


// Extends Application to provide "global" functions/variables
// (guaranteed to survive lifecycle of application, or so they say)

import android.app.Application;

public class MyApp extends Application {

    // Vars List
    private String[] var_name = {"Variables", "Altitude", "Speed", "Direction", "Battery"};
    private String[] var_value = {"", "402.8 ft", "5 mph", "103 degrees", "83%"};
    private boolean[] var_edito = {false, false, false, true, false};

    // Functions List
    public String[] fun_name = {"Functions", "Set Altitude", "Set Speed", "Set Direction", "Check Battery"};
    public String[] fun_value = {"", "Parameters: altitude(ft)", "Parameters: speed(mph)", "Parameters: direction(degrees)", "Parameters: none"};
    public boolean[] fun_edito = {false, true, true, true, false};

    public String[] getVarNames() {
        return var_name;
    }
    public String[] getVarDesc() {
        return var_value;
    }
    public boolean[] getVarEdito() {
        return var_edito;
    }
    public String[] getFunNames() {
        return fun_name;
    }
    public String[] getFunDesc() {
        return fun_value;
    }
    public boolean[] getFunEdito() {
        return fun_edito;
    }
}
