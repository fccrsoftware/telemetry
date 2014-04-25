package edu.ucsd.fccr.telemetry;

import android.support.v4.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class FunctionsFragment extends ListFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_functions, container,
                false);

        setListAdapter(new FunctionsListAdapter(rootView.getContext()));
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
