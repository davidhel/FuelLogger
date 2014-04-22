package no.appfortress.fuellogger;

import java.util.List;

import no.appfortress.database.CarDBHandler;
import no.appfortress.database.RefillDBHandler;
import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class MyFuelingsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_myvehicles);
		final ListView listview = (ListView) findViewById(R.id.listview);

		final RefillDBHandler database = new RefillDBHandler(this);
		final List<Refill> refills = database.getAllRefills();

		final ArrayAdapter<Refill> adapter = new ArrayAdapter<Refill>(this,
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
									Refill thisRefill = refills.get(position);
									database.deleteRefill(thisRefill.getID());
									refills.remove(position);
									adapter.notifyDataSetChanged();
									view.setAlpha(1);*/
								}
							});
				} else {

				}
			}
		});

	}

}
