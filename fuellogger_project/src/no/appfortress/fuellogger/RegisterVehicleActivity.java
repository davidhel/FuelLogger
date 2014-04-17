package no.appfortress.fuellogger;

import no.appfortress.json.CarDataManager;
import no.appfortress.json.CarDataManager.OnVehicleRequestListener;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class RegisterVehicleActivity extends Activity implements
		OnVehicleRequestListener {

	public static String EXTRA_MESSAGE = "no.appfortress.fuellogger.RegisterActivity";
	public static String EXTRA_MESSAGE2;
	public static String EXTRA_MESSAGE3;
	public static String EXTRA_MESSAGE4;
	private static final String DOWNLOAD_MAKES = "DOWNLOAD_MAKES";
	private static final String DOWNLOAD_MODELS = "DOWNLOAD_MODELS";
	private static final String NOT_DOWNLOADING = "NOT_DOWNLOADING";

	private CarDataManager carDataManager;

	private String downloadStatus = NOT_DOWNLOADING;

	private String[] makes;
	private String[] models;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		getMakes();

	}

	public void btnSubmit(View view) {
		Intent intent = new Intent(this, MyVehiclesActivity.class);

		TextView carBrand = (TextView) findViewById(R.id.editVehicleBrand);
		TextView carModel = (TextView) findViewById(R.id.editVehicleModel);
		TextView tankSize = (TextView) findViewById(R.id.editTankSize);
		TextView odometer = (TextView) findViewById(R.id.editOdometer);

		// A bundle with data that can be sent between activities
		Bundle extras = new Bundle();
		extras.putString(EXTRA_MESSAGE, carBrand.getText().toString());
		extras.putString(EXTRA_MESSAGE2, carModel.getText().toString());
		extras.putString(EXTRA_MESSAGE3, tankSize.getText().toString());
		extras.putString(EXTRA_MESSAGE4, odometer.getText().toString());

		intent.putExtras(extras);
		startActivity(intent);
	}

	private void getMakes() {
		downloadStatus = DOWNLOAD_MAKES;
		carDataManager = new CarDataManager(this);
		carDataManager.downloadMakes();
	}
	

	private void getModelsFromMake(String make) {
		if(carDataManager == null){
			carDataManager = new CarDataManager(this);
		}
		carDataManager.downloadModelsByMake(make);
	}

	@Override
	public void dataLoaded() {
		switch (downloadStatus) {
		case DOWNLOAD_MAKES:
			makes = carDataManager.getMakes();
			//printMakes();
			break;
		case DOWNLOAD_MODELS:
			models = carDataManager.getModels();
			break;

		}
		downloadStatus = NOT_DOWNLOADING;
	}

	private void printMakes() {
		for(int i=0; i<makes.length; i++){
			Log.d("MAKES", makes[i]);
		}
		
	}
}
