package edu.ucsd.fccr.telemetry;

import android.support.v4.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FunctionsFragment extends ListFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_functions, container,
                false);

        String[] Names = ((TelemetryApp) getActivity().getApplication()).getFunNames();
        String[] Values = ((TelemetryApp) getActivity().getApplication()).getFunValues();
        boolean[] Editables = ((TelemetryApp) getActivity().getApplication()).getFunEditables();
        setListAdapter(new TwoLineListAdapter(rootView.getContext(), Names, Values, Editables));
        return rootView;
    }

    public static FunctionsFragment newInstance(String text) {

        FunctionsFragment f = new FunctionsFragment();
        Bundle b = new Bundle();
        b.putString("com/MAVLink/Messages/msg", text);

        f.setArguments(b);

        return f;
    }
}
