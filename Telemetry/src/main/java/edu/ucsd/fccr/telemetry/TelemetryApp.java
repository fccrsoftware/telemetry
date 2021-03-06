package edu.ucsd.fccr.telemetry;

/**
 * Created by alex on 4/25/14.
 */


// Extends Application to provide "global" functions/variables
// (guaranteed to survive lifecycle of application, or so they say)

import android.app.Application;

import com.MAVLink.Messages.MAVLinkPacket;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TelemetryApp extends Application {
    public static TelemetryApp singleton;

    // Vars List
    private String[] VarNames = {"Variables", "Altitude (ft)", "Speed (mph)", "Direction (Degrees)", "Battery (%)"};
    private String[] VarValues = {"", "402.8", "5", "103", "83"};
    private boolean[] VarEditables = {false, true, true, true, false};
    private String[] VarMins = {"", "0","0","0","0"};
    private String[] VarMaxs = {"", "500","10","360","100"};
    private int index = 2;

    // Functions List
    public String[] FunNames = {"Functions", "Set Altitude", "Set Speed", "Set Direction", "Check Battery"};
    public String[] FunValues = {"", "Parameters: altitude(ft)", "Parameters: speed(mph)", "Parameters: direction(degrees)", "Parameters: none"};
    public boolean[] FunEditables = {false, true, true, true, false};

    // Joystick Parameters
    public double JSx;
    public double JSy;
    public double JSr;
    public double JStheta;

    // Mavlink data
    public MAVLinkPacket currentPacket;

    public String currentLog = "";

    public TelemetryApp(){}

    public static TelemetryApp getInstance() {
        if (singleton == null){
            singleton = new TelemetryApp();
        }
        return singleton;
    }

    // Setters
    public void setJSx(int value) {
        JSx = value;
    }
    public void setJSy(int value) {
        JSy = value;
    }
    public void setVarNames(String[] newVarNames) {
        VarNames = newVarNames;
    }
    public void setVarValues(String[] newVarValues) {
        this.VarValues = newVarValues;
    }
    public void setVarEditables(boolean[] newVarEditables) {
        VarEditables = newVarEditables;
    }
    public void setIndex(int newIndex){
        index = newIndex;
    }
    public void setVarMins(String[] newVarMins) {
        VarMins = newVarMins;
    }
    public void setVarMaxs(String[] newVarMaxs) {
        VarMaxs = newVarMaxs;
    }
    public void setNewPacket(MAVLinkPacket packet) {
        currentPacket = packet;
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
    public String[] getVarMins() {
        return VarMins;
    }
    public String[] getVarMaxs() {
        return VarMaxs;
    }
    public int getIndex(){
        return index;
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
    public double getJSx() {
        return JSx;
    }
    public double getJSy() {
        return JSy;
    }
    public double getJSr() {
        JSr = Math.sqrt(Math.pow(JSx,2) + Math.pow(JSy,2));
        return JSr;
    }
    public double getJStheta() {
        JStheta = Math.atan2(JSx,JSy);
        return JStheta;
    }
    public MAVLinkPacket getCurrentPacket() {
        return currentPacket;
    }

    public String log (String msg) {
        String c = currentLog;
        SimpleDateFormat df = new SimpleDateFormat("hh:mm:ss");
        String date = df.format(new Date());

        StringBuilder s = new StringBuilder(date +"> ");
        s.append(msg);
        s.append("\n" + c);

        currentLog = s.toString();

        return s.toString();
    }
}

