package ru.sberbank.converter.presenter;

import android.support.annotation.NonNull;

import ru.sberbank.converter.data.interactor.GetValutesUseCase;
import ru.sberbank.converter.view.SplashDataView;

/**
 * Презентер для начального экрана (Splash)
 *
 * @author Lord (Kuleshov M.V.)
 * @since 22.12.16
 */
public class SplashPresenter implements Presenter {
	private SplashDataView viewSplash;
	private GetValutesUseCase getValutesUseCase;

	public SplashPresenter(@NonNull GetValutesUseCase getValutesUseCase) {
		this.getValutesUseCase = getValutesUseCase;
	}

	public void setView(@NonNull SplashDataView view) {
		viewSplash = view;
	}

	public void startSyncService() {
		viewSplash.showLoading();
		getValutesUseCase.startService(viewSplash.context());
	}

	@Override
	public void resume() {
		getValutesUseCase.resume(viewSplash.context());
	}

	@Override
	public void pause() {
		getValutesUseCase.pause(viewSplash.context());
	}

	@Override
	public void destroy() {

	}
}
