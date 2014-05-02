package no.appfortress.fuellogger;

import java.util.ArrayList;
import java.util.List;

import no.appfortress.gps.GPSTrackService;
import no.appfortress.gps.MyGoogleMaps;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.BitmapFactory;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;
import android.widget.ToggleButton;

public class GPSFragment extends Fragment {

	public static final int TRACK_NOTIFICATION = 432910;
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

	private boolean gpsOn;

	LocationManager locManager;
	private FragmentActivity activity;

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
		activity = getActivity();
		// get gps manager
		locManager = (LocationManager) activity
				.getSystemService(Context.LOCATION_SERVICE);
		tglSer = (ToggleButton) activity.findViewById(R.id.tbStartService);

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
					if (!locManager
							.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
						arlertIfGPSDisabled();
					}
					registerReceiver();
					locationsTracked.clear();
				} else {
					unregisterReceiver();
					showRoute();
				}

			}
		});

	}

	private void arlertIfGPSDisabled() {
		final AlertDialog.Builder builder = new AlertDialog.Builder(
				getActivity());
		builder.setMessage(R.string.gps_is_disabled)
				.setCancelable(false)
				.setPositiveButton(R.string.enable,
						new DialogInterface.OnClickListener() {
							public void onClick(final DialogInterface dialog,
									final int id) {
								startActivity(new Intent(
										android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
							}
						})
				.setNegativeButton(R.string.not_now,
						new DialogInterface.OnClickListener() {
							public void onClick(final DialogInterface dialog,
									@SuppressWarnings("unused") final int id) {
								dialog.cancel();
							}
						});
		final AlertDialog alert = builder.create();
		alert.show();
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
		removeNotification();
		if (alarmManager == null) {
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

		addNotication();

		alarmManager = (AlarmManager) getActivity().getSystemService(
				Context.ALARM_SERVICE);

		startServiceIntent = new Intent(getActivity(), GPSTrackService.class);

		startServicePendingIntent = PendingIntent.getService(getActivity(), 0,
				startServiceIntent, 0);

		alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
				System.currentTimeMillis() - intervalTime, intervalTime,
				startServicePendingIntent);

	}

	private void addNotication() {

		NotificationCompat.Builder builder = new NotificationCompat.Builder(
				getActivity())
				.setSmallIcon(R.drawable.notification_icon)
				.setContentTitle("Tracking")
				.setContentText("Aplication is currently tracking your trip.")
				.setLargeIcon(
						BitmapFactory.decodeResource(getResources(),
								R.drawable.ic_launcher)).setOngoing(true);
		Intent notiIntent = new Intent(getActivity(), MainActivity.class);
		notiIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
				| Intent.FLAG_ACTIVITY_SINGLE_TOP);

		PendingIntent intent = PendingIntent.getActivity(getActivity(), 0,
				notiIntent, 0);

		builder.setContentIntent(intent);

		NotificationManager nManager = (NotificationManager) getActivity()
				.getSystemService(Context.NOTIFICATION_SERVICE);
		nManager.notify(TRACK_NOTIFICATION, builder.build());
	}

	private void removeNotification() {
		NotificationManager nManager = (NotificationManager) getActivity()
				.getSystemService(Context.NOTIFICATION_SERVICE);
		nManager.cancel(TRACK_NOTIFICATION);
	}
}
