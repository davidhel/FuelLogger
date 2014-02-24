package no.appfortress.fuellogger;

import java.util.ArrayList;
import java.util.List;

import no.appfortress.database.UserDBHelper;
import no.appfortress.database.UserDatabaseContract.UserFeedEntry;
import android.app.ListActivity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;

public class DatabaseActivity extends ListActivity{


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.database_layout);
		UserDBHelper userDB = new UserDBHelper(this);
		SQLiteDatabase db = userDB.getWritableDatabase();
		db.execSQL("INSERT INTO " + UserFeedEntry.TABLE_NAME + " ( " + UserFeedEntry.COLUMN_NAME + " ) VALUES ( 'Morgan Buckholm' )");
		db.close();
		userDB.close();
		Log.d("hei","hei");
		List<String> values = getValues();
		Log.d(Integer.toString(values.size()), "hi");
		for(int i = 0; i < values.size(); i++){
			Log.d(values.get(i), "heiojsf");
		}
		Log.d("hei","hei42");
		ArrayAdapter<String> aa = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, values);
		Log.d("h2i2","jf");
		setListAdapter(aa);
	}

	private List<String> getValues() {
		List<String> l = new ArrayList<String>();
		UserDBHelper helper = new UserDBHelper(this);
		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor c = db.rawQuery("SELECT * FROM " + UserFeedEntry.TABLE_NAME, null);
		c.moveToFirst();
		Log.d("Hdsklfj", "ldkjf");
		while(!c.isAfterLast()){
			l.add(c.getString(c.getColumnIndex(UserFeedEntry.COLUMN_NAME)));
			c.moveToNext();
		}
		Log.d("Hdsklfj", "ldkjf");
		helper.close();
		db.close();
		c.close();
		return l;
	}

	
	
}
