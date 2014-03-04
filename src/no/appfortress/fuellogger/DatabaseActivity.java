package no.appfortress.fuellogger;

import java.util.ArrayList;
import java.util.List;

import no.appfortress.database.CarDBHandler;
import no.appfortress.database.CarDatabaseContract.CarFeedEntry;
import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;

public class DatabaseActivity extends ListActivity{


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.database_layout);
		CarDBHandler handler = new CarDBHandler(this);
		handler.insertCar("AA40101", "Peugeot", "306", 1997);

		Cursor c = handler.getAllCars();
		List<String> values = new ArrayList<String>();
	
		String regNr, carBrand, carModel;
		int year, id;
		if(c.moveToFirst() == true){
			while(!c.isAfterLast()){
				id  = c.getInt(c.getColumnIndex(CarFeedEntry._ID));
				regNr = c.getString(c.getColumnIndex(CarFeedEntry.COLUMN_REGNR));
				carBrand = c.getString(c.getColumnIndex(CarFeedEntry.COLUMN_CAR_BRAND));
				carModel = c.getString(c.getColumnIndex(CarFeedEntry.COLUMN_CAR_MODEL));
				year = c.getInt(c.getColumnIndex(CarFeedEntry.COLUMN_YEAR));
				values.add(Integer.toString(id) + ". " + carBrand + " " + carModel + " " + Integer.toString(year) + " regnr: " + regNr);
				c.moveToNext();
			}
		}
		
		ArrayAdapter<String> aa = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, values);
		setListAdapter(aa);
	}

	
	
}
