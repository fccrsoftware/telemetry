package edu.ucsd.fccr.telemetry;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DecimalFormat;

public class WidgetsFragment extends Fragment {

    // for joystick
    TextView txtX, txtY, txtR, txtTheta;
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
        txtR = (TextView) v.findViewById(R.id.TextViewR);
        txtTheta = (TextView) v.findViewById(R.id.TextViewTheta);
        joystick = (JoystickView) v.findViewById(R.id.joystickView);
        joystick.setOnJostickMovedListener(_listener);
        joystick.setYAxisInverted(false);


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

            // Note: - get/set functions take doubles
            //       - pan and tilt are mapped (x,y)[0,10]
            //       - r returned [0,10]
            //       - theta returned [-pi,pi]

            ((TelemetryApp) getActivity().getApplication()).setJSx((double)pan);
            ((TelemetryApp) getActivity().getApplication()).setJSy((double)tilt);
            double X = ((TelemetryApp) getActivity().getApplication()).getJSx();
            double Y = ((TelemetryApp) getActivity().getApplication()).getJSy();
            double R = ((TelemetryApp) getActivity().getApplication()).getJSr();
            double Theta = ((TelemetryApp) getActivity().getApplication()).getJStheta();

            DecimalFormat dec = new DecimalFormat("#.###");
            txtX.setText(dec.format(X));
            txtY.setText(dec.format(Y));
            txtR.setText(dec.format(R));
            txtTheta.setText(dec.format(Theta));
        }
        @Override
        public void OnReleased() {
            txtX.setText("released");
            txtY.setText("released");
            txtR.setText("released");
            txtTheta.setText("released");
        }
        public void OnReturnedToCenter() {
            txtX.setText("stopped");
            txtY.setText("stopped");
            txtR.setText("stopped");
            txtTheta.setText("stopped");
        }
    };
    //
}
