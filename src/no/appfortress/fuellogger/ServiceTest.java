package no.appfortress.fuellogger;

import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.os.IBinder;

public class ServiceTest extends Service implements LocationListener{

	//AlarmManager
	
	
	@Override
	public IBinder onBind(Intent arg0) {		
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();			
	}
	
	@Override
	public void onLocationChanged(Location loc) {
		// TODO Auto-generated method stub
		loc.getAccuracy();
		
	}

	@Override
	public void onProviderDisabled(String arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onProviderEnabled(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
		// TODO Auto-generated method stub
		
	}
	
}
