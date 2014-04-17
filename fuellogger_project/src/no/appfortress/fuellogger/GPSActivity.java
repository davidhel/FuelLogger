package no.appfortress.fuellogger;

import no.appfortress.gps.GPSReceiver;
import android.app.Activity;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;
import android.widget.ToggleButton;

public class GPSActivity extends Activity {

	public static final String SEND_LOCATION = "SEND_LOCATION";
	
	private ArrayAdapter<String> aa;

	private ToggleButton tglSer;
	private ListView lstPos;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		initGUI();
	}

	private void initGUI() {
		setContentView(R.layout.activity_gps);

		// Get the list view

		lstPos = (ListView) findViewById(R.id.lvLocations);

		// Gets the ToggleButton from the xml layout file

		tglSer = (ToggleButton) findViewById(R.id.tbStartService);

		// Handling the ToggleButton actions

		tglSer.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (isChecked) {
					registerReceiver();
				} else {
					unregisterReceiver();
				}

			}
		});

	}

	protected void unregisterReceiver() {
		GPSReceiver.stopService(this);
		
	}

	protected void registerReceiver() {
		IntentFilter mStatusIntentFilter = new IntentFilter(
				SEND_LOCATION);
		LocalBroadcastManager lbm = LocalBroadcastManager.getInstance(this);
		GPSReceiver gpsRec = new GPSReceiver();
		lbm.registerReceiver(gpsRec, mStatusIntentFilter);
	}
}
