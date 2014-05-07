package edu.ucsd.fccr.telemetry;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public  class SSHFragment extends Fragment {

    static Button btnWifiToggle;
    static Button btnUDPToggle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_ssh, container, false);

        TextView tv = (TextView) v.findViewById(R.id.section_label);
        tv.setText(getArguments().getString("com/MAVLink/Messages/msg"));

        btnWifiToggle = (Button) v.findViewById(R.id.btnWifiToggle);
        btnUDPToggle = (Button) v.findViewById(R.id.btnUDPToggle);

        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        btnWifiToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity activity = (MainActivity) getActivity();
                activity.setupWifi();
            }
        });

        btnUDPToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity activity = (MainActivity) getActivity();
                activity.setupUDP();
            }
        });
    }

    public static SSHFragment newInstance(String text) {

        SSHFragment f = new SSHFragment();
        Bundle b = new Bundle();
        b.putString("com/MAVLink/Messages/msg", text);

        f.setArguments(b);

        return f;
    }
}