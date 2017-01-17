package ru.sberbank.converter.presenter;

import android.support.annotation.NonNull;

import ru.sberbank.converter.data.interactor.FetchCurrenciesUseCase;
import ru.sberbank.converter.view.SplashDataView;

/**
 * Презентер для начального экрана (Splash)
 *
 * @author Lord (Kuleshov M.V.)
 * @since 22.12.16
 */
public class SplashPresenter implements Presenter {

	private SplashDataView viewSplash;
	private FetchCurrenciesUseCase interactor;

	public SplashPresenter(@NonNull FetchCurrenciesUseCase interactor) {
		this.interactor = interactor;
	}

	public void setView(@NonNull SplashDataView view) {
		viewSplash = view;
	}

	public void startSyncService() {
		viewSplash.showLoading();
	}

	@Override
	public void start() {
		interactor.startService(viewSplash.context());
	}

	@Override
	public void resume() {

	}

	@Override
	public void pause() {

	}

	@Override
	public void destroy() {
		interactor.stopService(viewSplash.context());
	}

}
