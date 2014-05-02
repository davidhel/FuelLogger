package no.appfortress.fuellogger;

import no.appfortress.database.CarDBHandler;
import no.appfortress.json.CarDataManager;
import no.appfortress.json.CarDataManager.OnVehicleRequestListener;
import android.annotation.TargetApi;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class RegisterVehicleFragment extends Fragment implements
		OnVehicleRequestListener, OnClickListener, OnCheckedChangeListener {

	private static final String DOWNLOAD_MAKES = "DOWNLOAD_MAKES";
	private static final String DOWNLOAD_MODELS = "DOWNLOAD_MODELS";
	private static final String NOT_DOWNLOADING = "NOT_DOWNLOADING";

	private CarDataManager carDataManager;

	private String downloadStatus = NOT_DOWNLOADING;

	private String[] makes = null;
	private String[] models;

	private String selectedBrand;
	private String selectedModel;

	private Spinner carBrand, carModel;
	private EditText etTankSize, etOdometer,carBrandEditText,carModelEditText;
	private Button saveCar;
	private CheckBox cbToggleManually;
	private boolean connected;

	private float tankSize;
	private int odometer;

	public RegisterVehicleFragment() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.fragment_register_vehicle, container,
				false);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		FragmentActivity a = getActivity();
		carBrand = (Spinner) a.findViewById(R.id.spVehicleBrand);
		carModel = (Spinner) a.findViewById(R.id.spVehicleModel);
		carBrandEditText = (EditText) a.findViewById(R.id.etVehicleBrand);
		carModelEditText = (EditText) a.findViewById(R.id.etVehicleModel);
		etTankSize = (EditText) a.findViewById(R.id.editTankSize);
		etOdometer = (EditText) a.findViewById(R.id.editOdometer);
		saveCar = (Button) a.findViewById(R.id.btnSubmit);
		cbToggleManually = (CheckBox) a.findViewById(R.id.cbToggleEditText);

		cbToggleManually.setOnCheckedChangeListener(this);
		
		if (isConnectedToInternet()) {
			getMakes();

		} else {
			cbToggleManually.setChecked(true);
		}
		saveCar.setOnClickListener(this);
	}

	private void fillManually(boolean manually) {

		
		if(manually){
			carBrand.setVisibility(View.INVISIBLE);
			carModel.setVisibility(View.INVISIBLE);
			carBrandEditText.setVisibility(View.VISIBLE);
			carModelEditText.setVisibility(View.VISIBLE);
		}else{
			carBrand.setVisibility(View.VISIBLE);
			carModel.setVisibility(View.VISIBLE);
			carBrandEditText.setVisibility(View.INVISIBLE);
			carModelEditText.setVisibility(View.INVISIBLE);
			getMakes();
		}
		
	}

	private boolean isConnectedToInternet() {
		ConnectivityManager conMan = (ConnectivityManager) getActivity()
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = conMan.getActiveNetworkInfo();
		return (activeNetworkInfo != null && activeNetworkInfo.isConnected());
	}

	private void getMakes() {
		if(makes == null){
			downloadStatus = DOWNLOAD_MAKES;
			carDataManager = new CarDataManager(this);
			carDataManager.downloadMakes();

			carBrand.setOnItemSelectedListener(new SelectBrandListener());
		}
	}

	private void getModelsFromMake(String make) {
		if (carDataManager == null) {
			carDataManager = new CarDataManager(this);
		}
		carDataManager.downloadModelsByMake(make);
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@Override
	public void dataLoaded(String result) {
		switch (downloadStatus) {
		case DOWNLOAD_MAKES:
			makes = carDataManager.getMakes();
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(
					getActivity(), R.layout.car_spinner_layout);
			//adapter.addAll(makes);
			//carBrand.setAdapter(adapter);

			break;
		case DOWNLOAD_MODELS:
			break;

		}
		downloadStatus = NOT_DOWNLOADING;
	}

	@Override
	public void onClick(View v) {

		String errorMessage = "Please enter all required fields";

		try {
			odometer = Integer.parseInt(etOdometer.getText().toString());

		} catch (NumberFormatException ex) {
			Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_SHORT)
					.show();
			return;
		}

		if (carBrandEditText.getVisibility() == View.VISIBLE) {

			if (carBrandEditText.getText().length() == 0
					|| carModelEditText.getText().length() == 0) {
				Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_SHORT)
						.show();
				return;
			}
			selectedBrand = carBrandEditText.getText().toString();
			selectedModel = carModelEditText.getText().toString();
		}

		CarDBHandler database = new CarDBHandler(getActivity());
		database.insertCar(selectedBrand, selectedModel, 0, odometer, tankSize);

		VehiclesFragment vehicles = (VehiclesFragment) getParentFragment();
		vehicles.onTabChanged(VehiclesFragment.YOUR_VEHICLES);

	}

	private class SelectBrandListener implements OnItemSelectedListener {

		@TargetApi(Build.VERSION_CODES.HONEYCOMB)
		@Override
		public void onItemSelected(AdapterView<?> parent, View view,
				int position, long id) {
			if (makes != null) {
				selectedBrand = makes[position];
				models = carDataManager.getModels(selectedBrand);
				ArrayAdapter<String> modelAdapter = new ArrayAdapter<String>(
						getActivity(), R.layout.car_spinner_layout);
				//modelAdapter.addAll(models);
				//carModel.setAdapter(modelAdapter);
				//carModel.setOnItemSelectedListener(new SelectModelListener());
			}
		}

		@Override
		public void onNothingSelected(AdapterView<?> parent) {
		}

	}

	private class SelectModelListener implements OnItemSelectedListener {

		@Override
		public void onItemSelected(AdapterView<?> parent, View view,
				int position, long id) {
			selectedModel = models[position];
		}

		@Override
		public void onNothingSelected(AdapterView<?> parent) {
		}

	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		if(!isConnectedToInternet() && !isChecked){
			cbToggleManually.setChecked(true);
			Toast.makeText(getActivity(), "Could not get data. No internet connection", Toast.LENGTH_SHORT).show();
			return;
		}
		fillManually(isChecked);
		
	}
}
