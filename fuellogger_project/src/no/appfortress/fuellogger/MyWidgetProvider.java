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

		//Loop through the app widget(s)
		for (int i = 0; i < N; i++) {
			int widgetId = appWidgetIds[i];

			// Create an Intent to launch an activity
			Intent intent = new Intent(context, MainActivity.class);
			PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
					intent, 0);

			// Get the layout for the App Widget and attach an on-click listener
			// to the button
			RemoteViews views = new RemoteViews(context.getPackageName(),
					R.layout.widget_layout);
			views.setOnClickPendingIntent(R.id.btnWidget, pendingIntent);
			
			appWidgetManager.updateAppWidget(widgetId, views);
		}
	}
}