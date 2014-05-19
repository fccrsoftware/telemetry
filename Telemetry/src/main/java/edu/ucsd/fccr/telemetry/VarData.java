package edu.ucsd.fccr.telemetry;

/**
 * Created by Xian on 5/4/2014.
 */
public class VarData {

    private String VarName;
    private String VarValue;
    private boolean Editable;
    private String min;
    private String max;

    public void setVarName(String varName) {
        VarName = varName;
    }

    public String getVarName() {
        return VarName;
    }

    public String getVarValue() {
        return VarValue;
    }

    public void setVarValue(String varValue) {
        VarValue = varValue;
    }

    public void setEditable(boolean editable) {
        Editable = editable;
    }

    public boolean isEditable() {
        return Editable;
    }

    public String getMin() {
        return min;
    }

    public String getMax() {
        return max;
    }

    public void setMin(String min) {
        this.min = min;
    }

    public void setMax(String max) {
        this.max = max;
    }
}
