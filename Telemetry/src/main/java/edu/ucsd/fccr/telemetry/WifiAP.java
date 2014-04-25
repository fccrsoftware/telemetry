package edu.ucsd.fccr.telemetry;

/**
 * Created by zachnagengast09 on 4/11/14.
 */
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.util.Log;

import java.lang.reflect.Method;

import edu.ucsd.fccr.telemetry.MainActivity;

public class WifiAP extends Activity {
    private static int constant = 0;

    private static final int WIFI_AP_STATE_UNKNOWN = -1;
    private static int WIFI_AP_STATE_DISABLING = 0;
    private static int WIFI_AP_STATE_DISABLED = 1;
    public int WIFI_AP_STATE_ENABLING = 2;
    public int WIFI_AP_STATE_ENABLED = 3;
    private static int WIFI_AP_STATE_FAILED = 4;

    private final String[] WIFI_STATE_TEXTSTATE = new String[] {
            "DISABLING","DISABLED","ENABLING","ENABLED","FAILED"
    };

    private WifiManager wifi;
    private String TAG = "WifiAP";

    private int stateWifiWasIn = -1;

    private boolean alwaysEnableWifi = true; //set to false if you want to try and set wifi state back to what it was before wifi ap enabling, true will result in the wifi always being enabled after wifi ap is disabled

    public void toggleWiFiAP(WifiManager wifihandler, Context context) {
        if (wifi==null){
            wifi = wifihandler;
        }

        boolean wifiApIsOn = getWifiAPState()==WIFI_AP_STATE_ENABLED || getWifiAPState()==WIFI_AP_STATE_ENABLING;
        new SetWifiAPTask(!wifiApIsOn,false,context).execute();
    }

    private int setWifiApEnabled(boolean enabled) {
        Log.d(TAG, "*** setWifiApEnabled CALLED **** " + enabled);

        WifiConfiguration config = new WifiConfiguration();
        config.SSID = "beaglebase";
        config.preSharedKey = "beaglebone"; //This is the Password.
        config.priority = 40;
        config.status = WifiConfiguration.Status.ENABLED;
        config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
        config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_EAP);
        config.allowedProtocols.set(WifiConfiguration.Protocol.RSN);
        config.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
        config.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN);
        config.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.SHARED);
        config.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
        config.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
        config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40);
        config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP104);

