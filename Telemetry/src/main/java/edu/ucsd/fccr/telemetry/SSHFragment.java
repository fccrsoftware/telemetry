package edu.ucsd.fccr.telemetry;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;

public  class SSHFragment extends Fragment {

    public TextView logTextView;
    public EditText commandBox;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_ssh, container, false);

        logTextView = (TextView) v.findViewById(R.id.log_label);
        commandBox = (EditText) v.findViewById(R.id.command_box);

        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        this.updateLog("App Loaded");
    }

    public void updateLog(String m){
        if (m != null)
            logTextView.setText(((TelemetryApp) getActivity().getApplication()).log(m));
    }

    public static SSHFragment newInstance(String text) {

        SSHFragment f = new SSHFragment();
        Bundle b = new Bundle();
        b.putString("com/MAVLink/Messages/msg", text);

        f.setArguments(b);

        return f;
    }
}