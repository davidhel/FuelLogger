package no.appfortress.gps;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import no.appfortress.fuellogger.GPSActivity;
import no.appfortress.fuellogger.R;

import org.json.JSONObject;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.PolylineOptions;

public class MyGoogleMaps extends ActionBarActivity {
	
	private GoogleMap googleMap;
	private LatLngBounds.Builder bounds;
	private PolylineOptions polylineOptions;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.google_maps);
		
		ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		
		double[] longitudes = getIntent().getDoubleArrayExtra(
				GPSActivity.TRACKED_LONGITUDES);
		double[] latitudes = getIntent().getDoubleArrayExtra(
				GPSActivity.TRACKED_LATITUDES);

		if (longitudes == null || latitudes == null) {
			return;
		}
		
		googleMap = ((SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.mapsFragment)).getMap();
		
		String url = getMapsAPIUrl(latitudes, longitudes);
		
		ReadTask downloadTask = new ReadTask();
		downloadTask.execute(url);

	}
	
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()){
		case android.R.id.home:
			finish();
			return true;
		}
				
		return super.onOptionsItemSelected(item);
	}



	private void setGoogleMap(){
		if(polylineOptions != null){
			bounds = new LatLngBounds.Builder();
			List<LatLng> points = polylineOptions.getPoints();
			Log.d("POINTS SIZE", points.size() + "");
			for(int i = 0; i < points.size(); i++){
				bounds.include(points.get(i));
			}
			
			googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(
					bounds.build(),
					this.getResources().getDisplayMetrics().widthPixels, this
							.getResources().getDisplayMetrics().heightPixels, 10));
		}
	}

	private String getMapsAPIUrl(double[] latitudes, double[] longitudes) {
		String waypoints = "waypoints=optimize:true";
		String origin = "origin=" + latitudes[0] + "," + longitudes[0];
		String destination = "destination=" + latitudes[latitudes.length - 1]
				+ "," + longitudes[longitudes.length - 1];
		for (int i = 1; i < latitudes.length - 1; i += Math
				.ceil((latitudes.length / 7))+1) {
			waypoints += "|" + latitudes[i] + "," + longitudes[i];
		}
		String sensor = "sensor=false";
		String params = origin + "&" + destination + "&" + waypoints + "&"
				+ sensor;
		String output = "json";

		String url = "https://maps.google.com/maps/api/directions/" + output
				+ "?" + params;
		return url;
	}

	private class ReadTask extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... params) {
			String data = "";
			try {
				HttpConnection http = new HttpConnection();
				data = http.readUrl(params[0]);
			} catch (Exception e) {
			}
			return data;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			new ParserTask().execute(result);
		}

	}

	private class ParserTask extends
			AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {

		@Override
		protected List<List<HashMap<String, String>>> doInBackground(
				String... params) {
			JSONObject jObject;
			List<List<HashMap<String, String>>> routes = null;
			Log.d("hei", params[0]);
			try {
				jObject = new JSONObject(params[0]);
				PathJSONParser parser = new PathJSONParser();
				routes = parser.parse(jObject);
			} catch (Exception e) {
				Log.d("ERROR", "doInBackground Parser");
			}
			return routes;
		}

		@Override
		protected void onPostExecute(List<List<HashMap<String, String>>> result) {
			ArrayList<LatLng> points = null;
			polylineOptions = null;

			for (int i = 0; i < result.size(); i++) {
				points = new ArrayList<LatLng>();
				polylineOptions = new PolylineOptions();
				List<HashMap<String, String>> path = result.get(i);
				for (int j = 0; j < path.size(); j++) {
					HashMap<String, String> point = path.get(j);
					double lat = Double.parseDouble(point.get("lat"));
					double lng = Double.parseDouble(point.get("lng"));
					LatLng position = new LatLng(lat, lng);

					points.add(position);
				}

				polylineOptions.addAll(points);
				polylineOptions.width(10);
				polylineOptions.color(Color.BLUE);
			}

			googleMap.addPolyline(polylineOptions);
			setGoogleMap();
		}

	}

}
