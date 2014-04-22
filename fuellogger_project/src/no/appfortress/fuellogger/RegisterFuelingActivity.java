package no.appfortress.fuellogger;

import java.util.Calendar;

import no.appfortress.database.RefillDBHandler;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.Button;

public class RegisterFuelingActivity extends FragmentActivity {

	Button btnDate;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fueling);
		initGUIElements();
	}
	
	public void initGUIElements(){
		Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		int day = c.get(Calendar.DAY_OF_MONTH);
		
		btnDate = (Button)findViewById(R.id.btnPickDate);
		btnDate.setEnabled(true );
		btnDate.setText(day + "/" + month + "/" + year);
	}

	public void showDatePickerDialog(View v) {
		//DialogFragment newFragment = new DatePickerFragment();
		//newFragment.show(getSupportFragmentManager(), "datePicker");
	}
	
	public void setDate(int year, int month, int day){
		btnDate = (Button)findViewById(R.id.btnPickDate);
		btnDate.setText(year + "/" + month + "/" + day);
	}
	
	public void btnOdo(View view){
		new AlertDialog.Builder(this)
	    .setMessage(R.string.whatIsOdo)
	    .show();
	}
	public void btnSubmitFueling(View view){
		RefillDBHandler database = new RefillDBHandler(this);
		Car car = new Car(15, "Toyota", "Corolla", 2003, 150000, 50);
		database.newRefill(car, 40, 13, 156000);
		
		Intent intent = new Intent(this, MyFuelingsActivity.class);
		startActivity(intent);
	}
}
