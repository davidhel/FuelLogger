package no.appfortress.fuellogger;

import java.util.List;

import no.appfortress.database.CarDBHandler;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class MyVehiclesFragment extends Fragment {

	public static final String REGISTER_NEW_CAR = "REGISTER_NEW_CAR";

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);

		Button addCar = (Button) getActivity().findViewById(R.id.btnAddCar);
		addCar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				registerNewCar();
				
			}
		});
		final ListView listview = (ListView) getActivity().findViewById(R.id.listview);

		/*
		 * //Receive intent message with Bundle() from RegisterActivity.class
		 * Intent intent = getIntent(); Bundle extras = intent.getExtras();
		 * String carBrand =
		 * extras.getString(RegisterVehicleActivity.EXTRA_MESSAGE); String
		 * carModel = extras.getString(RegisterVehicleActivity.EXTRA_MESSAGE2);
		 * String tankSize =
		 * extras.getString(RegisterVehicleActivity.EXTRA_MESSAGE3); String
		 * odometer = extras.getString(RegisterVehicleActivity.EXTRA_MESSAGE4);
		 */
		// Get data from localDB
		final CarDBHandler database = new CarDBHandler(getActivity());
		final List<Car> cars = database.getAllCars();
		/*
		 * ArrayAdapter<Car> adapter = new ArrayAdapter<Car>(this,
		 * android.R.layout.simple_list_item_1, cars); setListAdapter(adapter);
		 */
		
		for(int i = 0; i < cars.size(); i++){
			Log.d("Cars", cars.get(i).toString());
		}
		final ArrayAdapter<Car> adapter = new ArrayAdapter<Car>(getActivity(),
				android.R.layout.simple_list_item_1, cars);
		// setListAdapter(adapter);
		listview.setAdapter(adapter);
		
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, final View view,
					final int position, long id) {
				if (Build.VERSION.SDK_INT >= 12) {
					view.animate().setDuration(500).alpha(0)
							.withEndAction(new Runnable() {
								@Override
								public void run() {
									Car thisCar = cars.get(position);
									database.deleteRow(thisCar.getID());
									cars.remove(position);
									adapter.notifyDataSetChanged();
									view.setAlpha(1);
								}
							});
				} else {

				}
			}
		});

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.activity_myvehicles, container, false);
	}

	protected void registerNewCar() {
		MainActivity activity = (MainActivity)getActivity();
		activity.setContent(new RegisterVehicleFragment(), true, REGISTER_NEW_CAR);
		//Intent intent = new Intent(getActivity(), RegisterVehicleActivity.class);
		//startActivity(intent);

	}
}
