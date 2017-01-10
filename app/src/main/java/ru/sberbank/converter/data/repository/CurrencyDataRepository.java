package ru.sberbank.converter.data.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import ru.sberbank.converter.data.db.DatabaseOpenHelper;
import ru.sberbank.converter.data.db.entity.Currency;
import ru.sberbank.converter.data.repository.mapper.CurrencyDataMapper;

/**
 * Репозиторий для управления данными о валютах в БД
 *
 * @author Lord (Kuleshov M.V.)
 * @since 29.12.16
 */
public class CurrencyDataRepository implements ICurrencyRepository {
	private static final String TAG = "CurrencyDataRepository";

	private Context context;
	private DatabaseOpenHelper helper;
//	private SQLiteDatabase db;

	public CurrencyDataRepository(Context context) {
		this.context = context;
		this.helper = new DatabaseOpenHelper(context);
	}

	private SQLiteDatabase getDatabase() {
		return helper.getReadableDatabase();
	}

	/**
	 * Получает список валют
	 * @return
	 */
	@Override
	public List<Currency> getList() {
		List<Currency> items = new ArrayList<>();
		Cursor c = null;
		SQLiteDatabase db = getDatabase();
		try {
			c = db.query(DatabaseOpenHelper.CURRENCY_TABLE,
					null, null,
					null, null, null, Currency.COLUMN_CHAR_CODE + " ASC");

			if (c.getCount() > 0) {
				int indexColumnId = c.getColumnIndexOrThrow(Currency.COLUMN_ID);
				int indexColumnCharCode = c.getColumnIndexOrThrow(Currency.COLUMN_CHAR_CODE);
				int indexColumnNumCode = c.getColumnIndexOrThrow(Currency.COLUMN_NUM_CODE);
				int indexColumnNominal = c.getColumnIndexOrThrow(Currency.COLUMN_NOMINAL);
				int indexColumnName = c.getColumnIndexOrThrow(Currency.COLUMN_NAME);
				int indexColumnValue = c.getColumnIndexOrThrow(Currency.COLUMN_VALUE);

				c.moveToFirst();
				do {
					Currency item = new Currency();
					item.id = c.getString(indexColumnId);
					item.charCode = c.getString(indexColumnCharCode);
					item.numCode = c.getString(indexColumnNumCode);
					item.nominal = c.getInt(indexColumnNominal);
					item.name = c.getString(indexColumnName);
					item.value = c.getDouble(indexColumnValue);

					items.add(item);
				} while (c.moveToNext());
			}

			return items;
		} catch (SQLException e) {
			Log.e(TAG, "Ошибка при получении списка валют из БД", e);
		} finally {
			if (c != null) {
				c.close();
			}
		}

		return null;
	}

	/**
	 * Выполняет поиск валюты по коду
	 * @param charCode код валюты, например: USD
	 * @return
	 */
	@Override
	public Currency getByCharCode(String charCode) {
		throw new RuntimeException("Not implemented");
	}

	private void storeItemInDatabase(SQLiteDatabase db, Currency item) {
		CurrencyDataMapper vdm = new CurrencyDataMapper();
		ContentValues cv = vdm.transform(item);

		try {
			db.insertOrThrow(DatabaseOpenHelper.CURRENCY_TABLE, null, cv);
		} catch (SQLException e) {
			Log.e(TAG, "Ошибка при вставке нового элемента", e);
			// Возникла ошибка при вставке, значит делаем обновление
			db.update(DatabaseOpenHelper.CURRENCY_TABLE, cv, Currency.COLUMN_ID + " = ?", new String[] { String.valueOf(item.id) });
		}
	}

	/**
	 * Сохраняет объект валюты в БД
	 * @param item объект валюты
	 */
	@Override
	public void store(Currency item) {
		SQLiteDatabase db = getDatabase();

		storeItemInDatabase(db, item);

		db.setTransactionSuccessful();
		db.endTransaction();
		db.close();
	}

	/**
	 * Сохраняет список валют в БД
	 * @param currencies список валют
	 */
	@Override
	public void storeList(List<Currency> currencies) {
		SQLiteDatabase db = getDatabase();

		db.beginTransaction();
		try {
			if (currencies != null && !currencies.isEmpty()) {
				for (Currency item : currencies) {
					storeItemInDatabase(db, item);
				}
			}
			db.setTransactionSuccessful();
		} catch (Exception e) {
			Log.e(TAG, "Ошибка при сохранении списка валют в БД");
		} finally {
			db.endTransaction();
			db.close();
		}
	}
}
