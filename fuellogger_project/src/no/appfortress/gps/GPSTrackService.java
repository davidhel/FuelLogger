package no.appfortress.gps;

import android.app.IntentService;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;

public class GPSTrackService extends IntentService implements
		GooglePlayServicesClient.OnConnectionFailedListener,
		GooglePlayServicesClient.ConnectionCallbacks, LocationListener {

	
	public GPSTrackService(String name) {
		super("GPSTrackService");
		
	}
	public static final String LONGITUDE = "longitude";
	public static final String LATITUDE = "latitude";
	public static final String SEND_LOCATION = "send_location";

	private LocationClient locationClient;


	

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		Log.d("Hei", "onCreate");
	}


	@Override
	public void onConnected(Bundle arg) {
		Log.d("Hei", "onConnects");/*
		Location location = locationClient.getLastLocation();
		locationClient.disconnect();
		Intent intent = new Intent(SEND_LOCATION);
		intent.putExtra(LATITUDE, location.getLatitude());
		intent.putExtra(LONGITUDE, location.getLongitude());
		sendBroadcast(intent);*/
	}

	
	@Override
	public void onDisconnected() {
	}

	@Override
	public void onConnectionFailed(ConnectionResult arg0) {

	}

	@Override
	public void onLocationChanged(Location location) {
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		
		Log.d("Hei", "handle intent");
		//tracking = true;
		//Log.d("Hei", "test");
		//locationClient = new LocationClient(this, this, this);
		//locationClient.connect();
			
	}

}
