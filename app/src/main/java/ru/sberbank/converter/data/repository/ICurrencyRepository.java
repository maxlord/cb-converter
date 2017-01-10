package ru.sberbank.converter.data.repository;

import java.util.List;

import ru.sberbank.converter.data.db.entity.Currency;

/**
 * Интерфейс репозитория с данными о валютах
 *
 * @author Lord (Kuleshov M.V.)
 * @since 29.12.16
 */
public interface ICurrencyRepository {
	/**
	 * Получает список валют
	 * @return
	 */
	List<Currency> getList();

	/**
	 * Выполняет поиск валюты по коду
	 * @param charCode код валюты, например: USD
	 * @return
	 */
	Currency getByCharCode(String charCode);

	/**
	 * Сохраняет объект валюты
	 * @param item объект валюты
	 */
	void store(Currency item);

	/**
	 * Сохраняет список валют
	 * @param currencies список валют
	 */
	void storeList(List<Currency> currencies);
}
