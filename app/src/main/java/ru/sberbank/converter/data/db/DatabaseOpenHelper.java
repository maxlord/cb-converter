package ru.sberbank.converter.data.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import ru.sberbank.converter.BuildConfig;
import ru.sberbank.converter.data.db.entity.Currency;

/**
 * Помощник для работы с БД
 *
 * @author Lord (Kuleshov M.V.)
 * @since 23.12.16
 */
public class DatabaseOpenHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "main.db";
	public static final String CURRENCY_TABLE = "currency";
	private static final String CURRENCY_TABLE_DDL = "CREATE TABLE currency (" +
			Currency.COLUMN_ID + " VARCHAR(10) PRIMARY KEY, " +
			Currency.COLUMN_NUM_CODE + " VARCHAR(3) NOT NULL, " +
			Currency.COLUMN_CHAR_CODE + " VARCHAR(3) NOT NULL, " +
			Currency.COLUMN_NOMINAL + " INTEGER NOT NULL, " +
			Currency.COLUMN_NAME + " VARCHAR(255) NOT NULL, " +
			Currency.COLUMN_VALUE + " REAL NOT NULL" +
			")";
	private static final String CURRENCY_RUB_INSERT = "INSERT INTO currency (" +
			Currency.COLUMN_ID + ", " +
			Currency.COLUMN_NUM_CODE + ", " +
			Currency.COLUMN_CHAR_CODE + ", " +
			Currency.COLUMN_NOMINAL + ", " +
			Currency.COLUMN_NAME + ", " +
			Currency.COLUMN_VALUE + ") VALUES ('R00000', '000', 'RUB', 1, 'Российский рубль', 1)";

	public DatabaseOpenHelper(Context context) {
		super(context, DATABASE_NAME, null, BuildConfig.DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CURRENCY_TABLE_DDL);
		// Вставляем информацию о российском рубле, потому что веб-сервис не содержит этой информации
		db.execSQL(CURRENCY_RUB_INSERT);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

}
