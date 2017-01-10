package ru.sberbank.converter.view.activity;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

/**
 * Базовый класс для всех активити приложения
 *
 *
 * @author Lord (Kuleshov M.V.)
 * @since 22.12.16
 */
public class BaseActvity extends AppCompatActivity {
	/**
	 * Добавляет {@link Fragment} к разметке активити.
	 *
	 * @param containerViewId Идентификатор контейнера, в который добавится фрагментt.
	 * @param fragment Добавляемый фрагмент.
	 */
	protected void addFragment(int containerViewId, Fragment fragment) {
		final FragmentTransaction fragmentTransaction = this.getFragmentManager().beginTransaction();
		fragmentTransaction.replace(containerViewId, fragment);
		fragmentTransaction.commit();
	}
}
