package no.appfortress.fuellogger;

import java.util.ArrayList;
import java.util.List;

import no.appfortress.gps.GPSTrackService;
import no.appfortress.gps.GPSTrackService.GPSTrackBinder;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;
import android.widget.ToggleButton;

public class GPSActivity extends Activity {

	// Used to keep track of if a service is binding
	private ServiceConnection mConnection = new ServiceConnection() {

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

	};

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

	protected void startServiceTracking() {
		gpsTrackService.startGPSTracking();

	}

	private void initGUI() {
		setContentView(R.layout.activity_gps);

		// Initializing the service Intent

		gpsServiceIntent = new Intent(this, GPSTrackService.class);

		// Gets the ToggleButton from the xml layout file

		tglSer = (ToggleButton) findViewById(R.id.tbStartService);

		// Handling the ToggleButton actions

		tglSer.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {

				// if toggle button is checked, it will start a bind service

				if (isChecked) {

					bindService(gpsServiceIntent, mConnection,
							Context.BIND_AUTO_CREATE);
				} else {
					if (boundToService) {
						gpsTrackService.stopGPSTracking();
						trackedLocations = gpsTrackService.getLocations();
						unbindService(mConnection);
						boundToService = false;
						updateListAdapter();
					}
				}

			}
		});
		if(trackedLocations != null){
			updateListAdapter();
		}

	}

	protected void updateListAdapter() {
		// Get ListView from xml document

		lstPos = (ListView) findViewById(R.id.lvLocations);

		// Initialize ArrayAdapter
		List<String> stringLocations = getLocationAsStrings();
		aa = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, stringLocations);

		lstPos.setAdapter(aa);
	}

	private List<String> getLocationAsStrings() {
		List<String> rtnList = new ArrayList<String>();
		Location curLoc;
		for (int i = 0; i < trackedLocations.size(); i++) {
			curLoc = trackedLocations.get(i);
			rtnList.add("Latitude: " + Double.toString(curLoc.getLatitude()) + "\n"
					+ "Longitude: " + Double.toString(curLoc.getLongitude()));
		}
		return rtnList;
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
