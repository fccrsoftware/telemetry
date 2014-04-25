package edu.ucsd.fccr.telemetry;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

public class VarsFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_vars, container, false);

        TextView tv = (TextView) v.findViewById(R.id.section_label);
        tv.setText(getArguments().getString("msg"));

        TextView T = (TextView) v.findViewById(R.id.T);
        T.setText("hello");

        ListView L1 = (ListView) v.findViewById(R.id.VarsListView);
        L1.setAdapter(new VarsListAdapter(getActivity()));

        return v;
    }

    public static VarsFragment newInstance(String text) {

        VarsFragment f = new VarsFragment();
        Bundle b = new Bundle();
        b.putString("msg", text);

        f.setArguments(b);

        return f;
    }
}
