package ru.sberbank.converter.presenter;

/**
 * Базовый класс презентера
 *
 * @author Lord (Kuleshov M.V.)
 * @since 22.12.16
 */
public interface Presenter {

	/**
	 * Метод для контроля жизненного цикла вью.
	 * Должен вызываться, в методе onResume() (Activity или Fragment)
	 */
	void resume();

	/**
	 * Метод для контроля жизненного цикла вью.
	 * Должен вызываться, в методе onPause() (Activity или Fragment)
	 */
	void pause();

	/**
	 * Метод для контроля жизненного цикла вью.
	 * Должен вызываться, в методе onDestroy() (Activity или Fragment)
	 */
	void destroy();

}
