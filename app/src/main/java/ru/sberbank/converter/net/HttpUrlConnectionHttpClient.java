package ru.sberbank.converter.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

import ru.sberbank.converter.util.Logger;

/**
 * Http-клиент на основе HttpUrlConnection
 *
 * @author Lord (Kuleshov M.V.)
 * @since 26.12.16
 */
public class HttpUrlConnectionHttpClient implements IHttpClient {

	private static final String TAG = "HttpClient";
	private static final String URL_QUERY_SEPARATOR = "?";
	private static final String URL_PARAM_CONCATENATOR = "&";
	private static final String URL_PARAM_SEPARATOR = "=";
	private static final String ENCODING_UTF8 = "utf-8";
	private static final String ENCODING_WINDOWS_1251 = "windows-1251";
	private static final int CONNECTION_TIMEOUT = 10 * 1000;

	/**
	 * Получает содержимое документа
	 * @param link ссылка
	 * @param args GET-параметры УРЛа
	 * @return контент страницы
	 */
	@Override
	public String get(String link, Map<String, Object> args) {
		String fullLink = link;
		if (args != null && !args.isEmpty()) {
			try {
				fullLink += URL_QUERY_SEPARATOR + getQuery(args);
			} catch (UnsupportedEncodingException e) {
				Logger.e(TAG, "Ошибка при кодированию URL-адреса", e);
			}
		}

		HttpURLConnection urlConnection = null;
		try {
			URL url = new URL(fullLink);

			urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.setConnectTimeout(CONNECTION_TIMEOUT);

			InputStream in = urlConnection.getInputStream();

			return readStream(in);
		} catch (IOException e) {
			Logger.e(TAG, "Ошибка при получении содержимого документа через УРЛ: " + fullLink, e);
		} finally {
			if (urlConnection != null) {
				urlConnection.disconnect();
			}
		}

		return null;
	}

	/**
	 * Читает из InputStream данные в строку
	 *
	 * @param in InputStream
	 * @return строка, прочитанная из потока in
	 */
	private String readStream(InputStream in) {
		BufferedReader reader = null;
		StringBuilder response = new StringBuilder();
		try {
			reader = new BufferedReader(new InputStreamReader(in, ENCODING_WINDOWS_1251));
			String line;
			while ((line = reader.readLine()) != null) {
				response.append(line);
			}
		} catch (UnsupportedEncodingException e) {
			Logger.e(TAG, "Ошибка преобразования кодировки текста", e);
		} catch (IOException e) {
			Logger.e(TAG, "Ошибка чтения данных из потока", e);
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					Logger.e(TAG, "Ошибка при закрытии reader", e);
				}
			}
		}

		return response.toString();
	}

	/**
	 * Получает query-строку для GET-запроса
	 * @param params мап с набором GET-параметров
	 *
	 * @return получает строку с параметрами запроса, типа: var1=a&var2=bc
	 */
	private String getQuery(Map<String, Object> params) throws UnsupportedEncodingException {
		StringBuilder result = new StringBuilder();
		boolean first = true;

		for (Map.Entry<String, Object> pair : params.entrySet()) {
			if (first)
				first = false;
			else
				result.append(URL_PARAM_CONCATENATOR);

			result.append(URLEncoder.encode(pair.getKey(), ENCODING_UTF8));
			result.append(URL_PARAM_SEPARATOR);
			result.append(URLEncoder.encode(String.valueOf(pair.getValue()), ENCODING_UTF8));
		}

		return result.toString();
	}

}
