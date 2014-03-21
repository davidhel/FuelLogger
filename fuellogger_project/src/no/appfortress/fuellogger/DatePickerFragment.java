package no.appfortress.fuellogger;


import java.util.Calendar;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.widget.DatePicker;

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener{

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		final Calendar cal =  Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH);
		int  day = cal.get(Calendar.DAY_OF_MONTH);		
		
		return new DatePickerDialog(getActivity(),this,year,month,day);
	}

	@Override
	public void onDateSet(DatePicker View, int year, int month, int day) {
		RegisterFuelingActivity regFuel = (RegisterFuelingActivity) getActivity();
		regFuel.setDate(year, month, day);
	}
	
	
	
}
