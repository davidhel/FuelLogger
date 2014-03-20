package no.appfortress.fuellogger;

<<<<<<< HEAD
import no.appfortress.gps.MyGoogleMap;
import android.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {


=======
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

>>>>>>> cc1b8eaeed3eec15e6a112a96096d801ecfa6a01
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
<<<<<<< HEAD
		setUpActionBar();
	}

	private void setUpActionBar() {
=======

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
>>>>>>> cc1b8eaeed3eec15e6a112a96096d801ecfa6a01
	}

	public void btnRegister(View view) {
		Intent intent = new Intent(this, RegisterVehicleActivity.class);
		startActivity(intent);
	}

	public void btnAddFueling(View view) {
		Intent intent = new Intent(this, RegisterFuelingActivity.class);
<<<<<<< HEAD

		startActivity(intent);
	}

	public void btnDatabaseClick(View view){
		Intent i = new Intent(this, DatabaseActivity.class);
		startActivity(i);
	}
	
	public void btnGPS(View view){
		Intent intent = new Intent(this, GPSActivity.class);
		startActivity(intent);
	}
	
	public void btnMap(View view){
		Intent intent = new Intent(this, MyGoogleMap.class);
		startActivity(intent);
	}
	
=======
		startActivity(intent);
	}

>>>>>>> cc1b8eaeed3eec15e6a112a96096d801ecfa6a01
}