package ru.sberbank.converter.data.service;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import ru.sberbank.converter.BuildConfig;
import ru.sberbank.converter.data.db.DatabaseOpenHelper;
import ru.sberbank.converter.data.network.model.ValCurs;
import ru.sberbank.converter.data.repository.CurrencyDataRepository;
import ru.sberbank.converter.data.repository.ICurrencyRepository;
import ru.sberbank.converter.net.HttpUrlConnectionHttpClient;
import ru.sberbank.converter.net.IHttpClient;
import ru.sberbank.converter.util.Logger;

/**
 * Сервис для синхронизации курсов валют
 *
 * @author Lord (Kuleshov M.V.)
 * @since 22.12.16
 */
public class CurrencySyncService extends Service {

	private static final String TAG = "CurrencySyncService";
	private final IBinder binder = new LocalBinder();

	private boolean isDataLoading = false;
	private String errorMessage = null;

	/**
	 * Класс для связи сервиса и активити.
	 */
	public class LocalBinder extends Binder {
		public CurrencySyncService getService() {
			return CurrencySyncService.this;
		}
	}

	/**
	 * Задача, выполняющая загрузку контента из сети асинхронно
	 */
	public class DownloadTask extends AsyncTask<Void, Void, DownloadException> {
		private DownloadCallback callback;

		public DownloadTask(DownloadCallback callback) {
			this.callback = callback;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			isDataLoading = true;
		}

		@Override
		protected DownloadException doInBackground(Void... params) {
			try {
				updateCurrencies();
			} catch (DownloadException e) {
				return e;
			}

			return null;
		}

		@Override
		protected void onPostExecute(DownloadException e) {
			super.onPostExecute(e);

			isDataLoading = false;
			if (e != null) {
				callback.error(e.getMessage());
			} else {
				callback.onComplete();
			}
		}

		/**
		 * Получает из сети и обновляет в БД список валют
		 * @throws DownloadException
		 */
		private void updateCurrencies() throws DownloadException {
			IHttpClient client = new HttpUrlConnectionHttpClient();
			ICurrencyRepository repository = new CurrencyDataRepository(new DatabaseOpenHelper(getBaseContext()));

			String xml = client.get(BuildConfig.WEB_SERVICE_URL, null);
			if (!TextUtils.isEmpty(xml)) {
				ValCurs valCurs = new CurrencyXmlParser(xml).parse();
				if (valCurs != null) {
					// Сохраняем в БД
					new CurrencyDataPersister(repository).store(valCurs);
				}
			}  else {
				Logger.d(TAG, "Ошибка при скачивании XML с адреса: " + BuildConfig.WEB_SERVICE_URL);
				throw new DownloadException("Веб-сервис недоступен");
			}
		}
	}

	public interface DownloadCallback {
		void onComplete();
		void error(String message);
	}

	@Nullable
	@Override
	public IBinder onBind(Intent intent) {
		return binder;
	}

	/**
	 *
	 * @return Выполняется ли загрузка данных
	 */
	public boolean isDataLoading() {
		return isDataLoading;
	}

	/**
	 *
	 * @return Получает сообщение об ошибке
	 */
	public String getErrorMessage() {
		return errorMessage;
	}

	public void runSyncTask(DownloadCallback callback) {
		Logger.d(TAG, "Стартуем задачу для синхронизации курсов валют");

		new DownloadTask(callback).execute();
	}
}
