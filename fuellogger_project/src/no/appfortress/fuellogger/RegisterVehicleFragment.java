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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class RegisterVehicleFragment extends Fragment implements
		OnVehicleRequestListener, OnClickListener {

	private static final String DOWNLOAD_MAKES = "DOWNLOAD_MAKES";
	private static final String DOWNLOAD_MODELS = "DOWNLOAD_MODELS";
	private static final String NOT_DOWNLOADING = "NOT_DOWNLOADING";

	private CarDataManager carDataManager;

	private String downloadStatus = NOT_DOWNLOADING;

	private String[] makes;
	private String[] models;

	private String[] carmodels;
	private String selectedBrand;
	private String selectedModel;

	private Spinner carBrand, carModel;
	private EditText tankSize, odometer;
	private Button saveCar;

	public RegisterVehicleFragment() {

	}

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
		carModel = (Spinner) a.findViewById(R.id.spVehicleModel);
		tankSize = (EditText) a.findViewById(R.id.editTankSize);
		odometer = (EditText) a.findViewById(R.id.editOdometer);
		saveCar = (Button) a.findViewById(R.id.btnSubmit);

		carBrand.setOnItemSelectedListener(new SelectBrandListener());
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
	public void dataLoaded(String result) {
		switch (downloadStatus) {
		case DOWNLOAD_MAKES:
			makes = carDataManager.getMakes();

			ArrayAdapter<String> adapter = new ArrayAdapter<String>(
					getActivity(), R.layout.car_spinner_layout);
			adapter.addAll(makes);
			carBrand.setAdapter(adapter);

			break;
		case DOWNLOAD_MODELS:
			// models = carDataManager.getModels();
			break;

		}
		downloadStatus = NOT_DOWNLOADING;
	}

	@Override
	public void onClick(View v) {
		CarDBHandler database = new CarDBHandler(getActivity());
		database.insertCar(selectedBrand, selectedModel, 0,
				Integer.parseInt(odometer.getText().toString()),
				Float.parseFloat(tankSize.getText().toString()));
		VehiclesFragment vehicles = (VehiclesFragment) getParentFragment();
		vehicles.onTabChanged(VehiclesFragment.YOUR_VEHICLES);

	}

	private class SelectBrandListener implements OnItemSelectedListener {

		@Override
		public void onItemSelected(AdapterView<?> parent, View view,
				int position, long id) {
			if (makes != null) {
				selectedBrand = makes[position];
				models = carDataManager.getModels(selectedBrand);
				ArrayAdapter<String> modelAdapter = new ArrayAdapter<String>(getActivity(), R.layout.car_spinner_layout);
				modelAdapter.addAll(models);
				carModel.setAdapter(modelAdapter);
			}
		}

		@Override
		public void onNothingSelected(AdapterView<?> parent) {
		}

	}
}
