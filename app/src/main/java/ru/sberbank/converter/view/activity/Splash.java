package ru.sberbank.converter.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import ru.sberbank.converter.R;
import ru.sberbank.converter.data.db.DatabaseOpenHelper;
import ru.sberbank.converter.data.interactor.FetchCurrenciesUseCase;
import ru.sberbank.converter.data.repository.CurrencyDataRepository;
import ru.sberbank.converter.presenter.SplashPresenter;
import ru.sberbank.converter.view.SplashDataView;
import ru.sberbank.converter.view.ViewFinder;

/**
 * Начальный экран
 *
 * @author Lord (Kuleshov M.V.)
 * @since 22.12.16
 */
public class Splash extends AppCompatActivity implements SplashDataView {

	ProgressBar progress;

	private SplashPresenter presenter;

	@Override
	public void showLoading() {
		progress.setVisibility(View.VISIBLE);
	}

	@Override
	public void hideLoading() {
		progress.setVisibility(View.GONE);
	}

	@Override
	public void navigateConverterActivity() {
		Intent intent = new Intent(this, Converter.class);
		startActivity(intent);
		finish();
	}

	@Override
	public void showError(String message) {
		Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
	}

	@Override
	public Context context() {
		return this;
	}

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_splash);

		initializeViews();

		FetchCurrenciesUseCase fetchCurrenciesUseCase = new FetchCurrenciesUseCase(new CurrencyDataRepository(context(), new DatabaseOpenHelper(context())), new FetchCurrenciesUseCase.SyncCompletedListener() {
			@Override
			public void onComplete() {
				hideLoading();
				navigateConverterActivity();
			}
		});
		presenter = new SplashPresenter(fetchCurrenciesUseCase);
		presenter.setView(this);
		presenter.startSyncService();
	}

	private void initializeViews() {
		progress = ViewFinder.findById(this, R.id.progress);
	}

	@Override
	protected void onPause() {
		super.onPause();

		presenter.pause();
	}

	@Override
	protected void onResume() {
		super.onResume();

		presenter.resume();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		presenter.destroy();
	}
}
