package edu.ucsd.fccr.telemetry;

/**
 * Created by alex on 4/25/14.
 */


// Extends Application to provide "global" functions/variables
// (guaranteed to survive lifecycle of application, or so they say)

import android.app.Application;

public class TelemetryApp extends Application {

    // Vars List
    private String[] VarNames = {"Variables", "Altitude", "Speed", "Direction", "Battery"};
    private String[] VarValues = {"", "402.8 ft", "5 mph", "103 degrees", "83%"};
    private boolean[] VarEditables = {false, false, false, true, false};

    // Functions List
    public String[] FunNames = {"Functions", "Set Altitude", "Set Speed", "Set Direction", "Check Battery"};
    public String[] FunValues = {"", "Parameters: altitude(ft)", "Parameters: speed(mph)", "Parameters: direction(degrees)", "Parameters: none"};
    public boolean[] FunEditables = {false, true, true, true, false};

    // Joystick Parameters
    public int JSx;
    public int JSy;





    // Setters
    public void setJSx(int value) {
        JSx = value;
    }
    public void setJSy(int value) {
        JSy = value;
    }

    // Getters
    public String[] getVarNames() {
        return VarNames;
    }
    public String[] getVarValues() {
        return VarValues;
    }
    public boolean[] getVarEditables() {
        return VarEditables;
    }
    public String[] getFunNames() {
        return FunNames;
    }
    public String[] getFunValues() {
        return FunValues;
    }
    public boolean[] getFunEditables() {
        return FunEditables;
    }
    public int getJSx() {
        return JSx;
    }
    public int getJSy() {
        return JSy;
    }
}
