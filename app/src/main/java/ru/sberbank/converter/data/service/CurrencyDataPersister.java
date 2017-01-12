package ru.sberbank.converter.data.service;

import android.support.annotation.NonNull;
import android.util.Log;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import ru.sberbank.converter.data.db.entity.Currency;
import ru.sberbank.converter.data.network.model.ValCurs;
import ru.sberbank.converter.data.network.model.Valute;
import ru.sberbank.converter.data.repository.ICurrencyRepository;

/**
 * Занимается сохранением списка полученных валют с сайта
 *
 * @author Lord (Kuleshov M.V.)
 * @since 12.01.17
 */
class CurrencyDataPersister {

	private static final String TAG = "CurrencyDataPersister";

	private ICurrencyRepository currencyRepository;

	CurrencyDataPersister(ICurrencyRepository currencyRepository) {
		this.currencyRepository = currencyRepository;
	}

	/**
	 * Сохраняет данные валют в БД
	 * @param valCurs список валют
	 */
	void store(@NonNull ValCurs valCurs) {
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
			// логируем
			Log.d(TAG, "Валюты: " + currencies.toString());
			currencyRepository.storeList(currencies);
		}
	}

	/**
	 * Парсит строку и преобразовывает ее в вещественное число
	 *
	 * @param s строка
	 * @param defValue значение по-умолчанию для вещественного числа (если не удалось преобразовать)
	 * @return вещественное число
	 */
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
