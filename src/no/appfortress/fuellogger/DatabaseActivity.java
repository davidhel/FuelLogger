package no.appfortress.fuellogger;

import java.util.List;

import no.appfortress.database.CarDBHandler;
import no.appfortress.database.RefillDBHandler;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class DatabaseActivity extends ListActivity {

	private List<Refill> fuelings;
	private RefillDBHandler fuelingHandler;
	private ArrayAdapter<Refill> aa;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.database_layout);
		
		fuelingHandler = new RefillDBHandler(this);
		fuelingHandler.newRefill(new Car(1,"Peugoet", "306", 1997, 420400, 0f), 20.2f, 512.2f, 420442);
		fuelings = fuelingHandler.getAllRefills();
		fuelingHandler.close();
		updateListAdapter();
	}

	@Override
	protected void onListItemClick(ListView l, View v, final int position,
			final long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		new AlertDialog.Builder(this)
				.setTitle("Delete Car?")
				.setMessage("Your car will then be erased!")
				.setPositiveButton("Delete",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								Refill f = fuelings.get(position);
								fuelingHandler.deleteRefill(f.getID());
								fuelings.remove(position);
								updateListAdapter();
							}

						})
				.setNegativeButton("Cancel",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {

							}
						}).show();

	}

	protected void updateListAdapter() {
		aa = new ArrayAdapter<Refill>(this,
				android.R.layout.simple_list_item_1, fuelings);
		setListAdapter(aa);
	}

}
