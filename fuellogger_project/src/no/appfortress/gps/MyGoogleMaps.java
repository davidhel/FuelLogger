package no.appfortress.gps;

import no.appfortress.fuellogger.GPSActivity;
import no.appfortress.fuellogger.R;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.LatLngBoundsCreator;
import com.google.android.gms.maps.model.PolylineOptions;

public class MyGoogleMaps extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.google_maps);
		double[] longitudes = getIntent().getDoubleArrayExtra(
				GPSActivity.TRACKED_LONGITUDES);
		double[] latitudes = getIntent().getDoubleArrayExtra(
				GPSActivity.TRACKED_LATITUDES);

		if (longitudes == null || latitudes == null) {
			return;
		}

		GoogleMap googleMap = ((SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.mapsFragment)).getMap();

		PolylineOptions polOptions = new PolylineOptions();
		polOptions.geodesic(false);
		polOptions.color(Color.BLUE);
		polOptions.width(10);
		LatLngBounds.Builder bounds = new LatLngBounds.Builder();

		for (int i = 0; i < longitudes.length; i++) {
			Log.d("Fuel", longitudes[i] + ":" + latitudes[i]);
			polOptions.add(new LatLng(latitudes[i], longitudes[i]));
			bounds.include(new LatLng(latitudes[i], longitudes[i]));
		}

		googleMap.addPolyline(polOptions);

		googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(
				bounds.build(),
				this.getResources().getDisplayMetrics().widthPixels, this
						.getResources().getDisplayMetrics().heightPixels, 10));

	}

}
