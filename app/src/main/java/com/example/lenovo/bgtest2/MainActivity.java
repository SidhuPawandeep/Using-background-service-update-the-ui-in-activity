package com.example.lenovo.bgtest2;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.widget.ToggleButton;

public class MainActivity extends Activity {
    private static final String TAG = "BroadcastTest";
    private Intent intent;
    Boolean status=false;
       @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ToggleButton toggleButton=(ToggleButton)findViewById(R.id.toggleButton1);
           intent = new Intent(this, com.example.lenovo.bgtest2.BroadcastService.class);
           if(toggleButton.isChecked())
           {
              status=true;
           }
           intent.putExtra("Toggle Status",status);
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            updateUI(intent);
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        startService(intent);
        registerReceiver(broadcastReceiver, new IntentFilter(com.example.lenovo.bgtest2.BroadcastService.BROADCAST_ACTION));
    }

    @Override
    public void onPause() {
        super.onPause();
        unregisterReceiver(broadcastReceiver);
        stopService(intent);
    }

    private void updateUI(Intent intent) {
        String counter =  intent.getStringExtra("counter");
        int count=Integer.parseInt(counter);
        ToggleButton toggleButton=(ToggleButton)findViewById(R.id.toggleButton1);
        StringBuilder result = new StringBuilder();
        Log.d(TAG, counter);
        result.append(toggleButton.getText());
       /* SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor=sharedPreferences.edit();
        */
       Boolean status=intent.getBooleanExtra("Status",false);
        if(status)
        {
            toggleButton.setChecked(true);
          //  Toast.makeText(getApplicationContext(),"Internet ON", Toast.LENGTH_LONG).show();
        }
        else
        {
            toggleButton.setChecked(false);
          //  Toast.makeText(getApplicationContext(),"Internet OFF",Toast.LENGTH_LONG).show();
        }
       /* if(result.toString().equals("Off"))
        {
            Toast.makeText(getApplicationContext(),"Internet Off",Toast.LENGTH_LONG).show();
        }
        else {

            Toast.makeText(getApplicationContext(),"Internet ON",Toast.LENGTH_LONG).show();

        }*/



    }
}