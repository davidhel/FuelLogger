package no.appfortress.gps;

import java.util.Calendar;
import java.util.Currency;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class GPSReceiver extends BroadcastReceiver {
	
	private static AlarmManager alarm;
	
	private static Intent gpsServiceIntent;
	
	private static PendingIntent pintent;
	
	private static boolean tracking = false;
	
	public GPSReceiver(){
	}
	
	@Override
	public void onReceive(Context context, Intent intent) {
		Log.d("Hei", "On Receive");
		if(!tracking){
			startService(context);
		}
		
	}

	public static void startService(Context context) {

		alarm = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
	
		gpsServiceIntent = new Intent(context, GPSTrackService.class);

		pintent = PendingIntent.getService(context, 60, gpsServiceIntent, 0);
		
		alarm.setRepeating(AlarmManager.RTC, System.currentTimeMillis(), 10*1000, pintent);
		
	}
	
	public static void stopService(Context context){
		if(tracking){
			alarm.cancel(pintent);
			
			gpsServiceIntent = null;
			
			pintent = null;
			
			alarm = null;
		}
	}
	

}
