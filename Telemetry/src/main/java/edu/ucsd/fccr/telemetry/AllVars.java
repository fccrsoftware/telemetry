package edu.ucsd.fccr.telemetry;

import android.app.Application;
import android.content.Context;

import java.util.ArrayList;

/**
 * Created by Xian on 5/5/2014.
 */
public class AllVars extends Application {
    private static AllVars allvars;
    private Context applicationContext;
    private ArrayList<VarData> varlist;
    private AllVars(Context applicationContext) {
        this.applicationContext = applicationContext;
        varlist = new ArrayList<VarData>();

        VarData Header = new VarData();
        Header.setVarName("Variables");
        varlist.add(Header);

        VarData Altitude = new VarData();
        Altitude.setVarName("Altitude");
        Altitude.setVarValue("402.8");
        Altitude.setEditable(false);
        Altitude.setMin("300");
        Altitude.setMax("500");
        varlist.add(Altitude);

        VarData Speed = new VarData();
        Speed.setVarName("Speed");
        Speed.setVarValue("5");
        Speed.setEditable(false);
        Speed.setMin("0");
        Speed.setMax("10");
        varlist.add(Speed);

        VarData Direction = new VarData();
        Direction.setVarName("Direction");
        Direction.setVarValue("103");
        Direction.setEditable(true);
        Direction.setMin("0");
        Direction.setMax("180");
        varlist.add(Direction);

        VarData Battery = new VarData();
        Battery.setVarName("Battery");
        Battery.setVarValue("83");
        Battery.setEditable(false);
        Battery.setMin("0");
        Battery.setMax("100");
        varlist.add(Battery);
    }

    public static AllVars get(Context context) {
        if(allvars == null) {
            allvars = new AllVars(context.getApplicationContext());
        }
        return allvars;
    }

    public ArrayList<VarData> getAllVars() {
        return varlist;
    }

    public VarData getVar(String name) {
        for(VarData variable : varlist) {
            if(variable.getVarName() == name) {
                return variable;
            }
        }
        return null;
    }
}
