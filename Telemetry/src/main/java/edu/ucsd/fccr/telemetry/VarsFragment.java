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
    public boolean[] Editables;
    int index;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_vars, container, false);


        Names = tApp.getVarNames();
        Values = tApp.getVarValues();
        Mins = tApp.getVarMins();
        Maxs = tApp.getVarMaxs();

        Editables = tApp.getVarEditables();
        adapter = new TwoLineListAdapter(rootView.getContext(), Names, Values, Editables);
        setListAdapter(adapter);
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
        index = position;

        if(Editables[index]) {
            Intent change = new Intent(getActivity(), ChangeWindowActivity.class);
            tApp.setIndex(index);
            startActivityForResult(change, REQUEST_CODE);
        }
        else {
            Toast CannotEdit = Toast.makeText(getActivity(),"Cannot Edit",Toast.LENGTH_SHORT);
            CannotEdit.show();
        }

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == 1) {

            if (resultCode == RESULT_FIRST_USER) {
                tApp.setVarValues(Values);
                adapter.notifyDataSetChanged();
            }
            if (resultCode == 0) {
                Toast fail = Toast.makeText(getActivity(), "Fail", Toast.LENGTH_SHORT);
                fail.show();
            }
        }
    }

}
