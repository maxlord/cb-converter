package ru.sberbank.converter.net;

import java.util.Map;

/**
 * Интерфейс для работы по протоколу http
 *
 * @author Lord (Kuleshov M.V.)
 * @since 26.12.16
 */
public interface IHttpClient {
	/**
	 * Получает содержимое веб-страницы по URL-у
	 *
	 * @param url УРЛ для загрузки
	 * @param args GET-параметры УРЛа
	 * @return контент страницы
	 */
	String get(String url, Map<String, Object> args);
}
