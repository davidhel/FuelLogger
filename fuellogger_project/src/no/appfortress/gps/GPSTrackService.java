package no.appfortress.gps;

import java.util.ArrayList;
import java.util.List;

import no.appfortress.fuellogger.R;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;

public class GPSTrackService extends Service implements
		GooglePlayServicesClient.OnConnectionFailedListener,
		GooglePlayServicesClient.ConnectionCallbacks, LocationListener {

	private static final int TRACKING_NOTIFICATION_ID = 1000;

	private final IBinder mBinder = new GPSTrackBinder();

	private boolean tracking = false;
	private LocationRequest locationRequest;

	private List<Location> locations;
	private LocationClient locationClient;
	private NotificationManager notificationManager;

	public class GPSTrackBinder extends Binder {
		public GPSTrackService getService() {
			return GPSTrackService.this;
		}
	}

	@Override
	public IBinder onBind(Intent intent) {
		return mBinder;
	}

	public void startGPSTracking() {
		if (!tracking) {
			tracking = true;
			locations = new ArrayList<Location>();
			locationClient = new LocationClient(this, this, this);
			locationClient.connect();
		}
	}

	public void stopGPSTracking() {
		if (tracking) {
			tracking = false;
			removeNotification();
			locationClient.removeLocationUpdates(this);
			locationClient.disconnect();
		}
	}

	public List<Location> getLocations() {
		return locations;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onConnected(Bundle arg) {
		if (tracking) {
			
			locationRequest = new LocationRequest();
			if(arg == null){
				locationRequest.setInterval(1000 * 10 * 1);
				locationClient.requestLocationUpdates(locationRequest, this);
				setNotification();
			}
		}

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
		// TODO Auto-generated method stub

	}

	@Override
	public void onLocationChanged(Location location) {
		locations.add(location);

<<<<<<< HEAD
		@Override
		protected Void doInBackground(Void... params) {
			while(tracking){
				locations.add(mLocationClient.getLastLocation());
				try{
					Thread.sleep(1000*10);
				}catch(InterruptedException ex){
				}
			}
			return null;
		}		
		
>>>>>>> 0d723fbf304cbea73548f9c5eca626ee8ee36135
=======
>>>>>>> 8c9329d1ba2e1b6b402de657aeef4f3f35f6aeec
	}

}
