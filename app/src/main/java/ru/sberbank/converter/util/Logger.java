package ru.sberbank.converter.util;

import android.util.Log;

/**
 * Логгер
 *
 * По-умолчанию: реализовано стандартное android-логирование
 *
 * @author Lord (Kuleshov M.V.)
 * @since 17.01.17
 */
public class Logger {
	private Logger() {

	}

	/**
	 * Выводит VERBOSE-сообщение в лог.
	 * @param tag Используется для идентификации источника сообщения журнала.
	 *            Это, как правило, определяет класс или активити, где происходит вызов журнала.
	 * @param msg Сообщение, которое нужно залогировать.
	 */
	public static int v(String tag, String msg) {
		return Log.v(tag, msg);
	}

	/**
	 * Выводит VERBOSE-сообщение и логирует exception.
	 * @param tag Используется для идентификации источника сообщения журнала.
	 *            Это, как правило, определяет класс или активити, где происходит вызов журнала.
	 * @param msg Сообщение, которое нужно залогировать.
	 * @param tr Exception, которое нужно залогировать
	 */
	public static int v(String tag, String msg, Throwable tr) {
		return Log.v(tag, msg, tr);
	}

	/**
	 * Выводит DEBUG-сообщение.
	 * @param tag Используется для идентификации источника сообщения журнала.
	 *            Это, как правило, определяет класс или активити, где происходит вызов журнала.
	 * @param msg Сообщение, которое нужно залогировать.
	 */
	public static int d(String tag, String msg) {
		return Log.d(tag, msg);
	}

	/**
	 * Выводит DEBUG-сообщение и логирует exception.
	 * @param tag Используется для идентификации источника сообщения журнала.
	 *            Это, как правило, определяет класс или активити, где происходит вызов журнала.
	 * @param msg Сообщение, которое нужно залогировать.
	 * @param tr Exception, которое нужно залогировать
	 */
	public static int d(String tag, String msg, Throwable tr) {
		return Log.d(tag, msg, tr);
	}

	/**
	 * Выводит INFO-сообщение.
	 * @param tag Используется для идентификации источника сообщения журнала.
	 *            Это, как правило, определяет класс или активити, где происходит вызов журнала.
	 * @param msg Сообщение, которое нужно залогировать.
	 */
	public static int i(String tag, String msg) {
		return Log.i(tag, msg);
	}

	/**
	 * Выводит INFO-сообщение и логирует exception.
	 * @param tag Используется для идентификации источника сообщения журнала.
	 *            Это, как правило, определяет класс или активити, где происходит вызов журнала.
	 * @param msg Сообщение, которое нужно залогировать.
	 * @param tr Exception, которое нужно залогировать
	 */
	public static int i(String tag, String msg, Throwable tr) {
		return Log.i(tag, msg, tr);
	}

	/**
	 * Выводит WARN-сообщение.
	 * @param tag Используется для идентификации источника сообщения журнала.
	 *            Это, как правило, определяет класс или активити, где происходит вызов журнала.
	 * @param msg Сообщение, которое нужно залогировать.
	 */
	public static int w(String tag, String msg) {
		return Log.w(tag, msg);
	}

	/**
	 * Выводит WARN-сообщение и логирует exception.
	 * @param tag Используется для идентификации источника сообщения журнала.
	 *            Это, как правило, определяет класс или активити, где происходит вызов журнала.
	 * @param msg Сообщение, которое нужно залогировать.
	 * @param tr Exception, которое нужно залогировать
	 */
	public static int w(String tag, String msg, Throwable tr) {
		return Log.w(tag, msg, tr);
	}


	/**
     * Выводит WARN-сообщение и логирует exception.
	 * @param tag Используется для идентификации источника сообщения журнала.
	 *            Это, как правило, определяет класс или активити, где происходит вызов журнала.
     * @param tr Exception, которое нужно залогировать
     */
	public static int w(String tag, Throwable tr) {
		return Log.w(tag, tr);
	}

	/**
	 * Выводит ERROR-сообщение.
	 * @param tag Используется для идентификации источника сообщения журнала.
	 *            Это, как правило, определяет класс или активити, где происходит вызов журнала.
	 * @param msg Сообщение, которое нужно залогировать.
	 */
	public static int e(String tag, String msg) {
		return Log.e(tag, msg);
	}

	/**
	 * Выводит ERROR-сообщение и логирует exception.
	 * @param tag Используется для идентификации источника сообщения журнала.
	 *            Это, как правило, определяет класс или активити, где происходит вызов журнала.
	 * @param msg Сообщение, которое нужно залогировать.
	 * @param tr Exception, которое нужно залогировать
	 */
	public static int e(String tag, String msg, Throwable tr) {
		return Log.e(tag, msg, tr);
	}
}
