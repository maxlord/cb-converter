package ru.sberbank.converter.presenter;

/**
 * Базовый класс презентера
 *
 * @author Lord (Kuleshov M.V.)
 * @since 22.12.16
 */
public interface Presenter {
	/**
	 * Method that control the lifecycle of the view. It should be called in the view's
	 * (Activity or Fragment) onResume() method.
	 */
	void resume();

	/**
	 * Method that control the lifecycle of the view. It should be called in the view's
	 * (Activity or Fragment) onPause() method.
	 */
	void pause();

	/**
	 * Method that control the lifecycle of the view. It should be called in the view's
	 * (Activity or Fragment) onDestroy() method.
	 */
	void destroy();
}
