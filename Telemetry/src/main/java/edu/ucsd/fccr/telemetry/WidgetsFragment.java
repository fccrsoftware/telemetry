package edu.ucsd.fccr.telemetry;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class WidgetsFragment extends Fragment {

    // for joystick
    TextView txtX, txtY;
    JoystickView joystick;
    //


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_widgets, container, false);

        TextView tv = (TextView) v.findViewById(R.id.section_label);
        tv.setText(getArguments().getString("com/MAVLink/Messages/msg"));

        // for joystick
        txtX = (TextView) v.findViewById(R.id.TextViewX);
        txtY = (TextView) v.findViewById(R.id.TextViewY);
        joystick = (JoystickView) v.findViewById(R.id.joystickView);
        joystick.setOnJostickMovedListener(_listener);
        //



        return v;
    }

    public static WidgetsFragment newInstance(String text) {

        WidgetsFragment f = new WidgetsFragment();
        Bundle b = new Bundle();
        b.putString("com/MAVLink/Messages/msg", text);

        f.setArguments(b);

        return f;
    }


    // Joystick listeners (for joystick)
    private JoystickMovedListener _listener = new JoystickMovedListener() {
        @Override
        public void OnMoved(int pan, int tilt) {
            // returns ints 0-1000
            ((TelemetryApp) getActivity().getApplication()).setJSx(pan);
            ((TelemetryApp) getActivity().getApplication()).setJSy(tilt);
            int X = ((TelemetryApp) getActivity().getApplication()).getJSx();
            int Y = ((TelemetryApp) getActivity().getApplication()).getJSy();
            txtX.setText(Integer.toString(X));
            txtY.setText(Integer.toString(Y));
        }
        @Override
        public void OnReleased() {
            txtX.setText("released");
            txtY.setText("released");
        }
        public void OnReturnedToCenter() {
            txtX.setText("stopped");
            txtY.setText("stopped");
        }
    };
    //
}
