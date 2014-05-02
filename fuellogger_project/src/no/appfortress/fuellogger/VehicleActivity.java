package no.appfortress.fuellogger;

import java.util.List;

import no.appfortress.database.RefillDBHandler;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class VehicleActivity extends Activity {

	public final static String CAR_BRAND = "CAR_BRAND";
	public final static String CAR_MODEL = "CAR_MODEL";
	public final static String CAR_YEAR = "CAR_YEAR";
	public final static String CAR_ODOMETER = "CAR_ODOMETER";
	public final static String CAR_FUELTANK = "FUEL_TANK";
	public final static String CAR_FUEL = "FUEL";
	public static final String CAR_ID = "CAR_ID";
	
	private String brand, model;
	private int year, odometer;
	private float fueltank, fuel;
	
	private TextView title, tvOdometer;
	private ListView lvRefills;
	private long id;
	
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
		title.setText(brand + " " + model);
		tvOdometer = (TextView) findViewById(R.id.tvOdometer);
		tvOdometer.setText(Integer.toString(odometer));
		lvRefills = (ListView) findViewById(R.id.lvCarRefills);
		
		populateListView();
	}

	private void populateListView() {
		RefillDBHandler dbHandler = new RefillDBHandler(this);
		List<Refill> refills = dbHandler.getRefillsFromCar(id);
		dbHandler.close();
		ArrayAdapter<Refill> adapter = new ArrayAdapter<Refill>(this, android.R.layout.simple_list_item_1, refills);
		lvRefills.setAdapter(adapter);
	}

	private void collectData() {

		Intent i = getIntent();
		id = i.getLongExtra(CAR_ID, -1);
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
