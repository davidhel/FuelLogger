package no.appfortress.fuellogger;

import java.util.List;

import no.appfortress.database.CarDBHandler;
import no.appfortress.database.RefillDBHandler;
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

public class MyFuelingsActivity extends Fragment {

	private FragmentActivity activity;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		return inflater.inflate(R.layout.activity_myvehicles, container, false);

	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		initGUIElements();
	}

	public void initGUIElements() {
		activity = getActivity();
		final ListView listview = (ListView) activity
				.findViewById(R.id.listview);

		final RefillDBHandler database = new RefillDBHandler(activity);
		final List<Refill> refills = database.getAllRefills();

		final ArrayAdapter<Refill> adapter = new ArrayAdapter<Refill>(activity,
				android.R.layout.simple_list_item_1, refills);

		listview.setAdapter(adapter);

		listview.setOnItemClickListener(new OnItemClickListener() {

			@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
			@Override
			public void onItemClick(AdapterView<?> parent, final View view,
					final int position, long id) {
				if (Build.VERSION.SDK_INT >= 12) {
					view.animate().setDuration(500).alpha(0)
							.withEndAction(new Runnable() {
								@Override
								public void run() {/*
													 * Refill thisRefill =
													 * refills.get(position);
													 * database
													 * .deleteRefill(thisRefill
													 * .getID());
													 * refills.remove(position);
													 * adapter
													 * .notifyDataSetChanged();
													 * view.setAlpha(1);
													 */
								}
							});
				} else {

				}
			}
		});
	}
}
