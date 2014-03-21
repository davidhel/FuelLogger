package no.appfortress.fuellogger;

import no.appfortress.gps.MyGoogleMap;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

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
		
	}

	public void showDatePickerDialog(View v) {
		DialogFragment newFragment = new DatePickerFragment();
		newFragment.show(getSupportFragmentManager(), "datePicker");
	}
	
	public void setDate(int year, int month, int day){
		btnDate = (Button)findViewById(R.id.btnPickDate);
		btnDate.setText(year + "/" + month + "/" + day);
	}
	
	public void btnSubmitFueling(View view){
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
	}
}
