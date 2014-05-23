package edu.ucsd.fccr.telemetry;

import android.content.Intent;
import android.support.v4.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.TextView;
import java.util.ArrayList;

public class VarsFragment extends ListFragment {

    private ArrayList<VarData> VarList;

    VarData tApp;
    public String Value;
    private final int REQUEST_CODE = 1;
    private final int RESULT_FIRST_USER = 1;
    VarAdapter varAdapter;
//    TextView Header;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        VarList = AllVars.get(getActivity()).getAllVars();

        varAdapter = new VarAdapter(VarList);
        setListAdapter(varAdapter);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_vars, container, false);
/*
        Header = (TextView) rootView.findViewById(R.id.VarHeader);
        Header.setText("Variables");
*/
        return rootView;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {

        tApp = ((VarAdapter) getListAdapter()).getItem(position);
        if(tApp.isEditable()) {
            Intent change = new Intent(getActivity(), ChangeVariableActivity.class);
            change.putExtra("name", tApp.getVarName());
            change.putExtra("value", tApp.getVarValue());
            change.putExtra("min", tApp.getMin());
            change.putExtra("max", tApp.getMax());
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
                Value = data.getStringExtra("value");
                tApp.setVarValue(Value);
                varAdapter.notifyDataSetChanged();
            }
            if (resultCode == 0) {
                Toast fail = Toast.makeText(getActivity(), "Fail", Toast.LENGTH_SHORT);
                fail.show();
            }
        }
    }

    private class VarAdapter extends ArrayAdapter<VarData> {
        public VarAdapter(ArrayList<VarData> variables) {
            super(getActivity(), R.layout.list_item, variables);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView == null) {
                convertView = getActivity().getLayoutInflater().inflate(R.layout.list_item, null);
            }
            VarData varData = getItem(position);
            TextView varNameTextView = (TextView) convertView.findViewById(R.id.varstv1);
            varNameTextView.setText(varData.getVarName());
            TextView varValueTextView = (TextView) convertView.findViewById(R.id.varstv2);
            varValueTextView.setText(varData.getVarValue());
            ImageView editIndicator = (ImageView) convertView.findViewById(R.id.editable_indicator);
            if (varData.isEditable())
            {
                editIndicator.setVisibility(View.VISIBLE);
            }
            else
            {
                editIndicator.setVisibility(View.INVISIBLE);
            }
            return convertView;
        }
    }

    public static VarsFragment newInstance(String text) {

        VarsFragment f = new VarsFragment();
        Bundle b = new Bundle();
        b.putString("com/MAVLink/Messages/msg", text);

        f.setArguments(b);

        return f;
    }
}
