package ru.sberbank.converter.view;

import android.content.Context;
import android.support.annotation.NonNull;

import ru.sberbank.converter.data.db.entity.Currency;

/**
 * Вью для экрана "Конвертер"
 *
 * @author Lord (Kuleshov M.V.)
 * @since 26.12.16
 */
public interface ConverterDataView {
	/**
	 * Выполнить конвертирование
	 */
	void performConvert();

	/**
	 * Получает выбранную исходную валюту
	 * @return
	 */
	@NonNull Currency getFromCurrency();

	/**
	 * Получает выбранную конечную валюту
	 * @return
	 */
	@NonNull Currency getToCurrency();

	/**
	 * Получает введенное значение валюты
	 * @return
	 */
	double getAmount();

	/**
	 * Отображает результат пользователю
	 * @param result результат
	 */
	void displayResult(double result);

	/**
	 * Отображает ошибку пользователю
	 * @param message сообщение об ошибке
	 */
	void displayError(String message);

	/**
	 * Контекст
	 * @return
	 */
	Context context();
}
