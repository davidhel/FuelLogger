package no.appfortress.gps;

import java.util.ArrayList;
import java.util.List;

import no.appfortress.fuellogger.R;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;

public class GPSTrackService extends IntentService implements
		GooglePlayServicesClient.OnConnectionFailedListener,
		GooglePlayServicesClient.ConnectionCallbacks, LocationListener {

	
	public GPSTrackService(String name) {
		super("GPSTrackService");
	}

	private static final int TRACKING_NOTIFICATION_ID = 1000;
	public static final String LONGITUDE = "longitude";
	public static final String LATITUDE = "latitude";
	public static final String SEND_LOCATION = "send_location";
	//private final IBinder mBinder = new GPSTrackBinder();

	private boolean tracking = false;
	private LocationRequest locationRequest;

	private List<Location> locations;
	private LocationClient locationClient;
	private NotificationManager notificationManager;
	/*
	public class GPSTrackBinder extends Binder {
		public GPSTrackService getService() {
			return GPSTrackService.this;
		}
	}
	*/
	
/*
	@Override
	public IBinder onBind(Intent intent) {
		return mBinder;
	}
*/
	public void startGPSTracking() {
		
	}

	public void stopGPSTracking() {
		
	}

	
	/*
	@Override
	public boolean onUnbind(Intent intent) {
		if(tracking){
			return true;
		}else{
			return super.onUnbind(intent);
		}
	}*/

	public List<Location> getLocations() {
		return locations;
	}

	

	@Override
	public void onConnected(Bundle arg) {
		Location location = locationClient.getLastLocation();
		locationClient.disconnect();
		Intent intent = new Intent(SEND_LOCATION);
		intent.putExtra(LATITUDE, location.getLatitude());
		intent.putExtra(LONGITUDE, location.getLongitude());
		sendBroadcast(intent);
	}

	private void setNotification() {
		Notification.Builder notificationBuilder = new Notification.Builder(
				this)
				.setSmallIcon(R.drawable.notification_icon)
				.setContentTitle(
						getString(R.string.tracking_notification_title))
				.setContentText(getString(R.string.tracking_notification_text))
				.setOngoing(true);
		notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		notificationManager.notify(TRACKING_NOTIFICATION_ID,
				notificationBuilder.build());
	}

	private void removeNotification() {
		notificationManager.cancel(TRACKING_NOTIFICATION_ID);
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
		tracking = true;
		locationClient = new LocationClient(this, this, this);
		locationClient.connect();
			
	}

}
