package no.appfortress.fuellogger;

import java.util.ArrayList;
import java.util.List;

import com.google.android.gms.games.multiplayer.turnbased.TurnBasedMultiplayer.InitiateMatchResult;

import no.appfortress.database.CarDBHandler;
import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

@SuppressLint("NewApi")
public class MyVehiclesFragment extends Fragment {

	public static final String REGISTER_NEW_CAR = "REGISTER_NEW_CAR";
	private ListView listview;
	private CarDBHandler database;
	private List<Car> cars;
	private ArrayAdapter<Car> adapter;
	private boolean isInitialized = false;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.activity_myvehicles, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		database = new CarDBHandler(getActivity());
		listview = (ListView) getActivity().findViewById(R.id.listviewFueling);

		cars = database.getAllCars();

		adapter = new ArrayAdapter<Car>(getActivity(),
				android.R.layout.simple_list_item_1, cars);
		listview.setAdapter(adapter);
		adapter.notifyDataSetChanged();
		database.close();

		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, final View view,
					final int position, long id) {
				if (Build.VERSION.SDK_INT >= 12) {
					Car thisCar = cars.get(position);
					database.deleteRow(thisCar.getID());
					cars.remove(position);
					view.animate().setDuration(300).alpha(0).translationX(1000)
							.withEndAction(new Runnable() {
								@Override
								public void run() {

									adapter.notifyDataSetChanged();
									view.setAlpha(1);
								}
							});
				} else {

				}
			}
		});
	}

}
