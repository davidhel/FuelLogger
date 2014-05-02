package no.appfortress.graph;

import java.util.Calendar;
import java.util.List;

import no.appfortress.database.RefillDBHandler;
import no.appfortress.fuellogger.R;
import no.appfortress.fuellogger.Refill;

import org.achartengine.GraphicalView;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class GraphActivity extends Activity {

	private static GraphicalView view;
	private FuelPriceLineGraph line;
	private List<Refill> refills;
	private float highestFuelPrice;

	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_graph);
		line = new FuelPriceLineGraph();
		line.setContext(this);
		refills = getDataFromDB();
		highestFuelPrice = 0;
		for (int i = 0; i < refills.size(); i++) {

			Log.d("GRAPH", refills.get(i).toString());
			Refill r = refills.get(i);
			Calendar c = r.getDate();
			int month = c.get(Calendar.MONTH);
			int day = c.get(Calendar.DAY_OF_YEAR);
			double cons = r.getFuelPrice();

			//Find the highest price
			if(highestFuelPrice < r.getFuelPrice()){
				highestFuelPrice = r.getFuelPrice();
			}
			
			Point p = MockData.getDataFromReceiver(i, cons);
			line.addNewPoints(p, c); // Add it to our graph
		}
		//Focus y axis where the elements are placed
		line.mRenderer.setYAxisMax(highestFuelPrice+1);
		
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
