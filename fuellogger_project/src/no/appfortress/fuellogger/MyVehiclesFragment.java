package no.appfortress.fuellogger;

import java.util.ArrayList;
import java.util.List;

import no.appfortress.database.CarDBHandler;
import android.app.AlertDialog;
import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MyVehiclesFragment extends Fragment {

	public static final String REGISTER_NEW_CAR = "REGISTER_NEW_CAR";
	private ListView listview = null;
	private CarDBHandler database;
	private List<Car> cars;
	private ArrayAdapter<Car> adapter;
	private boolean isInitialized = false;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.activity_myvehicles, container, false);
		return view;
	}


	private void setListView() {
		if(listview == null){

		listview = (ListView) getView().findViewById(R.id.listview);
		
		}else{
			listview  = (ListView) getActivity().findViewById(R.id.listview);
		}
		cars = new ArrayList<Car>();
		
		adapter = new ArrayAdapter<Car>(getActivity(),
				android.R.layout.simple_list_item_1, cars);

		listview.setAdapter(adapter);
		
		database = new CarDBHandler(getActivity());
		List<Car> carList= database.getAllCars();
		database.close();
		
		cars.clear();
		for(Car c : carList){
			cars.add(c);
		}

		adapter.notifyDataSetChanged();
		

	}

	
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		setListView();
		
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, final View view,
					final int position, long id) {
					Car c = cars.get(position);
					openCarInfo(c);
			}
		});
		
		listview.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				boolean rtn = listLongClick(position);
				
				return rtn;
			}
		});
	}

	protected boolean listLongClick(int position) {
		DialogFragment fragment = new LongClickCarDialog(cars.get(position));
		FragmentManager fm = getFragmentManager();
		fragment.show(fm, "String");
		return true;
	}

	protected void openCarInfo(Car c) {
		Intent vehicleIntent = new Intent(getActivity(), VehicleActivity.class);
		vehicleIntent.putExtra(VehicleActivity.CAR_BRAND, c.getBrand());
		vehicleIntent.putExtra(VehicleActivity.CAR_MODEL, c.getModel());
		vehicleIntent.putExtra(VehicleActivity.CAR_YEAR, c.getYear());
		vehicleIntent.putExtra(VehicleActivity.CAR_ODOMETER, c.getOdometer());
		vehicleIntent.putExtra(VehicleActivity.CAR_FUELTANK, c.getFuelTank());
		vehicleIntent.putExtra(VehicleActivity.CAR_FUEL, c.getFuel());
		startActivity(vehicleIntent);
	}

	private class LongClickCarDialog extends DialogFragment{

		private Car car;
		
		public LongClickCarDialog(Car c){
			car = c;
		}
		
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {

			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());	
			builder.setTitle(car.getBrand() + " " + car.getModel());
			return super.onCreateDialog(savedInstanceState);
		}
		
	}
	
}
