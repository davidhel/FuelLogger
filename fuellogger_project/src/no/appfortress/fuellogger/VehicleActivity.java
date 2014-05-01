package no.appfortress.fuellogger;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

public class VehicleActivity extends Activity {

	public final static String CAR_BRAND = "CAR_BRAND";
	public final static String CAR_MODEL = "CAR_MODEL";
	public final static String CAR_YEAR = "CAR_YEAR";
	public final static String CAR_ODOMETER = "CAR_ODOMETER";
	public final static String CAR_FUELTANK = "FUEL_TANK";
	public final static String CAR_FUEL = "FUEL";
	
	private String brand, model;
	private int year, odometer;
	private float fueltank, fuel;
	
	TextView title;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.vehicle_fragment);
		collectData();
		initGUI();
		
		
	}
	
	private void initGUI() {
		title = (TextView)findViewById(R.id.tvVehicleTitle);
		title.setText(brand + " " + model + " " + year);
	}

	private void collectData() {

		Intent i = getIntent();
		brand = i.getStringExtra(CAR_BRAND);
		model = i.getStringExtra(CAR_MODEL);
		year = i.getIntExtra(CAR_YEAR, 0);
		odometer = i.getIntExtra(CAR_ODOMETER, 0);
		fueltank = i.getFloatExtra(CAR_FUELTANK, 0.0f);
		fuel = i.getFloatExtra(CAR_FUEL, 0.0f);
		
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()){
		case android.R.id.home:
			finish();
			break;
		}
		return super.onOptionsItemSelected(item);
	}
}
