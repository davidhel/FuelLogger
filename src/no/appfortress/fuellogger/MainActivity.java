package no.appfortress.fuellogger;

import no.appfortress.gps.MyGoogleMap;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	public void btnRegister(View view) {
		Intent intent = new Intent(this, RegisterVehicleActivity.class);
		startActivity(intent);
	}

	public void btnAddFueling(View view) {
		Intent intent = new Intent(this, RegisterFuelingActivity.class);

		startActivity(intent);
	}

	public void btnDatabaseClick(View view){
		Intent i = new Intent(this, DatabaseActivity.class);
		startActivity(i);
	}
	
	public void btnGPS(View view){
		Intent intent = new Intent(this, GPSActivity.class);
		startActivity(intent);
	}
	
	public void btnMap(View view){
		Intent intent = new Intent(this, MyGoogleMap.class);
		startActivity(intent);
	}
	
}