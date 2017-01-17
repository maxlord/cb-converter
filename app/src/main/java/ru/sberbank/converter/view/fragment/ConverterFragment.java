package ru.sberbank.converter.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Locale;

import ru.sberbank.converter.R;
import ru.sberbank.converter.data.db.DatabaseOpenHelper;
import ru.sberbank.converter.data.db.entity.Currency;
import ru.sberbank.converter.data.interactor.ConverterUseCase;
import ru.sberbank.converter.data.repository.CurrencyDataRepository;
import ru.sberbank.converter.presenter.ConverterPresenter;
import ru.sberbank.converter.util.Logger;
import ru.sberbank.converter.view.ConverterDataView;
import ru.sberbank.converter.view.ViewFinder;

/**
 * Фрагмент для экрана "Конвертер"
 *
 * @author Lord (Kuleshov M.V.)
 * @since 22.12.16
 */
public class ConverterFragment extends BaseFragment implements ConverterDataView {
	private static final String TAG = "ConverterFragment";

	EditText amount;
	Spinner currentCurrency;
	Spinner convertCurrency;
	Button convertButton;
	TextView amountResult;

	private ConverterPresenter presenter;

	public static ConverterFragment newInstance() {
		Bundle args = new Bundle();

		ConverterFragment fragment = new ConverterFragment();
		fragment.setArguments(args);

		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		presenter = new ConverterPresenter(new ConverterUseCase(new CurrencyDataRepository(new DatabaseOpenHelper(context()))));
		presenter.setView(this);
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_converter, container, false);
	}

	@Override
	public void onViewCreated(View v, Bundle savedInstanceState) {
		super.onViewCreated(v, savedInstanceState);

		amount = ViewFinder.findById(v, R.id.amount);
		currentCurrency = ViewFinder.findById(v, R.id.current_currency_spinner);
		convertCurrency = ViewFinder.findById(v, R.id.convert_currency_spinner);
		convertButton = ViewFinder.findById(v, R.id.convert_button);
		amountResult = ViewFinder.findById(v, R.id.amount_result);

		convertButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				performConvert();
			}
		});

		initializeSpinners();
	}

	/**
	 * Инициализирует выпадающие списки
	 */
	private void initializeSpinners() {
		ArrayAdapter<Currency> fromCurrencyAdapter = new ArrayAdapter<>(context(), android.R.layout.simple_list_item_1,
				presenter.getCurrencyItems());
		currentCurrency.setAdapter(fromCurrencyAdapter);

		ArrayAdapter<Currency> toCurrencyAdapter = new ArrayAdapter<>(context(), android.R.layout.simple_list_item_1,
				presenter.getCurrencyItems());
		convertCurrency.setAdapter(toCurrencyAdapter);
	}

	@Override
	public void performConvert() {
		amountResult.setVisibility(View.GONE);
		presenter.performConvert(getFromCurrency(), getToCurrency(), getAmount());
	}

	@NonNull
	public Currency getFromCurrency() {
		return (Currency) currentCurrency.getSelectedItem();
	}

	@NonNull
	public Currency getToCurrency() {
		return (Currency) convertCurrency.getSelectedItem();
	}

	public double getAmount() {
		try {
			return Double.parseDouble(amount.getText().toString());
		} catch (NumberFormatException e) {
			Logger.e(TAG, "Ошибка преобразования строки в число");
		}

		return 0;
	}

	@Override
	public void displayResult(double result) {
		amountResult.setVisibility(View.VISIBLE);

		amountResult.setText(String.format(Locale.getDefault(), "%.2f", result));
	}

	@Override
	public void displayError(String message) {
		new AlertDialog.Builder(context())
				.setTitle(R.string.common_error)
				.setMessage(message)
				.setPositiveButton(R.string.common_ok, null)
				.show();
	}

	@Override
	public Context context() {
		return getActivity();
	}

	@Override
	public void onStart() {
		super.onStart();

		presenter.start();
	}

	@Override
	public void onResume() {
		super.onResume();

		presenter.resume();
	}

	@Override
	public void onPause() {
		super.onPause();

		presenter.pause();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();

		presenter.destroy();
	}
}
