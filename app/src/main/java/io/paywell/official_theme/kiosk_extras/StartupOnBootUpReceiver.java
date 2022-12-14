package io.paywell.official_theme.kiosk_extras;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import io.paywell.official_theme.MainActivity;

public class StartupOnBootUpReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())){
            Intent startIntent = new Intent(context, MainActivity.class);
            startIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(startIntent);
        }
    }
}
