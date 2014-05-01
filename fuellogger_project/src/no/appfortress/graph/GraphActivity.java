package no.appfortress.graph;

import no.appfortress.fuellogger.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class GraphActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_graph);
	}
	public void lineGraphHandler (View view){
		LineGraph line = new LineGraph();
		Intent linteIntent = line.getIntent(this);
		startActivity(linteIntent);
	}

}
