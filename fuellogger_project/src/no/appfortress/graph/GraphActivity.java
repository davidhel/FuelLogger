package no.appfortress.graph;

import java.util.Calendar;
import java.util.List;

import no.appfortress.database.RefillDBHandler;
import no.appfortress.fuellogger.R;
import no.appfortress.fuellogger.Refill;

import org.achartengine.GraphicalView;

import android.app.Activity;
import android.os.Bundle;

public class GraphActivity extends Activity {

	private static GraphicalView view;
	private LineGraph line = new LineGraph();
	private static Thread thread;
	private List<Refill> refills;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_graph);

		refills = getDataFromDB();

		for (int i = 0; i <= refills.size(); i++) {
			Refill r = refills.get(i);
			Calendar c = r.getDate();
			int month = c.get(Calendar.MONTH);
			int day = c.get(Calendar.DAY_OF_MONTH);
			String date = day + "." + month;
			double cons = r.getFuelPrice();
			Point p = MockData.getDataFromReceiver(Double.parseDouble(date), cons); // We got new data!
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

		List<Refill> refills = null;
		RefillDBHandler database = new RefillDBHandler(this);
		
		refills = database.getAllRefills();
		database.close();
		return refills;

	}
}
/*
 * public void lineGraphHandler (View view){ LineGraph line = new LineGraph();
 * Intent linteIntent = line.getIntent(this); startActivity(linteIntent); }
 */

// }
