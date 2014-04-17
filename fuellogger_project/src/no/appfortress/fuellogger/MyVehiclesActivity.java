package no.appfortress.fuellogger;

import java.util.List;

import no.appfortress.database.CarDBHandler;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class MyVehiclesActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_myvehicles);
		
		TextView txtCarData = (TextView) findViewById(R.id.txtCarData);
		
		
		
		/*//Receive intent message with Bundle() from RegisterActivity.class
		Intent intent = getIntent();
		Bundle extras = intent.getExtras();
		String carBrand = extras.getString(RegisterVehicleActivity.EXTRA_MESSAGE);
		String carModel = extras.getString(RegisterVehicleActivity.EXTRA_MESSAGE2);
		String tankSize = extras.getString(RegisterVehicleActivity.EXTRA_MESSAGE3);
		String odometer = extras.getString(RegisterVehicleActivity.EXTRA_MESSAGE4);*/
		//Get data from localDB
		CarDBHandler database = new CarDBHandler(this);
		List<Car> cars = database.getAllCars();
		
		String listString = "";
		
		/*for (String s : cars)
		{
		    listString += s + "\t";
		}
		txtCarData.setText("Merke: "+carBrand+" Modell: "+carModel);*/
	}
}
