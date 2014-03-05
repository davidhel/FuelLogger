package no.appfortress.fuellogger;

import java.util.ArrayList;
import java.util.List;

import no.appfortress.database.CarDBHandler;
import no.appfortress.database.CarDatabaseContract.CarFeedEntry;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class DatabaseActivity extends ListActivity {

	List<String> values;
	CarDBHandler handler;
	ArrayAdapter<String> aa;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.database_layout);
		handler = new CarDBHandler(this);
		if (handler.insertCar("Peugeot", "306", 1997, 134193, 40.0f)) {
			Toast.makeText(this, "Data saved", Toast.LENGTH_SHORT).show();
		} else {
			Toast.makeText(this, "Couldn't save data", Toast.LENGTH_LONG)
					.show();
		}

		Cursor c = handler.getAllCars();
		values = new ArrayList<String>();
		String carBrand, carModel;
		int year, id;
		c.moveToFirst();
		Log.d("if", "1");
		Log.d("sadfojasfioejwlkafjdoijf", Boolean.toString(c.isAfterLast()));
		while (!c.isAfterLast()) {
			Log.d("while", "1");
			id = c.getInt(c.getColumnIndex(CarFeedEntry._ID));
			carBrand = c.getString(c
					.getColumnIndex(CarFeedEntry.COLUMN_CAR_BRAND));
			carModel = c.getString(c
					.getColumnIndex(CarFeedEntry.COLUMN_CAR_MODEL));
			year = c.getInt(c.getColumnIndex(CarFeedEntry.COLUMN_YEAR));
			values.add(Integer.toString(id) + ". " + carBrand + " " + carModel
					+ " " + Integer.toString(year));
			c.moveToNext();
		}

		c.close();
		handler.close();
		updateListAdapter();
	}

	@Override
	protected void onListItemClick(ListView l, View v, final int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		new AlertDialog.Builder(this).setTitle("Delete Car?").setMessage("Your car will then be erased!").setPositiveButton("Delete", new DialogInterface.OnClickListener(){
			
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				values.remove(position);
				handler.deleteRow(position+1);
				updateListAdapter();
			}

			
		})
		.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
			}
		}).show();
		
	}

	protected void updateListAdapter() {
		aa = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, values);
		setListAdapter(aa);
	}

}
