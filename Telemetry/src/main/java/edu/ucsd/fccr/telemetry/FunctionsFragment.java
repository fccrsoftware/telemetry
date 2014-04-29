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

        String[] NNN = ((MyApp) getActivity().getApplication()).getFunNames();
        String[] MMM = ((MyApp) getActivity().getApplication()).getFunDesc();
        boolean[] EEE = ((MyApp) getActivity().getApplication()).getFunEdito();
        setListAdapter(new TwoLineListAdapter(rootView.getContext(), NNN, MMM, EEE));
        return rootView;
    }

    public static FunctionsFragment newInstance(String text) {

        FunctionsFragment f = new FunctionsFragment();
        Bundle b = new Bundle();
        b.putString("msg", text);

        f.setArguments(b);

        return f;
    }
}
