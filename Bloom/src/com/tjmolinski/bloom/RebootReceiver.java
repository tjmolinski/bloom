package com.tjmolinski.bloom;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class RebootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
       Intent serviceIntent = new Intent(context, ScheduleService.class);
       serviceIntent.putExtra("caller", "RebootReceiver");
       context.startService(serviceIntent);
    }
}
