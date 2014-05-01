package no.appfortress.graph;

import java.util.Calendar;
import java.util.List;

import no.appfortress.database.RefillDBHandler;
import no.appfortress.fuellogger.Refill;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.TimeSeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Toast;

public class LineGraph{
	
	
	
	
	//public Intent getIntent(Context context) {
		
		 GraphicalView view;
		
		 TimeSeries dataset = new TimeSeries("Rain Fall"); 
		 XYMultipleSeriesDataset mDataset = new XYMultipleSeriesDataset();
		
		 XYSeriesRenderer renderer = new XYSeriesRenderer(); // This will be used to customize line 1
		 XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer(); // Holds a collection of XYSeriesRenderer
		
		 
		public LineGraph()
		{
			// Add single dataset to multiple dataset
			mDataset.addSeries(dataset);
			
			// Customization time for line 1!
			renderer.setColor(Color.WHITE);
			renderer.setPointStyle(PointStyle.SQUARE);
			renderer.setFillPoints(true);
			
			// Enable Zoom
			mRenderer.setZoomButtonsVisible(true);
			mRenderer.setXTitle("Day");
			mRenderer.setYTitle("Litre price");
			
			
			
			// Add single renderer to multiple renderer
			mRenderer.addSeriesRenderer(renderer);	
		}
		
		public GraphicalView getView(Context context) 
		{
			view =  ChartFactory.getLineChartView(context, mDataset, mRenderer);
			return view;
		}
		
		public void addNewPoints(Point p, Calendar c)
		{
			dataset.add(p.getX(), p.getY());

			mRenderer.addXTextLabel(p.getX(), c.get(Calendar.DAY_OF_MONTH) + "/" + c.get(Calendar.DAY_OF_MONTH));
			mRenderer.setXLabels(1);
		}
		
		public void addLabel(){
			//for()
		}
		
}
		
		
		
		
		
		
		
		
		
		
		
		
	
/*		Log.d("DATABASE","hei");
		// Our first data
		int[] x = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 }; // The x values represents date
		int[] y =  { 200, 300, 245, 190, 400, 280, 190, 467, 350, 332}; // The y values represents distance driven on between two refills
		TimeSeries series = new TimeSeries("Line1");
		for( int i = 0; i < x.length; i++)
		{
			series.add(x[i], y[i]);
		}
		 
		//getDataFromDB();
		Log.d("DATABASE","hett");
		
		
		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		dataset.addSeries(series);
		
		XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer(); // Holds a collection of XYSeriesRenderer and customizes the graph
		XYSeriesRenderer renderer = new XYSeriesRenderer(); // This will be used to customize line 1
		renderer.setPointStyle(PointStyle.SQUARE);
		renderer.setPointStrokeWidth(5);
		mRenderer.addSeriesRenderer(renderer);
		
		
		
		mRenderer.setLabelsTextSize(30);
		mRenderer.setZoomButtonsVisible(true);
		mRenderer.setZoomEnabled(true);
		
		
		Intent intent = ChartFactory.getLineChartIntent(context, dataset, mRenderer, "Line Graph Title");
		return intent;*/
	//}

/*	private List<Refill> getDataFromDB() {
		
		refills = null;
		RefillDBHandler database = new RefillDBHandler(activity);
		return refills = database.getAllRefills();
		
	}
	
}*/
