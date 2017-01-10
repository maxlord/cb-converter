package ru.sberbank.converter.view;

import android.content.Context;

/**
 * Вью для начального экрана (Splash)
 *
 * @author Lord (Kuleshov M.V.)
 * @since 26.12.16
 */
public interface SplashDataView {
	void showLoading();

	void hideLoading();

	void navigateConverterActivity();

	void showError(String message);

	Context context();
}
