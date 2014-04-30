package no.appfortress.fuellogger;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class TripCalculatorActivity extends Fragment{

	private Button btnSubmit;
	private TextView txtResult;
	private FragmentActivity activity;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		return inflater.inflate(R.layout.activity_calculator, container, false);
		
		
	}
	
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		initGUIElements();
	}

	public void initGUIElements() {
		activity = getActivity();
		
		btnSubmit = (Button) activity.findViewById(R.id.btnCalculate);
		txtResult = (TextView) activity.findViewById(R.id.txtResult);
		
		btnSubmit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				calculateFuelCost(10,15,1);
				
				txtResult.setText(Float.toString(calculateFuelCost(10,15,1)));
			}

		});
	
	}
	public float calculateFuelCost(float distance, float fuelPrice, float consumption ){
		float sum = (Float) null;
		float costPerMile;
		costPerMile = fuelPrice * consumption;
		sum = costPerMile * distance;
		return sum;
	}

}
