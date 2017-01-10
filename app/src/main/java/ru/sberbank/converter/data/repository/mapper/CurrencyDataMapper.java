package ru.sberbank.converter.data.repository.mapper;

import android.content.ContentValues;

import ru.sberbank.converter.data.db.entity.Currency;

/**
 * Класс-маппер для преобразования объекта валюты в ContentValues для сохранения в БД
 *
 * @author Lord (Kuleshov M.V.)
 * @since 05.01.17
 */
public class CurrencyDataMapper {
	public ContentValues transform(Currency item) {
		ContentValues cv = new ContentValues();
		cv.put(Currency.COLUMN_ID, item.id);
		cv.put(Currency.COLUMN_CHAR_CODE, item.charCode);
		cv.put(Currency.COLUMN_NUM_CODE, item.numCode);
		cv.put(Currency.COLUMN_NOMINAL, item.nominal);
		cv.put(Currency.COLUMN_NAME, item.name);
		cv.put(Currency.COLUMN_VALUE, item.value);

		return cv;
	}
}
