package no.appfortress.fuellogger;

import java.util.List;

import no.appfortress.database.CarDBHandler;
import no.appfortress.database.RefillDBHandler;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
public class MyFuelingsFragment extends Fragment {

	private FragmentActivity activity;
	private ListView listview;
	private RefillDBHandler database;
	private List<Refill> refills;
	private ArrayAdapter<Refill> adapter;
	private boolean isInitialized = false;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		/*View view = inflater.inflate(R.layout.fragment_my_fuelings, container, false);
		return view;*/
		return inflater
				.inflate(R.layout.fragment_my_fuelings, container, false);

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		database = new RefillDBHandler(getActivity());
		
		listview = (ListView) getActivity().findViewById(R.id.listviewFueling);

		refills = database.getAllRefills();

		adapter = new ArrayAdapter<Refill>(getActivity(),
				android.R.layout.simple_list_item_1, refills);
		listview.setAdapter(adapter);
		adapter.notifyDataSetChanged();
		database.close();

		listview.setOnItemClickListener(new OnItemClickListener() {

			@SuppressLint("NewApi")
			@Override
			public void onItemClick(AdapterView<?> parent, final View view,
					final int position, long id) {
				if (Build.VERSION.SDK_INT >= 12) {
					Refill thisList = refills.get(position);
					database.deleteRefill(thisList.getID());
					refills.remove(position);
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

		// //////////////////////
		// initGUIElements();
	}
}
