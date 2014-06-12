package edu.ucsd.fccr.telemetry;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.MAVLink.Messages.MAVLinkPacket;
import com.androidplot.Plot;
import com.androidplot.util.PlotStatistics;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYSeries;
import com.androidplot.xy.*;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Observable;
import java.util.Observer;

public class WidgetsFragment extends Fragment {

    // for joystick
    TextView txtX, txtY, txtR, txtTheta;
    JoystickView joystick;

    // for xy plot
    private XYPlot bigPlot;
    private Number[] rollNumbers = {0};
    private Number[] pitchNumbers = {0};
    private Number[] yawNumbers = {0};
    private SimpleXYSeries series1;
    private SimpleXYSeries series2;
    private SimpleXYSeries series3;
    private static final int HISTORY_SIZE = 200;

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
        series1 = new SimpleXYSeries(
                Arrays.asList(rollNumbers),
                SimpleXYSeries.ArrayFormat.Y_VALS_ONLY,
                "Roll"
        );
        series2 = new SimpleXYSeries(
                Arrays.asList(pitchNumbers),
                SimpleXYSeries.ArrayFormat.Y_VALS_ONLY,
                "Pitch"
        );

        series3 = new SimpleXYSeries(
                Arrays.asList(yawNumbers),
                SimpleXYSeries.ArrayFormat.Y_VALS_ONLY,
                "Yaw"
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

        // format series 3, plot
        LineAndPointFormatter series3Format = new LineAndPointFormatter();
        series3Format.setPointLabelFormatter(new PointLabelFormatter());
        series3Format.configure(
                getActivity().getApplicationContext(),
                R.layout.line_point_formatter_with_plf3
        );
        bigPlot.addSeries(series3, series3Format);



        return v;
    }

    public static WidgetsFragment newInstance(String text) {

        WidgetsFragment f = new WidgetsFragment();
        Bundle b = new Bundle();
        b.putString("com/MAVLink/Messages/msg", text);

        f.setArguments(b);

        return f;
    }

    public void updateGraph(float roll, float pitch, float yaw){
        //Update the plot
        // update instantaneous data:

        // get rid the oldest sample in history:
        if (series1.size() > HISTORY_SIZE) {
            series1.removeFirst();
            series2.removeFirst();
            series3.removeFirst();
        }

        // add the latest history sample:
        series1.addLast(null, roll);
        series2.addLast(null, pitch);
        series3.addLast(null, yaw);

        // redraw the Plots:
        bigPlot.redraw();
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
            txtX.setText("");
            txtY.setText("");
            txtR.setText("");
            txtTheta.setText("");
        }
    };

}
