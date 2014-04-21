package no.appfortress.fuellogger;

import java.util.ArrayList;
import java.util.List;

import no.appfortress.gps.GPSTrackService;
import no.appfortress.gps.MyGoogleMaps;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;
import android.widget.ToggleButton;

public class GPSActivity extends Activity {

	public static final String SEND_LOCATION = "SEND_LOCATION";
	public static final String LOCATION_FILTER = "LOCATION_FILTER";
	public static final String RECEIVE_LATITUDE = "RECEIVE_LATITUDE";
	public static final String RECEIVE_LONGITUDE = "RECEIVE_LONGITUDE";
	public static final String SAVE_LONGITUDE_LIST = "SAVE_LONGITUDE_LIST";
	public static final String SAVE_LATITUDE_LIST = "SAVE_LATITUDE_LIST";
	public static final String SAVE_TRACKING_STATE = "SAVE_TRACKING_STATE";
	public static final String TRACKED_LONGITUDES = "TRACKED_LONGITUDES";
	public static final String TRACKED_LATITUDES = "TRACKED_LATITUDES";

	private BroadcastReceiver locationReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			Log.d("Fuel", "onReceive");
			double latitude = intent.getDoubleExtra(RECEIVE_LATITUDE, -1000);
			double longitude = intent.getDoubleExtra(RECEIVE_LONGITUDE, -1000);
			double[] position = { latitude,longitude};
			locationsTracked.add(position);
			updateList();
		}

	};

	// Tracking variable
	private AlarmManager alarmManager;
	private Intent startServiceIntent;
	private PendingIntent startServicePendingIntent;
	private List<double[]> locationsTracked;
	private int intervalTime = 5 * 1000;
	private boolean tracking = false;
	
	

	// GUI VARIABLES
	private ArrayAdapter<String> aa;
	private ToggleButton tglSer;
	private ListView lstPos;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Log.d("Fuel", "onCreate");
	
		if(savedInstanceState == null || locationsTracked == null){
			locationsTracked = new ArrayList<double[]>();
		}
		setContentView(R.layout.activity_gps);
		initGUI();

	}
	
	

	protected void updateList() {
		Log.d("Fuel", "onUpdate");
		
		List<String> listPositions = getLatLonForList(locationsTracked);
		
		aa = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, listPositions);
		lstPos.setAdapter(aa);
	}

	private List<String> getLatLonForList(List<double[]> locationList) {
		List<String> rtnList = new ArrayList<String>();
		for(int i = 0; i < locationList.size(); i++){
			rtnList.add(locationList.get(i)[0] + ", " + locationList.get(i)[1]);
		}
		return rtnList;
	}



	private void initGUI() {

		
		// Get the list view

		lstPos = (ListView) findViewById(R.id.lvLocations);

		// Gets the ToggleButton from the xml layout file

		tglSer = (ToggleButton) findViewById(R.id.tbStartService);

		// Set toogle buttons checked true/false, depending if the app is
		// tracking location or not

		tglSer.setChecked(tracking);

		// Handling the ToggleButton actions

		tglSer.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (isChecked) {
					registerReceiver();
					locationsTracked.clear();
					updateList();
				} else {
					unregisterReceiver();
					showRoute();
				}

			}
		});
		
		updateList();

	}

	protected void showRoute() {
		if(locationsTracked.size() < 1){
			return;
		}
		Intent intent = new Intent(this, MyGoogleMaps.class);
		double[] longitudesTracked = new double[locationsTracked.size()];
		double[] latitudesTracked = new double[locationsTracked.size()];
		
		for(int i = 0; i < locationsTracked.size();i++){
			longitudesTracked[i] = locationsTracked.get(i)[1];
			latitudesTracked[i] = locationsTracked.get(i)[0];
		}
		intent.putExtra(TRACKED_LONGITUDES, longitudesTracked);
		intent.putExtra(TRACKED_LATITUDES, latitudesTracked);
		startActivity(intent);
	}



	protected void unregisterReceiver() {
		LocalBroadcastManager.getInstance(this).unregisterReceiver(
				locationReceiver);
		stopService();
	}

	private void stopService() {
		if (alarmManager != null) {
			alarmManager.cancel(startServicePendingIntent);
		}
	}

	protected void registerReceiver() {
		LocalBroadcastManager.getInstance(this).registerReceiver(
				locationReceiver, new IntentFilter(LOCATION_FILTER));
		startService();

	}

	private void startService() {
		alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

		startServiceIntent = new Intent(this, GPSTrackService.class);

		startServicePendingIntent = PendingIntent.getService(this, 0,
				startServiceIntent, 0);

		alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
				System.currentTimeMillis() - intervalTime, intervalTime,
				startServicePendingIntent);

	}

}
