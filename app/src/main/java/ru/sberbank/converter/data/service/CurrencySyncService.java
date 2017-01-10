package ru.sberbank.converter.data.service;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;

import org.simpleframework.xml.core.Persister;

import java.io.Reader;
import java.io.StringReader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import ru.sberbank.converter.BuildConfig;
import ru.sberbank.converter.data.db.entity.Currency;
import ru.sberbank.converter.data.network.model.ValCurs;
import ru.sberbank.converter.data.network.model.Valute;
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

		String xml = client.get(BuildConfig.WEB_SERVICE_URL, null);
		// Конвертируем из cp1251 в utf-8

		if (!TextUtils.isEmpty(xml)) {
			Reader reader = new StringReader(xml);
			Persister serializer = new Persister();
			try {
				ValCurs valCurs = serializer.read(ValCurs.class, reader, false);
				Log.d(TAG, "Valutes: " + valCurs.valutes.size());
				// Сохраняем в БД
				store2db(valCurs);
			} catch (Exception e) {
				Log.e(TAG, e.getMessage());
			}
		} else {
			Log.d(TAG, "Error downloading xml from url: " + BuildConfig.WEB_SERVICE_URL);
		}
	}

	private void store2db(ValCurs valCurs) {
		ICurrencyRepository repository = new CurrencyDataRepository(getBaseContext());
		if (valCurs.valutes != null && !valCurs.valutes.isEmpty()) {
			List<Currency> currencies = new ArrayList<>();

			for (Valute valute : valCurs.valutes) {
				Currency currency = new Currency();
				currency.id = valute.id;
				currency.numCode = valute.numCode;
				currency.charCode = valute.charCode;
				currency.nominal = valute.nominal;
				currency.name = valute.name;

				currency.value = parseDouble(valute.value, 0);

				currencies.add(currency);
			}

			Log.d(TAG, "Currencies: " + currencies.toString());

			repository.storeList(currencies);
		}
	}

	private double parseDouble(String s, double defValue) {
		try {
			DecimalFormat df = new DecimalFormat("0,00");
			Number num = df.parse(s);

			return num.doubleValue();
		} catch (Exception e) {
			Log.e(TAG, "Ошибка при конвертировании строки в вещественное число", e);
		}

		return defValue;
	}
}
