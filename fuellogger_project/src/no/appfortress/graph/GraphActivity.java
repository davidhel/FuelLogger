package no.appfortress.graph;

import java.util.List;

import org.achartengine.GraphicalView;

import no.appfortress.database.RefillDBHandler;
import no.appfortress.fuellogger.R;
import no.appfortress.fuellogger.Refill;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class GraphActivity extends Activity {

	private static GraphicalView view;
	private LineGraph line = new LineGraph();
	private static Thread thread;
	private List refills;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_graph);

		getDataFromDB();

		for (int i = 0; i <= refills.size(); i++) {
			Point p = MockData.getDataFromReceiver(i, i); // We got new data!
			line.addNewPoints(p); // Add it to our graph
			// view.repaint();
		}

	}

	@Override
	protected void onStart() {
		super.onStart();
		view = line.getView(this);
		setContentView(view);
	}

	private List<Refill> getDataFromDB() {

		refills = null;
		RefillDBHandler database = new RefillDBHandler(this);
		database.close();
		return refills = database.getAllRefills();

	}
}
/*
 * public void lineGraphHandler (View view){ LineGraph line = new LineGraph();
 * Intent linteIntent = line.getIntent(this); startActivity(linteIntent); }
 */

// }
