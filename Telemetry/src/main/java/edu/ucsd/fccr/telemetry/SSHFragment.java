package edu.ucsd.fccr.telemetry;

import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.content.Context;
import android.view.WindowManager;

import edu.ucsd.fccr.telemetry.WifiAP;

public  class SSHFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_ssh, container, false);

        TextView tv = (TextView) v.findViewById(R.id.section_label);
        tv.setText(getArguments().getString("msg"));

        return v;
    }

    public static SSHFragment newInstance(String text) {

        SSHFragment f = new SSHFragment();
        Bundle b = new Bundle();
        b.putString("msg", text);

        f.setArguments(b);

        return f;
    }
}