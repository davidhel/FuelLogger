package no.appfortress.fuellogger;

import java.util.List;

import no.appfortress.database.UserDBHandler;
import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

public class DatabaseActivity extends ListActivity{


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.database_layout);
		userHandler.insertUser("MorganMorgan");
		List<String> values = userHandler.getAllUsers();
		ArrayAdapter<String> aa = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, values);
		setListAdapter(aa);
	}

	
	
}
