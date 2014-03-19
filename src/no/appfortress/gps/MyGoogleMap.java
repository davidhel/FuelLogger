package no.appfortress.gps;

import no.appfortress.fuellogger.R;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

public class MyGoogleMap extends FragmentActivity{

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.google_map_activity);
		
		GoogleMap map = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
		map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
		LatLng halden = new LatLng(59.1293771,11.3702121);
		
		map.setMyLocationEnabled(true);
		map.moveCamera(CameraUpdateFactory.newLatLngZoom(halden,10));
		Polyline polyline = map.addPolyline(new PolylineOptions().geodesic(true)
				.add(new LatLng(59.1293686,11.3702836),new LatLng(59.131645,11.360442),new LatLng(59.128804,11.351687)));
		
		
	}

	
	
	
}
