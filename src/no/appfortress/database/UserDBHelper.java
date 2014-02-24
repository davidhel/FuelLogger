package no.appfortress.database;

import no.appfortress.database.UserDatabaseContract.UserFeedEntry;
import no.appfortress.fuellogger.R;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
/**
 * 
 * @author Morgan
 *
 */
public class UserDBHelper extends SQLiteOpenHelper{
	
	private static final String SQL_CREATE_ENTRIES = 
			"CREATE TABLE " + UserFeedEntry.TABLE_NAME + " ("
			+ UserFeedEntry._ID + " INTEGER PRIMARY KEY, "
			+ UserFeedEntry.COLUMN_NAME + " VARCHAR(255) NOT NULL)";
	
	private static final String SQL_DELETE_ENTRIES =
			"DROP TABLE IF EXISTS " +  UserFeedEntry.TABLE_NAME;
	
	public UserDBHelper(Context c){
		super(c, c.getResources().getString(R.string.databaseName), null, c.getResources().getInteger(R.integer.databaseVersion));
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(SQL_CREATE_ENTRIES);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL(SQL_DELETE_ENTRIES);
		onCreate(db);
	}

}
