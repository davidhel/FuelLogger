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
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.widget.Toast;

public class FuelPriceLineGraph {

	Context c;

	// public Intent getIntent(Context context) {

	GraphicalView view;

	TimeSeries dataset = new TimeSeries("Fuel price");
	XYMultipleSeriesDataset mDataset = new XYMultipleSeriesDataset();
	
	//renderer is used to custumise the line of the graph
	XYSeriesRenderer renderer = new XYSeriesRenderer(); 
	//mRenderer is used to custumise the graph in general
	XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer(); 

	
	
	public FuelPriceLineGraph() {
		

	}
	@SuppressWarnings("deprecation")
	public void setContext(Context context){
		c = context;
		
		// Set text size dependent on screen dimensions
		DisplayMetrics metrics = c.getResources().getDisplayMetrics();
		float val = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 14,
				metrics);

		// Add single dataset to multiple dataset
		mDataset.addSeries(dataset);

		// Customization time for line 1!
		renderer.setColor(Color.WHITE);
		renderer.setPointStyle(PointStyle.SQUARE);
		renderer.setPointStrokeWidth(10);
		renderer.setFillPoints(true);
		mRenderer.setPointSize(6);

		mRenderer.setShowCustomTextGrid(true);
		mRenderer.setShowGrid(true);

		// Set x and y axis min and max value
		mRenderer.setPanLimits(new double[] { 0.0, Double.MAX_VALUE, 0.0,
				Double.MAX_VALUE });

		mRenderer.setXAxisMax(5);

		// Enable Zoom
		mRenderer.setZoomButtonsVisible(true);
		
		mRenderer.setChartTitleTextSize(val);
		mRenderer.setXTitle("Day");
		
		mRenderer.setLabelsTextSize(15);
		mRenderer.setLegendTextSize(40);
		mRenderer.setXLabelsPadding(80);
		mRenderer.setYLabelsPadding(50);
		mRenderer.setMargins(new int[]{ 80, 80, 0, 0 });
		mRenderer.setYTitle("Litre price");

		// mRenderer.setXLabels(0);

		mRenderer.setLabelsTextSize(val);

		// Add single renderer to multiple renderer
		mRenderer.addSeriesRenderer(renderer);

	}

	public GraphicalView getView(Context context) {
		view = ChartFactory.getLineChartView(context, mDataset, mRenderer);
		return view;
	}

	public void addNewPoints(Point p, Calendar c) {
		dataset.add(p.getX(), p.getY());

		mRenderer.addXTextLabel(p.getX(), c.get(Calendar.DAY_OF_MONTH) + "/"
				+ c.get(Calendar.MONTH));
		mRenderer.setXLabels(0);
	}

}
