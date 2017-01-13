package ru.sberbank.converter.view;

import android.content.Context;

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
