package com.syed.myapplication.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.TextView;

public class RevertReceiver extends BroadcastReceiver {
    TextView tv;
    public RevertReceiver(TextView tv){
        this.tv = tv;
    }
    @Override
    public void onReceive(Context context, Intent intent) {
        int batteryPercentage = intent.getIntExtra("level", 0);
        if(batteryPercentage != 0){
            tv.setText(batteryPercentage + "%");
        }
    }
}
