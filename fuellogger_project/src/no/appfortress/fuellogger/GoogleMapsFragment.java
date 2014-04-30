package no.appfortress.fuellogger;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;

public class GoogleMapsFragment extends SupportMapFragment {

	SupportMapFragment mapFragment;
	private GoogleMap maps;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.google_maps, container, false);
		
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);

		/*
		 * double[] longitudes = getIntent().getDoubleArrayExtra(
		 * GPSActivity.TRACKED_LONGITUDES); double[] latitudes =
		 * getIntent().getDoubleArrayExtra( GPSActivity.TRACKED_LATITUDES);
		 * 
		 * if (longitudes == null || latitudes == null) { return; }
		 * 
		 * 
		 * SupportMapFragment fm =
		 * (SupportMapFragment)getFragmentManager().findFragmentById
		 * (R.id.mapsFragment); GoogleMap googleMap = fm.getMap();
		 * 
		 * PolylineOptions polOptions = new PolylineOptions();
		 * polOptions.geodesic(false); polOptions.color(Color.BLUE);
		 * polOptions.width(10); LatLngBounds.Builder bounds = new
		 * LatLngBounds.Builder(); /* for (int i = 0; i < longitudes.length;
		 * i++) { Log.d("Fuel", longitudes[i] + ":" + latitudes[i]);
		 * polOptions.add(new LatLng(latitudes[i], longitudes[i]));
		 * bounds.include(new LatLng(latitudes[i], longitudes[i])); }
		 * 
		 * Polyline polyline = googleMap.addPolyline(polOptions);
		 * 
		 * googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(
		 * bounds.build(), this.getResources().getDisplayMetrics().widthPixels,
		 * this .getResources().getDisplayMetrics().heightPixels, 10));
		 */
	}

}
