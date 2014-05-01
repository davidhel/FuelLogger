package no.appfortress.fuellogger;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class TripCalculatorFragment extends Fragment{

	private Button btnSubmit;
	private EditText etDistance;
	private EditText etFuelPrice;
	private EditText etFuelConsumption;
	private TextView txtResult;
	private FragmentActivity activity;
	
	private float distance;
	private float fuelPrice;
	private float fuelConsumption;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		return inflater.inflate(R.layout.fragment_calculator, container, false);
		
		
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
		etDistance = (EditText) activity.findViewById(R.id.etDistance);
		etFuelPrice = (EditText) activity.findViewById(R.id.etFuelPrice);
		etFuelConsumption = (EditText) activity.findViewById(R.id.etFuelComsumption);
		
		
		
		btnSubmit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				//calculateFuelCost(10,15,1);
				distance = Float.valueOf(etDistance.getText().toString());
				fuelPrice = Float.valueOf(etFuelPrice.getText().toString());
				fuelConsumption = Float.valueOf(etFuelConsumption.getText().toString());
				
				txtResult.setText(Float.toString(calculateFuelCost(distance,fuelPrice,fuelConsumption)));
			}

		});
	
	}
	public float calculateFuelCost(float distance, float fuelPrice, float consumption ){
		float sum;
		float costPerMile;
		costPerMile = fuelPrice * consumption;
		sum = costPerMile * distance;
		//if fuelcost/km
		sum = sum/10;
		return sum;
	}

}
