package no.appfortress.gps;

import no.appfortress.fuellogger.GPSActivity;
import android.app.IntentService;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;

public class GPSTrackService extends IntentService implements
		GooglePlayServicesClient.OnConnectionFailedListener,
		GooglePlayServicesClient.ConnectionCallbacks, LocationListener {

	
	public GPSTrackService() {
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
	}


	@Override
	public void onConnected(Bundle arg) {
		locationClient.requestLocationUpdates(new LocationRequest(), this);
		
	}

	
	@Override
	public void onDisconnected() {
	}

	@Override
	public void onConnectionFailed(ConnectionResult arg0) {

	}

	@Override
	public void onLocationChanged(Location location) {
		locationClient.removeLocationUpdates(this);
		locationClient.disconnect();
		broadcastCoordinations(location.getLongitude(), location.getLatitude());
	}


	@Override
	protected void onHandleIntent(Intent intent) {
		locationClient = new LocationClient(this,this,this);
		locationClient.connect();
	}
	
	private void broadcastCoordinations(double longitude, double latitude){
		Intent newIntent = new Intent(GPSActivity.LOCATION_FILTER);
		newIntent.putExtra(GPSActivity.RECEIVE_LATITUDE, latitude);
		newIntent.putExtra(GPSActivity.RECEIVE_LONGITUDE, longitude);
		LocalBroadcastManager.getInstance(this).sendBroadcast(newIntent);
	}


}
