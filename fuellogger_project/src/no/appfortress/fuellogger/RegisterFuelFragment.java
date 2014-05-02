package no.appfortress.fuellogger;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import no.appfortress.database.CarDBHandler;
import no.appfortress.database.RefillDBHandler;
import no.appfortress.json.CarDataManager;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterFuelFragment extends Fragment {

	private Button btnDate;
	private FragmentActivity activity;
	private int year;
	private int month;
	private int day;
	private EditText etFuelLitre;
	private EditText etFuelPrice;
	private EditText etOdometer;
	private float fuelLitre;
	private float fuelPrice;
	private int odometer;
	private Spinner spMyVehicle;
	private List<Car> myCars;

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.activity_fueling, container, false);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		initGUIElements();
	}

	public void initGUIElements() {
		activity = getActivity();

		myCars = new ArrayList<Car>();
		Calendar c = Calendar.getInstance();
		year = c.get(Calendar.YEAR);
		month = c.get(Calendar.MONTH);
		day = c.get(Calendar.DAY_OF_MONTH);
		spMyVehicle = (Spinner) activity.findViewById(R.id.spYourVehicles);
		etFuelLitre = (EditText) activity.findViewById(R.id.etLitre);
		etFuelPrice = (EditText) activity.findViewById(R.id.etCost);
		etOdometer = (EditText) activity.findViewById(R.id.etOdo);
		btnDate = (Button) activity.findViewById(R.id.btnPickDate);
		btnDate.setEnabled(true);
		setDate(year, month + 1, day);

		btnDate.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				DialogFragment newFragment = new DatePickerFragment();

				newFragment.show(activity.getSupportFragmentManager(),
						"datePicker");

			}

		});
		
		////////////////////////////////////////
		 ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(activity, android.R.layout.simple_spinner_item);
		 spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down vieww
		 spMyVehicle.setAdapter(spinnerArrayAdapter);
		
		
		
		TextView whatIsOdo = (TextView) activity
				.findViewById(R.id.txtWhatIsOdo);
		whatIsOdo.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				new AlertDialog.Builder(activity)
						.setMessage(R.string.whatIsOdo).show();
			}
		});

		Button btnSubmit = (Button) activity
				.findViewById(R.id.btnSubmitFueling);
		btnSubmit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Car c;
				c = getCarById(1);
				Calendar d = Calendar.getInstance();
				d.set(year, month, day);
				
				//Try to get text from edittext fields, if fail mark input field with red color
				try {
					fuelLitre = Float.parseFloat(etFuelLitre.getText().toString());
					fuelPrice = Float.valueOf(etFuelPrice.getText().toString());
					odometer = Integer.valueOf(etOdometer.getText().toString());
				}
				catch(NumberFormatException ex) {
					etFuelLitre.setHintTextColor(Color.RED);
					etOdometer.setHintTextColor(Color.RED);
					etFuelPrice.setHintTextColor(Color.RED);
				}
				
				Log.d("SAVE",Float.toString(fuelLitre));
				btnSubmitFueling(c, fuelLitre, fuelPrice, odometer, d);
			}

		});

	}
	protected Car getCarById(int id){
		CarDBHandler carDatabase = new CarDBHandler(getActivity());
		Car c = carDatabase.getCarById(id);
		return c;
	}
	protected void setDate(int year, int month, int day) {
		btnDate = (Button) activity.findViewById(R.id.btnPickDate);
		btnDate.setText(year + "/" + month + "/" + day);
	
	}

	protected void btnSubmitFueling(Car c, float fuelLitre, float fuelPrice, int odometer, Calendar date) {
		// TODO Submit fueling
		
		RefillDBHandler database = new RefillDBHandler(getActivity());
		
		database.newRefill(c, fuelLitre, fuelPrice, odometer, date);
		Log.d("DATABASE", "savedFillup");
		database.close();
		/*VehiclesFragment vehicles = (VehiclesFragment)getParentFragment();
		vehicles.onTabChanged(VehiclesFragment.YOUR_VEHICLES);*/
	}
	
	private List<Car> getMyCarsFromDB(){
		
		CarDBHandler database;database = new CarDBHandler(getActivity());
		myCars = database.getAllCars();
		database.close();
		return myCars;
	}

	@SuppressLint("ValidFragment")
	protected class DatePickerFragment extends DialogFragment implements
			OnDateSetListener {

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			final Calendar cal = Calendar.getInstance();

			return new DatePickerDialog(getActivity(), this, year, month, day);
		}

		@Override
		public void onDateSet(DatePicker view, int _year, int monthOfYear,
				int dayOfMonth) {
			Log.d("Month of Year", Integer.toString(monthOfYear));
			year = _year;
			month = monthOfYear;
			day = dayOfMonth;

			setDate(year, monthOfYear + 1, dayOfMonth);

		}

	}

}
