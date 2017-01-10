package ru.sberbank.converter.presenter;

import android.support.annotation.NonNull;

import java.util.List;

import ru.sberbank.converter.data.db.entity.Currency;
import ru.sberbank.converter.data.interactor.ConverterUseCase;
import ru.sberbank.converter.view.ConverterDataView;

/**
 * Презентер для экрана "Конвертер"
 *
 * @author Lord (Kuleshov M.V.)
 * @since 10.01.17
 */
public class ConverterPresenter implements Presenter {
	private ConverterDataView view;
	private ConverterUseCase converterUseCase;

	public ConverterPresenter(@NonNull ConverterUseCase useCase) {
		this.converterUseCase = useCase;
	}

	public void setView(@NonNull ConverterDataView view) {
		this.view = view;
	}

	/**
	 * Выполняет конвертирование значения
	 */
	public void performConvert() {
		try {
			view.displayResult(converterUseCase.performConvert(view.getFromCurrency(), view.getToCurrency(), view.getAmount()));
		} catch (Exception e) {
			view.displayError(e.getMessage());
		}
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
	 * @return
	 */
	public List<Currency> getCurrencyItems() {
		return converterUseCase.getCurrencies();
	}
}
