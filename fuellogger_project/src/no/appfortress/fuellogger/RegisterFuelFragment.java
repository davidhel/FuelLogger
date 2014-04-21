package no.appfortress.fuellogger;

import java.util.Calendar;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

public class RegisterFuelFragment extends Fragment {

	private Button btnDate;
	private FragmentActivity activity;
	private int year;
	private int month;
	private int day;

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

		Calendar c = Calendar.getInstance();
		year = c.get(Calendar.YEAR);
		month = c.get(Calendar.MONTH);
		day = c.get(Calendar.DAY_OF_MONTH);

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
				btnSubmitFueling();
			}

		});

	}

	protected void setDate(int year, int month, int day) {
		btnDate = (Button) activity.findViewById(R.id.btnPickDate);
		btnDate.setText(year + "/" + month + "/" + day);
	}

	protected void btnSubmitFueling() {
		// TODO Submit fueling
	}

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
