package edu.ucsd.fccr.telemetry;

import java.lang.reflect.Method;
import java.util.Locale;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity
{
    private String TAG = "MainActivity";

    boolean wasAPEnabled = false;
    static WifiAP wifiAp;
    private WifiManager wifi;
    static Button btnWifiToggle;

    SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setOffscreenPageLimit(4);
        mViewPager.setPageMargin(
                getResources().getDimensionPixelOffset(R.dimen.viewpager_margin));

//        btnWifiToggle = (Button) findViewById(R.id.btnWifiToggle);

        wifiAp = new WifiAP();
        wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);

//        btnWifiToggle.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
                wifiAp.toggleWiFiAP(wifi, MainActivity.this);
                Toast.makeText(getBaseContext(), "Wifi Toggled", Toast.LENGTH_LONG);
//            }
//        });



        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD|WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON|WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch(position) {

                case 0: return VarsFragment.newInstance("Variables");
                case 1: return FunctionsFragment.newInstance("Functions");
                case 2: return SSHFragment.newInstance("SSH");
                case 3: return WidgetsFragment.newInstance("Widgets");
                default: return WidgetsFragment.newInstance("Widgets");
            }
        }

        @Override
        public int getCount() {
            return 4;
        }
    }

    //Wifi Methods
    @Override
    public void onResume() {
        super.onResume();
        if (wasAPEnabled) {
            if (wifiAp.getWifiAPState()!=wifiAp.WIFI_AP_STATE_ENABLED && wifiAp.getWifiAPState()!=wifiAp.WIFI_AP_STATE_ENABLING){
                wifiAp.toggleWiFiAP(wifi, MainActivity.this);
            }
        }
//        updateStatusDisplay();
    }

    @Override
    public void onPause() {
        super.onPause();
        boolean wifiApIsOn = wifiAp.getWifiAPState()==wifiAp.WIFI_AP_STATE_ENABLED || wifiAp.getWifiAPState()==wifiAp.WIFI_AP_STATE_ENABLING;
        if (wifiApIsOn) {
            wasAPEnabled = true;
            wifiAp.toggleWiFiAP(wifi, MainActivity.this);
        } else {
            wasAPEnabled = false;
        }
//        updateStatusDisplay();
    }

    public static void updateStatusDisplay() {
        if (wifiAp.getWifiAPState()==wifiAp.WIFI_AP_STATE_ENABLED || wifiAp.getWifiAPState()==wifiAp.WIFI_AP_STATE_ENABLING) {
            btnWifiToggle.setText("Turn off");
            //findViewById(R.id.bg).setBackgroundResource(R.drawable.bg_wifi_on);
        } else {
            btnWifiToggle.setText("Turn on");
            //findViewById(R.id.bg).setBackgroundResource(R.drawable.bg_wifi_off);
        }
    }

}
