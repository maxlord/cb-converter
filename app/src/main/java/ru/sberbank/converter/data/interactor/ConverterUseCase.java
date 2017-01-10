package ru.sberbank.converter.data.interactor;

import android.support.annotation.NonNull;

import java.util.List;

import ru.sberbank.converter.data.db.entity.Currency;
import ru.sberbank.converter.data.repository.ICurrencyRepository;

/**
 * Класс бизнес-логики для экрана "Конвертер"
 *
 * @author Lord (Kuleshov M.V.)
 * @since 10.01.17
 */
public class ConverterUseCase extends UseCase<Double> {
	private Currency fromCurrency;
	private Currency toCurrency;
	private Double fromValue;

	private final ICurrencyRepository currencyRepository;

	public ConverterUseCase(ICurrencyRepository currencyRepository) {
		this.currencyRepository = currencyRepository;
	}

	/**
	 * Получает список валют из БД
	 * @return
	 */
	public List<Currency> getCurrencies() {
		return currencyRepository.getList();
	}

	/**
	 * Выполняет конвертирование значения из одной валюты в другую
	 *
	 * @param fromCurrency исходная валюта
	 * @param toCurrency конечная валюта
	 * @param value значение
	 *
	 * @return
	 */
	public double performConvert(@NonNull Currency fromCurrency, @NonNull Currency toCurrency, double value) {
		this.fromCurrency = fromCurrency;
		this.toCurrency = toCurrency;
		this.fromValue = value;

		execute();

		return getData();
	}

	/**
	 * Выполняет основное действие класса: расчет конвертированного значения валюты
	 *
	 * @return
	 */
	@Override
	protected Double fetchData() {
		// Количество рублей в 1 единице валюты
		double fromRur = fromCurrency.value / fromCurrency.nominal;
		// исходное значение в рублях
		double fromValueInRur = fromValue * fromRur;

		// конечное значение в конечной валюте
		double toCurrencyInRur = toCurrency.value / toCurrency.nominal;

		return fromValueInRur / toCurrencyInRur;
	}
}
