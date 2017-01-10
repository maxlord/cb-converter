package ru.sberbank.converter.net;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

/**
 * Http-клиент на основе HttpUrlConnection
 *
 * @author Lord (Kuleshov M.V.)
 * @since 26.12.16
 */
public class HttpUrlConnectionHttpClient implements IHttpClient {
	private static final String TAG = "HttpClient";

	/**
	 * Получает содержимое документа
	 * @param link ссылка
	 * @param args GET-параметры УРЛа
	 * @return
	 */
	@Override
	public String get(String link, Map<String, Object> args) {
		String fullLink = link;
		if (args != null && !args.isEmpty()) {
			fullLink += "?" + getQuery(args);
		}

		URL url = null;
		HttpURLConnection urlConnection = null;
		try {
			url = new URL(fullLink);

			urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.setConnectTimeout(10 * 1000);

			InputStream in = urlConnection.getInputStream();

			return readStream(in);
		} catch (Exception e) {
			Log.e(TAG, "Ошибка при получении содержимого документа через УРЛ: " + fullLink, e);
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
	 * @return
	 */
	private String readStream(InputStream in) {
		BufferedReader reader = null;
		StringBuffer response = new StringBuffer();
		try {
			reader = new BufferedReader(new InputStreamReader(in, "windows-1251"));
			String line;
			while ((line = reader.readLine()) != null) {
				response.append(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return response.toString();
	}

	/**
	 * Получает query-строку для GET-запроса
	 * @param params мап с набором GET-параметров
	 * @return
	 */
	private String getQuery(Map<String, Object> params) {
		StringBuilder result = new StringBuilder();
		boolean first = true;

		for (Map.Entry<String, Object> pair : params.entrySet()) {
			if (first)
				first = false;
			else
				result.append("&");

			try {
				result.append(URLEncoder.encode(pair.getKey(), "UTF-8"));
				result.append("=");
				result.append(URLEncoder.encode(String.valueOf(pair.getValue()), "UTF-8"));
			} catch (UnsupportedEncodingException e) {
				Log.e(TAG, "Возникла ошибки при кодировании параметра URL");
			}
		}

		return result.toString();
	}
}
