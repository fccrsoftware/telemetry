package edu.ucsd.fccr.telemetry;

import android.content.Intent;
import android.support.v4.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

public class VarsFragment extends ListFragment {

    TelemetryApp tApp = TelemetryApp.getInstance();
    private final int REQUEST_CODE = 1;
    private final int RESULT_FIRST_USER = 1;
    TwoLineListAdapter adapter;
    public String[] Names;
    public String[] Values;
    public String[] Mins;
    public String[] Maxs;
    boolean[] Editables;
    int index;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_vars, container, false);


        Names = tApp.getVarNames();
        Values = tApp.getVarValues();
        Mins = tApp.getVarMins();
        Maxs = tApp.getVarMaxs();

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

    public void onListItemClick(ListView list, View v, int position, long id){

        Intent change = new Intent(getActivity(), ChangeWindowActivity.class);
        index = position;
        change.putExtra("index", position);
        change.putExtra("name", Names[position]);
        change.putExtra("value", Values[position]);
        change.putExtra("min", Mins[position]);
        change.putExtra("max", Maxs[position]);
        Toast test = Toast.makeText(getActivity(), "Position" + Integer.toString(index), Toast.LENGTH_SHORT);
        test.show();
        startActivityForResult(change, REQUEST_CODE);

    }

    /*public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Toast hello = Toast.makeText(getActivity(), "RequestCode1: " +Integer.toString(requestCode), Toast.LENGTH_SHORT);
        hello.show();
        if (requestCode == 1) {

            if (resultCode == RESULT_FIRST_USER) {
                index = data.getIntExtra("index", 0);
                Values[index] = data.getStringExtra("value");
                tApp.setVarValues(Values);
                adapter.notifyDataSetChanged();
            }
            if (resultCode == RESULT_FIRST_USER) {
                Toast fail = Toast.makeText(getActivity(), "Fail", Toast.LENGTH_SHORT);
                fail.show();
            }
        }
    }*/

}
