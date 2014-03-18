package no.appfortress.fuellogger;


import java.util.Calendar;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener{

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		final Calendar cal =  Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH);
		int  day = cal.get(Calendar.DAY_OF_MONTH);
		return super.onCreateDialog(savedInstanceState);
	}
	
	
	
}
