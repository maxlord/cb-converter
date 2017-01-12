package ru.sberbank.converter.data.service;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;

import ru.sberbank.converter.BuildConfig;
import ru.sberbank.converter.data.db.DatabaseOpenHelper;
import ru.sberbank.converter.data.network.model.ValCurs;
import ru.sberbank.converter.data.repository.CurrencyDataRepository;
import ru.sberbank.converter.data.repository.ICurrencyRepository;
import ru.sberbank.converter.net.HttpUrlConnectionHttpClient;
import ru.sberbank.converter.net.IHttpClient;


/**
 * Сервис для синхронизации курсов валют
 *
 * @author Lord (Kuleshov M.V.)
 * @since 22.12.16
 */
public class CurrencySyncService extends IntentService {

	private static final String TAG = "CurrencySyncService";
	public static final String BROADCAST_ACTION = "ru.sberbank.converter.data.service.BROADCAST";

	public CurrencySyncService() {
		super("CurrencySyncService");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		updateCurrencies();
		Intent localIntent = new Intent(BROADCAST_ACTION);
		LocalBroadcastManager.getInstance(this).sendBroadcast(localIntent);
	}

	private void updateCurrencies() {
		IHttpClient client = new HttpUrlConnectionHttpClient();
		ICurrencyRepository repository = new CurrencyDataRepository(getBaseContext(), new DatabaseOpenHelper(getBaseContext()));

		String xml = client.get(BuildConfig.WEB_SERVICE_URL, null);
		if (!TextUtils.isEmpty(xml)) {
			ValCurs valCurs = new CurrencyXmlParser(xml).parse();
			if (valCurs != null) {
				// Сохраняем в БД
				new CurrencyDataPersister(repository).store(valCurs);
			}
		}  else {
			Log.d(TAG, "Ошибка при скачивании XML с адреса: " + BuildConfig.WEB_SERVICE_URL);
		}
	}

}
