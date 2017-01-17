package ru.sberbank.converter.data.db.entity;

/**
 * Представляет класс валюты в БД
 * Таблица БД
 *
 * @author Lord (Kuleshov M.V.)
 * @since 27.12.16
 */
public class Currency {
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_NUM_CODE = "num_code";
	public static final String COLUMN_CHAR_CODE = "char_code";
	public static final String COLUMN_NOMINAL = "nominal";
	public static final String COLUMN_NAME = "name";
	public static final String COLUMN_VALUE = "value";

	/**
	 * Идентификатор валюты
	 */
	public String id;
	/**
	 * Номерной код
	 */
	public String numCode;
	/**
	 * Символьный код, например: USD
	 */
	public String charCode;
	/**
	 * Номинал валюты, например 10 шведских крон
	 */
	public int nominal;
	/**
	 * Наименование валюты, например: Российский рубль
	 */
	public String name;
	/**
	 * Значение курса
	 */
	public double value;

	@Override
	public String toString() {
		return charCode +
				" (" +
				nominal +
				" " +
				name +
				")";
	}
}
