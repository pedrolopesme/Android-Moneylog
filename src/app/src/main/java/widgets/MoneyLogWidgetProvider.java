package widgets;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.moneylog.android.moneylog.BuildConfig;
import com.moneylog.android.moneylog.R;
import com.moneylog.android.moneylog.activities.AddTransactionActivity;
import com.moneylog.android.moneylog.utils.DateUtil;
import com.moneylog.android.moneylog.utils.NumberUtil;

import java.util.Date;

import timber.log.Timber;

/**
 * Implementation of App Widget functionality.
 */
public class MoneyLogWidgetProvider extends AppWidgetProvider {

    public MoneyLogWidgetProvider() {
        if (BuildConfig.DEBUG)
            Timber.plant(new Timber.DebugTree());
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        Timber.i("Updating Account Balance Widgets");
        MoneyLogWidgetService.startActionUpdateBalance(context);
    }

    @Override
    public void onEnabled(Context context) {
    }

    @Override
    public void onDisabled(Context context) {
    }

    public static void updateBalanceWidgets(Context context, AppWidgetManager widgetManager, double balance, int[] widgetIds) {
        Timber.i("Running Account Balance Widget Provider updateBalanceWidgets");
        for (int widgetId : widgetIds) {

            // Construct the RemoteViews object
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_money_log);

            views.setTextViewText(R.id.widget_balance, String.format("$ %s", NumberUtil.stringify(balance)));
            views.setTextViewText(R.id.widget_date, DateUtil.format(new Date(), "MMM - yyyy"));

            // Setting up click event
            Intent intent = new Intent(context, AddTransactionActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
            views.setOnClickPendingIntent(R.id.btn_widget_add_transaction, pendingIntent);

            // Instruct the widget manager to update the widget
            widgetManager.updateAppWidget(widgetId, views);
        }
    }
}

