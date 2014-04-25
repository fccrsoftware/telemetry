package edu.ucsd.fccr.telemetry;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by alex on 4/24/14.
 */
public class VarsListAdapter extends BaseAdapter {

    public String[] text1 = {"oranges", "fruits", "carrot"};
    public String[] text2 = {"number one ingredient", "favorite food", "long & orange"};
    String[] Title, Detail;
    LayoutInflater inflater;
    Context context;

    VarsListAdapter() {
        Title = null;
        Detail = null;
    }
    public VarsListAdapter(Context context) {
        Title = text1;
        Detail = text2;
        this.context = context;
        inflater = LayoutInflater.from(this.context);
    }
    @Override
    public int getCount() {
        return Title.length;
    }
    @Override
    public Object getItem(int arg0) {
        return null;
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.from(context).inflate(R.layout.list_item, parent, false);
        }
        TextView title, detail;
        title = (TextView) convertView.findViewById(R.id.varstv1);
        detail = (TextView) convertView.findViewById(R.id.varstv2);
        title.setText(Title[position]);
        detail.setText(Detail[position]);

        return convertView;
    }

}