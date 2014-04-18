package edu.ucsd.fccr.prototype;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	ListView listview;
	
	public String[] nameArray = {"x1","x2","x3","x4"};
	public double[] valueArray = { 1,2,4,3};
	public ArrayList<String> valueStringArray = new ArrayList<String>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		listview = (ListView) findViewById(R.id.listView);
		
		ArrayList<Map<String, String>> list = buildData();
	    String[] from = { "name", "purpose" };
	    int[] to = { android.R.id.text1, android.R.id.text2 };

	    SimpleAdapter adapter = new SimpleAdapter(this, list,
	        android.R.layout.simple_list_item_2, from, to);
	    listview.setAdapter(adapter);
	    
	    listview.setTextFilterEnabled(true);
	    
	    listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {		
				  // ListView Clicked item index
                int itemPosition     = position;
				
				Toast.makeText(getApplicationContext(),
				"Position :"+ Integer.toString(itemPosition) , Toast.LENGTH_LONG).show();
			}
	    	
	    });
		
	}
	
	private ArrayList<Map<String, String>> buildData() {
	    ArrayList<Map<String, String>> list = new ArrayList<Map<String, String>>();
	    
	    for(int i=0; i<nameArray.length; i++){
	    	
	    	valueStringArray.add("Value = " + Double.toString(valueArray[i]));
	    	list.add(putData(nameArray[i], valueStringArray.get(i)));
	    	
	    }
	    return list;
	  }

	  private HashMap<String, String> putData(String name, String purpose) {
	    HashMap<String, String> item = new HashMap<String, String>();
	    item.put("name", name);
	    item.put("purpose", purpose);
	    return item;
	  }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
