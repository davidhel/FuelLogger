package no.appfortress.fuellogger;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class RegisterVehicleActivity extends Activity{
	
	public static String EXTRA_MESSAGE  = "no.appfortress.fuellogger.RegisterActivity";
	public static String EXTRA_MESSAGE2;
	public static String EXTRA_MESSAGE3;
	public static String EXTRA_MESSAGE4;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		
		
	}
	
	public void btnSubmit(View view){
		Intent intent = new Intent(this, MyVehiclesActivity.class);
		
		TextView carBrand = (TextView) findViewById(R.id.editVehicleBrand);
		TextView carModel = (TextView) findViewById(R.id.editVehicleModel);
		TextView tankSize = (TextView) findViewById(R.id.editTankSize);
		TextView odometer = (TextView) findViewById(R.id.editOdometer);
		
		//A bundle with data that can be sent between activities
		Bundle extras = new Bundle();
		extras.putString(EXTRA_MESSAGE, carBrand.getText().toString());
		extras.putString(EXTRA_MESSAGE2, carModel.getText().toString());
		extras.putString(EXTRA_MESSAGE3, tankSize.getText().toString());
		extras.putString(EXTRA_MESSAGE4, odometer.getText().toString());
		
		intent.putExtras(extras);
		startActivity(intent);	
	}

}
