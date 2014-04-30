package no.appfortress.fuellogger;

import java.util.Random;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

public class MyWidgetProvider extends AppWidgetProvider {


	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {

		final int N = appWidgetIds.length;

		// Perform this loop procedure for each App Widget that belongs to this
		// provider
		for (int i = 0; i < N; i++) {
			int appWidgetId = appWidgetIds[i];

			// Create an Intent to launch an activity
			Intent intent = new Intent(context, MainActivity.class);
			PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
					intent, 0);

			// Get the layout for the App Widget and attach an on-click listener
			// to the button
			RemoteViews views = new RemoteViews(context.getPackageName(),
					R.layout.widget_layout);
			views.setOnClickPendingIntent(R.id.btnWidget, pendingIntent);

			// Tell the AppWidgetManager to perform an update on the current app
			// widget
			appWidgetManager.updateAppWidget(appWidgetId, views);

			/*
			 * // Get all ids ComponentName thisWidget = new
			 * ComponentName(context, MyWidgetProvider.class); int[]
			 * allWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget); for
			 * (int widgetId : allWidgetIds) { // Create some random data int
			 * number = (new Random().nextInt(100));
			 * 
			 * RemoteViews remoteViews = new
			 * RemoteViews(context.getPackageName(), R.layout.widget_layout);
			 * Log.w("WidgetExample", String.valueOf(number)); // Set the text
			 * remoteViews.setTextViewText(R.id.update, String.valueOf(number));
			 * 
			 * // Register an onClickListener Intent intent = new
			 * Intent(context, MyWidgetProvider.class);
			 * 
			 * intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
			 * intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS,
			 * appWidgetIds);
			 * 
			 * PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
			 * 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
			 * remoteViews.setOnClickPendingIntent(R.id.update, pendingIntent);
			 * appWidgetManager.updateAppWidget(widgetId, remoteViews);
			 */
		}
	}
}