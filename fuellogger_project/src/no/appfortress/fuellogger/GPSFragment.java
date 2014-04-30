package no.appfortress.fuellogger;

import java.util.ArrayList;
import java.util.List;

import no.appfortress.gps.GPSTrackService;
import no.appfortress.gps.MyGoogleMaps;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class GPSFragment extends Fragment {

	public static final String SEND_LOCATION = "SEND_LOCATION";
	public static final String LOCATION_FILTER = "LOCATION_FILTER";
	public static final String RECEIVE_LATITUDE = "RECEIVE_LATITUDE";
	public static final String RECEIVE_LONGITUDE = "RECEIVE_LONGITUDE";
	public static final String SAVE_LONGITUDE_LIST = "SAVE_LONGITUDE_LIST";
	public static final String SAVE_LATITUDE_LIST = "SAVE_LATITUDE_LIST";
	public static final String SAVE_TRACKING_STATE = "SAVE_TRACKING_STATE";
	public static final String TRACKED_LONGITUDES = "TRACKED_LONGITUDES";
	public static final String TRACKED_LATITUDES = "TRACKED_LATITUDES";
	private static final String IS_TRACKING = "IS_TRACKING";

	private BroadcastReceiver locationReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			Log.d("Fuel", "onReceive");
			double latitude = intent.getDoubleExtra(RECEIVE_LATITUDE, -1000);
			double longitude = intent.getDoubleExtra(RECEIVE_LONGITUDE, -1000);
			double[] position = { latitude, longitude };
			locationsTracked.add(position);
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
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		return inflater.inflate(R.layout.activity_gps, container, false);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		if (savedInstanceState == null) {
			if (locationsTracked == null) {
				locationsTracked = new ArrayList<double[]>();
			}
		}
		tracking = GPSTrackService.isTracking();
		initGUI();
	}

	private void initGUI() {
		// Gets the ToggleButton from the xml layout file

		tglSer = (ToggleButton) getActivity().findViewById(R.id.tbStartService);

		// Set toogle buttons checked true/false, depending if the app is
		// tracking location or not

		tglSer.setChecked(tracking);

		// Handling the ToggleButton actions

		tglSer.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				tracking = isChecked;
				if (isChecked) {
					registerReceiver();
					locationsTracked.clear();
				} else {
					unregisterReceiver();
					showRoute();
				}

			}
		});

	}

	protected void showRoute() {
		if (locationsTracked.size() < 1) {
			return;
		}

		Intent intent = new Intent(getActivity(), MyGoogleMaps.class);
		double[] longitudesTracked = new double[locationsTracked.size()];
		double[] latitudesTracked = new double[locationsTracked.size()];

		for (int i = 0; i < locationsTracked.size(); i++) {
			longitudesTracked[i] = locationsTracked.get(i)[1];
			latitudesTracked[i] = locationsTracked.get(i)[0];
		}
		intent.putExtra(TRACKED_LONGITUDES, longitudesTracked);
		intent.putExtra(TRACKED_LATITUDES, latitudesTracked);
		startActivity(intent);
	}

	protected void unregisterReceiver() {
		LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(
				locationReceiver);
		stopService();
	}

	private void stopService() {
		GPSTrackService.setTracking(false);
		if(alarmManager == null){
			alarmManager = (AlarmManager) getActivity().getSystemService(
					Context.ALARM_SERVICE);
		}
		alarmManager.cancel(startServicePendingIntent);
	}

	protected void registerReceiver() {
		LocalBroadcastManager.getInstance(getActivity()).registerReceiver(
				locationReceiver, new IntentFilter(LOCATION_FILTER));
		startService();

	}

	@Override
	public void onDetach() {
		unregisterReceiver();
		super.onDetach();
	}
	
	private void startService() {
		GPSTrackService.setTracking(true);
		
		alarmManager = (AlarmManager) getActivity().getSystemService(
				Context.ALARM_SERVICE);

		startServiceIntent = new Intent(getActivity(), GPSTrackService.class);

		startServicePendingIntent = PendingIntent.getService(getActivity(), 0,
				startServiceIntent, 0);

		alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
				System.currentTimeMillis() - intervalTime, intervalTime,
				startServicePendingIntent);
		
	}


}
