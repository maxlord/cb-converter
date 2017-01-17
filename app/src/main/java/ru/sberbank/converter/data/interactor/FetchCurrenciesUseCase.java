package ru.sberbank.converter.data.interactor;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import java.util.List;

import ru.sberbank.converter.data.db.entity.Currency;
import ru.sberbank.converter.data.repository.ICurrencyRepository;
import ru.sberbank.converter.data.service.CurrencySyncService;
import ru.sberbank.converter.util.Logger;

/**
 * Класс бизнес-логики
 * Получает список валют из сети и сохраняет его в БД
 *
 * @author Lord (Kuleshov M.V.)
 * @since 29.12.16
 */
public class FetchCurrenciesUseCase {
	private static final String TAG = "FetchCurrenciesUseCase";

	private final ICurrencyRepository currencyRepository;
	private SyncCompletedListener completedListener;
	private CurrencySyncService service;
	private boolean isServiceBound;
	/** Определяет колбеки для связи с сервисом, передаваемые в bindService() */
	private ServiceConnection serviceConnection = new ServiceConnection() {
		@Override
		public void onServiceConnected(ComponentName className, IBinder serviceBinder) {
			Logger.d(TAG,"Подключились к сервису: " + className.toString());

			CurrencySyncService.LocalBinder binder = (CurrencySyncService.LocalBinder) serviceBinder;
			service = binder.getService();
			isServiceBound = true;

			service.runSyncTask(new CurrencySyncService.DownloadCallback() {
				@Override
				public void onComplete() {
					if (completedListener != null) {
						completedListener.onComplete();
					}
				}

				@Override
				public void error(String message) {
					if (completedListener != null) {
						completedListener.error(message);
					}
				}
			});
		}

		@Override
		public void onServiceDisconnected(ComponentName className) {
			Logger.d(TAG, "Отключились от сервиса: " + className.toString());

			isServiceBound = false;
		}
	};

	public FetchCurrenciesUseCase(ICurrencyRepository currencyRepository, SyncCompletedListener completedListener) {
		this.currencyRepository = currencyRepository;
		this.completedListener = completedListener;
	}

	public interface SyncCompletedListener {
		void onComplete();
		void error(String message);
	}

	/**
	 * Получает список валют из БД.
	 * Вызывается после синхронизации списка валют из сети.
	 * @return список валют
	 */
	protected List<Currency> fetchCurrencies() {
		return currencyRepository.getList();
	}

	/**
	 * Стартует сервис для загрузки списка валют из сети
	 * @param context контекст
	 */
	public void startService(Context context) {
		Intent serviceIntent = new Intent(context, CurrencySyncService.class);
		context.bindService(serviceIntent, serviceConnection, Context.BIND_AUTO_CREATE);
	}

	/**
	 * Отключаемся от сервиса
	 * @param context контекст
	 */
	public void stopService(Context context) {
		context.unbindService(serviceConnection);
		isServiceBound = false;
	}
}
