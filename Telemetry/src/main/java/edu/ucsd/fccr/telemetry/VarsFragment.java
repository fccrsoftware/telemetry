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


        String[] NNN = ((MyApp) getActivity().getApplication()).getVarNames();
        String[] MMM = ((MyApp) getActivity().getApplication()).getVarDesc();
        boolean[] EEE = ((MyApp) getActivity().getApplication()).getVarEdito();
        setListAdapter(new TwoLineListAdapter(rootView.getContext(), NNN, MMM, EEE));
        return rootView;
    }

    public static VarsFragment newInstance(String text) {

        VarsFragment f = new VarsFragment();
        Bundle b = new Bundle();
        b.putString("msg", text);

        f.setArguments(b);

        return f;
    }
}
