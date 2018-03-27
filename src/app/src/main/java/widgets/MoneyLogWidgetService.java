package widgets;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.Nullable;

import com.moneylog.android.moneylog.BuildConfig;
import com.moneylog.android.moneylog.R;
import com.moneylog.android.moneylog.business.TransactionBusiness;
import com.moneylog.android.moneylog.dao.BaseDaoFactory;
import com.moneylog.android.moneylog.dao.DaoFactory;

import timber.log.Timber;

/**
 * Money Log Widget Service
 */
public class MoneyLogWidgetService extends IntentService {

    public static final String ACTION_UPDATE_BALANCE_WIDGET = "com.moneylog.android.moneylog.widget.update_balance";

    public MoneyLogWidgetService() {
        super("MoneyLogWidgetService");

        if (BuildConfig.DEBUG)
            Timber.plant(new Timber.DebugTree());
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Timber.i("Running on handle intent");
        if (intent != null) {
            final String action = intent.getAction();
            if (action != null && action.equals(ACTION_UPDATE_BALANCE_WIDGET)) {
                handleUpdateBalanceAction();
            }
        }
    }

    public static void startActionUpdateBalance(Context context) {
        Timber.i("Running start action update account balance");
        Intent intent = new Intent(context, MoneyLogWidgetService.class);
        intent.setAction(ACTION_UPDATE_BALANCE_WIDGET);
        context.startService(intent);
    }

    /**
     * Handle Update Balance Action calls
     */
    private void handleUpdateBalanceAction() {
        Timber.i("Handling update balance action");
        try {
            String apiKey = getString(R.string.config_google_maps_key);
            DaoFactory daoFactory = new BaseDaoFactory(getContentResolver(), apiKey);
            final TransactionBusiness transactionBusiness = new TransactionBusiness(daoFactory);
            final double transactionAmount = transactionBusiness.getTransactionAmount();

            AppWidgetManager widgetManager = AppWidgetManager.getInstance(this);
            int[] appWidgetIds = widgetManager.getAppWidgetIds(new ComponentName(this, MoneyLogWidgetProvider.class));
            MoneyLogWidgetProvider.updateBalanceWidgets(this, widgetManager, transactionAmount, appWidgetIds);
        } catch (Exception ex) {
            Timber.e("Something went bad while trying to update account balance action");
            Timber.e(ex);
        }
    }


}
