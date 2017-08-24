package com.example.lenovo.bgtest2;

import android.app.Service;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import java.util.Date;

public class BroadcastService  extends Service {
    private static final String TAG = "BroadcastService";
    public static final String BROADCAST_ACTION = "com.websmithing.broadcasttest.displayevent";
    private final Handler handler = new Handler();
    Intent intent;
    int counter = 0;
    Boolean status=false,intent_result=false;
    private static final int REQUEST_PERMISSION_CODE = 45;
    int img_Count=0;
  //  SharedPreferences preferStore = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
   // SharedPreferences.Editor editor;

    public BroadcastService() {
   //      editor = preferStore.edit();


    }

    @Override
    public void onCreate() {
        super.onCreate();

        intent = new Intent(BROADCAST_ACTION);
    }

    @Override
    public void onStart(Intent intent, int startId) {
       intent_result=intent.getBooleanExtra("Toggle Status",false);
        handler.removeCallbacks(sendUpdatesToUI);
        handler.postDelayed(sendUpdatesToUI, 1000); // 1 second

    }

    private Runnable sendUpdatesToUI = new Runnable() {
        public void run() {

            ConnectivityManager conMgr = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);

            if (conMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED
                    || conMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {

                // notify user you are online
                //  Toast.makeText(getApplicationContext(), "Internet On", Toast.LENGTH_LONG).show();
               // editor.putBoolean("Connection", true);
                status=true;


            } else {
                if (conMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.DISCONNECTED
                        || conMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.DISCONNECTED) {

                    // notify user you are not online
                    //   Toast.makeText(getApplicationContext(), "Internet off", Toast.LENGTH_LONG).show();
                   // editor.putBoolean("Connection", false);
                    status=false;
                }
            }
            DisplayLoggingInfo();
            handler.postDelayed(this, 10000); // 10 seconds
        }
    };

    private void DisplayLoggingInfo() {
        Log.d(TAG, "entered DisplayLoggingInfo");

        intent.putExtra("time", new Date().toLocaleString());
        intent.putExtra("counter", String.valueOf(++counter));
        intent.putExtra("Status",status);
        if(status)
        {
            Toast.makeText(getApplicationContext(),"Internet ON", Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(getApplicationContext(),"Internet OFF", Toast.LENGTH_LONG).show();
        }
        //intent.putExtra("image",String.valueOf(R.drawable.abc1));
        sendBroadcast(intent);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        handler.removeCallbacks(sendUpdatesToUI);
        super.onDestroy();
    }
}
