package no.appfortress.fuellogger;

import no.appfortress.database.CarDBHandler;
import no.appfortress.json.CarDataManager;
import no.appfortress.json.CarDataManager.OnVehicleRequestListener;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class RegisterVehicleFragment extends Fragment implements
		OnVehicleRequestListener, OnItemSelectedListener, OnClickListener {

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

	private String[] carmodels;
	private String selectedBrand;

	Spinner carBrand;
	EditText carModel;
	EditText tankSize;
	EditText odometer;
	Button saveCar;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.activity_register, container, false);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		getMakes();

		FragmentActivity a = getActivity();
		carBrand = (Spinner) a.findViewById(R.id.spVehicleBrand);
		carModel = (EditText) a.findViewById(R.id.editVehicleModel);
		tankSize = (EditText) a.findViewById(R.id.editTankSize);
		odometer = (EditText) a.findViewById(R.id.editOdometer);
		saveCar = (Button) a.findViewById(R.id.btnSubmit);

		carBrand.setOnItemSelectedListener(this);
		saveCar.setOnClickListener(this);
	}

	private void getMakes() {
		downloadStatus = DOWNLOAD_MAKES;
		carDataManager = new CarDataManager(this);
		carDataManager.downloadMakes();
	}

	private void getModelsFromMake(String make) {
		if (carDataManager == null) {
			carDataManager = new CarDataManager(this);
		}
		carDataManager.downloadModelsByMake(make);
	}

	@Override
	public void dataLoaded() {
		switch (downloadStatus) {
		case DOWNLOAD_MAKES:
			// makes = carDataManager.getMakes();
			// printMakes();
			break;
		case DOWNLOAD_MODELS:
			// models = carDataManager.getModels();
			break;

		}
		downloadStatus = NOT_DOWNLOADING;
	}

	private void printMakes() {
		for (int i = 0; i < makes.length; i++) {
			// Log.d("MAKES", makes[i]);
		}

	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		carmodels = getResources().getStringArray(R.array.carmodels);
		selectedBrand = carmodels[position];
		Log.d("DATABASE", selectedBrand);

	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onClick(View v) {

		/*
		 * // A bundle with data that can be sent between activities Bundle
		 * extras = new Bundle(); extras.putString(EXTRA_MESSAGE,
		 * carBrand.getText().toString()); extras.putString(EXTRA_MESSAGE2,
		 * carModel.getText().toString()); extras.putString(EXTRA_MESSAGE3,
		 * tankSize.getText().toString()); extras.putString(EXTRA_MESSAGE4,
		 * odometer.getText().toString()); intent.putExtras(extras);
		 */

		CarDBHandler database = new CarDBHandler(getActivity());

		database.insertCar(selectedBrand, carModel.getText().toString(), 0,
				Integer.parseInt(odometer.getText().toString()),
				Float.parseFloat(tankSize.getText().toString()));

		getFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
		getFragmentManager().popBackStack();
	}
}
