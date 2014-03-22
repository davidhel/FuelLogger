package no.appfortress.gps;

import java.util.ArrayList;
import java.util.List;

import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;

public class GPSTrackService extends Service implements
		GooglePlayServicesClient.ConnectionCallbacks,
		GooglePlayServicesClient.OnConnectionFailedListener {

	private final IBinder mBinder = new GPSTrackBinder();
	private LocationClient mLocationClient;
	private boolean tracking = false;
	private List<Location> locations;
	private GPSTrackingTask mGPSTrackingTask;
	
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
		locations = new ArrayList<Location>();
		mLocationClient = new LocationClient(this, this, this);
		mLocationClient.connect();
		tracking = true;
	}
	
	public void stopGPSTracking(){
		tracking = false;
		mGPSTrackingTask.cancel(true);
		mLocationClient.disconnect();
	}
	
	public List<Location> getLocations(){
		return locations;
	}

	@Override
	public void onConnectionFailed(ConnectionResult arg0) {
		stopSelf();
	}

	@Override
	public void onConnected(Bundle arg0) {
		mGPSTrackingTask = new GPSTrackingTask();
		mGPSTrackingTask.execute();
	}


	@Override
	public void onDisconnected() {
		
	}
	
	

	@Override
	public void onDestroy() {
		super.onDestroy();
	}



	private class GPSTrackingTask extends AsyncTask<Void,Void,Void>{

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
		
	}
	
}
