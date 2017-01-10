package ru.sberbank.converter.app;

import android.app.Application;

/**
 * Главный класс приложения
 *
 * @author Lord (Kuleshov M.V.)
 * @since 22.12.16
 */
public class ConverterApplication extends Application {
	private static ConverterApplication instance;

	@Override
	public void onCreate() {
		super.onCreate();

		instance = this;
	}

	public static ConverterApplication getInstance() {
		return instance;
	}
}
