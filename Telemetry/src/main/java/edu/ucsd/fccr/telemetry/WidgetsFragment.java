package edu.ucsd.fccr.telemetry;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYSeries;
import com.androidplot.xy.*;

import java.text.DecimalFormat;
import java.util.Arrays;

public class WidgetsFragment extends Fragment {

    // for joystick
    TextView txtX, txtY, txtR, txtTheta;
    JoystickView joystick;
    //

    // for xy plot
    private XYPlot bigPlot;
    //


    TelemetryApp telemetryApp;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_widgets, container, false);

        TextView tv = (TextView) v.findViewById(R.id.section_label);
        tv.setText(getArguments().getString("com/MAVLink/Messages/msg"));

        telemetryApp = ((TelemetryApp) getActivity().getApplication());



        // for joystick
        txtX = (TextView) v.findViewById(R.id.TextViewX);
        txtY = (TextView) v.findViewById(R.id.TextViewY);
        txtR = (TextView) v.findViewById(R.id.TextViewR);
        txtTheta = (TextView) v.findViewById(R.id.TextViewTheta);
        joystick = (JoystickView) v.findViewById(R.id.joystickView);
        joystick.setOnJostickMovedListener(_listener);
        joystick.setYAxisInverted(false);

        // for xy plot
        bigPlot = (XYPlot) v.findViewById(R.id.mySimpleXYPlot);
        Number[] series1Numbers = {1,2,3,6,15,40,120};
        Number[] series2Numbers = {5,4,3,4,1,9,7};
        XYSeries series1 = new SimpleXYSeries(
                Arrays.asList(series1Numbers),
                SimpleXYSeries.ArrayFormat.Y_VALS_ONLY,
                "Series 1"
        );
        XYSeries series2 = new SimpleXYSeries(
                Arrays.asList(series2Numbers),
                SimpleXYSeries.ArrayFormat.Y_VALS_ONLY,
                "Series 2"
        );

        // format series 1, plot
        LineAndPointFormatter series1Format = new LineAndPointFormatter();
        series1Format.setPointLabelFormatter(new PointLabelFormatter());
        series1Format.configure(
                getActivity().getApplicationContext(),
                R.layout.line_point_formatter_with_plf1
        );
        bigPlot.addSeries(series1,series1Format);

        // format series 2, plot
        LineAndPointFormatter series2Format = new LineAndPointFormatter();
        series2Format.setPointLabelFormatter(new PointLabelFormatter());
        series2Format.configure(
                getActivity().getApplicationContext(),
                R.layout.line_point_formatter_with_plf2
        );
        bigPlot.addSeries(series2, series2Format);
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

            // Note: - get/set functions take doubles
            //       - pan and tilt are mapped (x,y)[0,10]
            //       - r returned [0,10]
            //       - theta returned [-pi,pi]

            telemetryApp.setJSx(pan);
            telemetryApp.setJSy(tilt);
            double X = telemetryApp.getJSx();
            double Y = telemetryApp.getJSy();
            double R = telemetryApp.getJSr();
            double Theta = telemetryApp.getJStheta();

            DecimalFormat dec = new DecimalFormat("#.###");
            txtX.setText(dec.format(X));
            txtY.setText(dec.format(Y));
            txtR.setText(dec.format(R));
            txtTheta.setText(dec.format(Theta));

            // Send the updates to robot
            Client client = new Client();
            client.chan1 = (short)tilt;
            client.chan2 = (short)pan;
            new Thread(client).start();
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
