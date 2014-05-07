package edu.ucsd.fccr.telemetry;

import android.support.v4.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class VarsFragment extends ListFragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_vars, container, false);


        String[] Names = ((TelemetryApp) getActivity().getApplication()).getVarNames();
        String[] Values = ((TelemetryApp) getActivity().getApplication()).getVarValues();
        boolean[] Editables = ((TelemetryApp) getActivity().getApplication()).getVarEditables();
        setListAdapter(new TwoLineListAdapter(rootView.getContext(), Names, Values, Editables));
        return rootView;
    }

    public static VarsFragment newInstance(String text) {

        VarsFragment f = new VarsFragment();
        Bundle b = new Bundle();
        b.putString("com/MAVLink/Messages/msg", text);

        f.setArguments(b);

        return f;
    }
}
