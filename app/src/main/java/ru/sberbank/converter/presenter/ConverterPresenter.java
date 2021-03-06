package ru.sberbank.converter.presenter;

import android.support.annotation.NonNull;

import java.util.List;

import ru.sberbank.converter.data.db.entity.Currency;
import ru.sberbank.converter.data.interactor.ConvertException;
import ru.sberbank.converter.data.interactor.ConverterUseCase;
import ru.sberbank.converter.util.Logger;
import ru.sberbank.converter.view.ConverterDataView;

/**
 * Презентер для экрана "Конвертер"
 *
 * @author Lord (Kuleshov M.V.)
 * @since 10.01.17
 */
public class ConverterPresenter implements Presenter {
	private static final String TAG = "ConverterPresenter";


	private ConverterDataView view;
	private ConverterUseCase interactor;

	public ConverterPresenter(@NonNull ConverterUseCase useCase) {
		this.interactor = useCase;
	}

	public void setView(@NonNull ConverterDataView view) {
		this.view = view;
	}

	/**
	 * Выполняет конвертирование значения
	 */
	public void performConvert(@NonNull Currency fromCurrency, @NonNull Currency toCurrency, double value) {
		try {
			view.displayResult(interactor.performConvert(fromCurrency, toCurrency, value));
		} catch (ConvertException e) {
			Logger.e(TAG, "Ошибка конвертирования валюты", e);

			view.displayError(e.getMessage());
		}
	}

	@Override
	public void start() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void pause() {

	}

	@Override
	public void destroy() {

	}

	/**
	 * Получает список валют
	 * @return список валют
	 */
	public List<Currency> getCurrencyItems() {
		return interactor.getCurrencies();
	}

}
