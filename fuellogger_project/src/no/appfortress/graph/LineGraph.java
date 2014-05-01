package no.appfortress.graph;

import org.achartengine.ChartFactory;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.TimeSeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

public class LineGraph extends Fragment{
	public Intent getIntent(Context context) {
		
		// Our first data
		int[] x = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 }; // The x values represents date
		int[] y =  { 200, 300, 245, 190, 400, 280, 190, 467, 350, 332}; // The y values represents distance driven on between two refills
		TimeSeries series = new TimeSeries("Line1"); 
		for( int i = 0; i < x.length; i++)
		{
			series.add(x[i], y[i]);
		}
		
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
		return intent;
	}
	
}
