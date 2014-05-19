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

    private TextView logTextView;
    public static EditText commandBox;

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

        // Moved to action
//        btnWifiToggle.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                MainActivity activity = (MainActivity) getActivity();
//                activity.setupWifi();
//            }
//        });

        this.log("App Loaded");
    }

    public void log (String msg) {
        String c = logTextView.getText().toString();
        SimpleDateFormat df = new SimpleDateFormat("hh:mm:ss");
        String date = df.format(new Date());

        StringBuilder s = new StringBuilder(c);
        s.append("\n" + date +"> ");
        s.append(msg);

        logTextView.setText(s.toString());
    }

    public static SSHFragment newInstance(String text) {

        SSHFragment f = new SSHFragment();
        Bundle b = new Bundle();
        b.putString("com/MAVLink/Messages/msg", text);

        f.setArguments(b);

        return f;
    }
}