package no.appfortress.fuellogger;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import no.appfortress.gps.GPSTrackService;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class GPSActivity extends Activity {

	// Used to keep track of if a service is binding
	/*private ServiceConnection mConnection = new ServiceConnection() {

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			GPSTrackBinder gpsTrackBinder = (GPSTrackBinder) service;
			gpsTrackService = gpsTrackBinder.getService();
			boundToService = true;
			startServiceTracking();			
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			boundToService = false;
			
		}

	};*/
	
	private BroadcastReceiver receiver = new BroadcastReceiver(){

		@Override
		public void onReceive(Context context, Intent intent) {
			Bundle bundle = intent.getExtras();
			Double latitude = bundle.getDouble(GPSTrackService.LATITUDE);
			Double longitude = bundle.getDouble(GPSTrackService.LONGITUDE);
			createToast(latitude + " : " + longitude);
		}
		
	};
	
	private static final int REPEAT_TIME = 10 * 1000;

	private GPSTrackService gpsTrackService;
	private boolean boundToService = false;
	private List<Location> trackedLocations;
	private ArrayAdapter<String> aa;

	private ToggleButton tglSer;
	private ListView lstPos;

	private Intent gpsServiceIntent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		initGUI();
		
	}



	protected void stopServiceTracking() {
		gpsTrackService.stopGPSTracking();
		trackedLocations = gpsTrackService.getLocations();
		updateListAdapter();
	}

	protected void startServiceTracking() {
		gpsTrackService.startGPSTracking();

	}

	private void initGUI() {
		setContentView(R.layout.activity_gps);

		final AlarmManager alarm = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
		
		
		// Initializing the service Intent


		gpsServiceIntent = new Intent(this, GPSTrackService.class);

		final PendingIntent pintent = PendingIntent.getService(this, 0, gpsServiceIntent, 0);
		
		// Get the list view
		
		lstPos = (ListView) findViewById(R.id.lvLocations);
		
		// Gets the ToggleButton from the xml layout file

		tglSer = (ToggleButton) findViewById(R.id.tbStartService);

		tglSer.setChecked(boundToService);

		
		// Handling the ToggleButton actions

		tglSer.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {

				// if toggle button is checked, it will start a bind service

				createToast("Bound to service: " + boundToService + " - is checked : " + isChecked);
				if (isChecked) {
					Calendar cal = Calendar.getInstance();
					alarm.setRepeating(AlarmManager.RTC,cal.getTimeInMillis(), REPEAT_TIME, pintent);
					/*
					bindService(gpsServiceIntent, mConnection,
							Context.BIND_NOT_FOREGROUND);*/
				} else {
					if (boundToService) {
						
						alarm.cancel(pintent);
						/*
						unbindService(mConnection);
						stopServiceTracking();*/
					}
				}

			}
		});
		if(trackedLocations != null){
			updateListAdapter();
		}


	}

	protected void updateListAdapter() {
		// Initialize ArrayAdapter
		List<String> stringLocations = getLocationAsStrings();
		aa = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, stringLocations);

		lstPos.setAdapter(aa);
	}

	private List<String> getLocationAsStrings() {
		List<String> rtnList = new ArrayList<String>();
		Location curLoc;
		Log.d("Test","l1");
		for (int i = 0; i < trackedLocations.size(); i++) {
			curLoc = trackedLocations.get(i);
			rtnList.add("Latitude:\t" + Double.toString(curLoc.getLatitude()) + "\n"
					+ "Longitude:\t" + Double.toString(curLoc.getLongitude()));
		}
		Log.d("Test","l2");
		return rtnList;
	}

	private void createToast(String text){
		Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
	}
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();

	}

	@Override
	protected void onStop() {
		super.onStop();
	}

}
