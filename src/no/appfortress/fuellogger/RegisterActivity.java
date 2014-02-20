package no.appfortress.fuellogger;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class RegisterActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
	}
	
	public void btnSubmit(View view){
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
	}

}
