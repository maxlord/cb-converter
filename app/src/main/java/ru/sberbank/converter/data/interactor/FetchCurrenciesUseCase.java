package ru.sberbank.converter.data.interactor;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;

import java.util.List;

import ru.sberbank.converter.data.db.entity.Currency;
import ru.sberbank.converter.data.repository.ICurrencyRepository;
import ru.sberbank.converter.data.service.CurrencySyncService;

/**
 * Класс бизнес-логики
 * Получает список валют из сети и сохраняет его в БД
 *
 * @author Lord (Kuleshov M.V.)
 * @since 29.12.16
 */
public class FetchCurrenciesUseCase {

	public interface SyncCompletedListener {
		void onComplete();
	}

	private final ICurrencyRepository currencyRepository;

	private CurrencySyncBroadcastReceiver receiver;
	private IntentFilter intentFilter;
	private SyncCompletedListener completedListener;

	public void resume(Context context) {
		LocalBroadcastManager.getInstance(context).registerReceiver(receiver, intentFilter);
	}

	public void pause(Context context) {
		LocalBroadcastManager.getInstance(context).unregisterReceiver(receiver);
	}

	/**
	 * Ресивер, сообщающий UI об окончании загрузки валют из сети
	 */
	private class CurrencySyncBroadcastReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			if (completedListener != null) {
				completedListener.onComplete();
			}
		}
	}

	public FetchCurrenciesUseCase(ICurrencyRepository currencyRepository, SyncCompletedListener completedListener) {
		this.currencyRepository = currencyRepository;
		this.completedListener = completedListener;

		intentFilter = new IntentFilter(CurrencySyncService.BROADCAST_ACTION);
		receiver = new CurrencySyncBroadcastReceiver();
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
		context.startService(serviceIntent);
	}

}
