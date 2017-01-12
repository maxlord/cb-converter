package ru.sberbank.converter.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import ru.sberbank.converter.R;
import ru.sberbank.converter.view.fragment.ConverterFragment;

/**
 * Экран "Конвертер"
 *
 * @author Lord (Kuleshov M.V.)
 * @since 22.12.16
 */
public class Converter extends BaseActvity {

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_converter);

		addFragment(R.id.container, ConverterFragment.newInstance());
	}

}