//        config.wepKeys[0] = "\"beaglebone\""; //This is the WEP Password.
//        config.wepTxKeyIndex = 0;

        //remember wirelesses current state
        if (enabled && stateWifiWasIn==-1){
            stateWifiWasIn=wifi.getWifiState();
        }

        //disable wireless
        if (enabled && wifi.getConnectionInfo() !=null) {
            Log.d(TAG, "disable wifi: calling");
            wifi.setWifiEnabled(false);
            int loopMax = 10;
            while(loopMax>0 && wifi.getWifiState()!=WifiManager.WIFI_STATE_DISABLED){
                Log.d(TAG, "disable wifi: waiting, pass: " + (10-loopMax));
                try {
                    Thread.sleep(500);
                    loopMax--;
                } catch (Exception e) {

                }
            }
            Log.d(TAG, "disable wifi: done, pass: " + (10-loopMax));
        }

        //enable/disable wifi ap
        int state = WIFI_AP_STATE_UNKNOWN;
        try {
            Log.d(TAG, (enabled?"enabling":"disabling") +" wifi ap: calling");
            wifi.setWifiEnabled(false);
            Method method1 = wifi.getClass().getMethod("setWifiApEnabled", WifiConfiguration.class, boolean.class);
            //method1.invoke(wifi, null, enabled); // true
            method1.invoke(wifi, config, enabled); // true
            Method method2 = wifi.getClass().getMethod("getWifiApState");
            state = (Integer) method2.invoke(wifi);
        } catch (Exception e) {
            Log.e(WIFI_SERVICE, e.getMessage());
            // toastText += "ERROR " + e.getMessage();
        }


        //hold thread up while processing occurs
        if (!enabled) {
            int loopMax = 10;
            while (loopMax>0 && (getWifiAPState()==WIFI_AP_STATE_DISABLING || getWifiAPState()==WIFI_AP_STATE_ENABLED || getWifiAPState()==WIFI_AP_STATE_FAILED)) {
                Log.d(TAG, (enabled?"enabling":"disabling") +" wifi ap: waiting, pass: " + (10-loopMax));
                try {
                    Thread.sleep(500);
                    loopMax--;
                } catch (Exception e) {

                }
            }
            Log.d(TAG, (enabled?"enabling":"disabling") +" wifi ap: done, pass: " + (10-loopMax));

            //enable wifi if it was enabled beforehand
            //this is somewhat unreliable and app gets confused and doesn't turn it back on sometimes so added toggle to always enable if you desire
            if(stateWifiWasIn==WifiManager.WIFI_STATE_ENABLED || stateWifiWasIn==WifiManager.WIFI_STATE_ENABLING || stateWifiWasIn==WifiManager.WIFI_STATE_UNKNOWN || alwaysEnableWifi){
                Log.d(TAG, "enable wifi: calling");
                wifi.setWifiEnabled(true);
                //don't hold things up and wait for it to get enabled
            }

            stateWifiWasIn = -1;
        } else if (enabled) {
            int loopMax = 10;
            while (loopMax>0 && (getWifiAPState()==WIFI_AP_STATE_ENABLING || getWifiAPState()==WIFI_AP_STATE_DISABLED || getWifiAPState()==WIFI_AP_STATE_FAILED)) {
                Log.d(TAG, (enabled?"enabling":"disabling") +" wifi ap: waiting, pass: " + (10-loopMax));
                try {
                    Thread.sleep(500);
                    loopMax--;
                } catch (Exception e) {

                }
            }
            Log.d(TAG, (enabled?"enabling":"disabling") +" wifi ap: done, pass: " + (10-loopMax));
        }
        return state;
    }

    public int getWifiAPState() {
        int state = WIFI_AP_STATE_UNKNOWN;
        try {
            Method method2 = wifi.getClass().getMethod("getWifiApState");
            state = (Integer) method2.invoke(wifi);
        } catch (Exception e) {

        }

        if(state>=10){
            //using Android 4.0+ (or maybe 3+, haven't had a 3 device to test it on) so use states that are +10
            constant=10;
        }

        //reset these in case was newer device
        WIFI_AP_STATE_DISABLING = 0+constant;
        WIFI_AP_STATE_DISABLED = 1+constant;
        WIFI_AP_STATE_ENABLING = 2+constant;
        WIFI_AP_STATE_ENABLED = 3+constant;
        WIFI_AP_STATE_FAILED = 4+constant;

        Log.d(TAG, "getWifiAPState.state " + (state==-1?"UNKNOWN":WIFI_STATE_TEXTSTATE[state-constant]));
        return state;
    }

    class SetWifiAPTask extends AsyncTask<Void, Void, Void> {
        boolean mMode; //enable or disable wifi AP
        boolean mFinish; //finalize or not (e.g. on exit)
        ProgressDialog d;

        public SetWifiAPTask(boolean mode, boolean finish, Context context) {
            mMode = mode;
            mFinish = finish;
            d = new ProgressDialog(context);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            d.setTitle("Turning WiFi AP " + (mMode?"on":"off") + "...");
            d.setMessage("...please wait a moment.");
            d.show();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            try {
                d.dismiss();
                MainActivity.setupUDP();
                MainActivity.updateStatusDisplay();
            } catch (IllegalArgumentException e) {

            };
            if (mFinish){
                finish();
            }
        }

        @Override
        protected Void doInBackground(Void... params) {
            setWifiApEnabled(mMode);
            return null;
        }
    }
}
