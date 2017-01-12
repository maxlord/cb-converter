package ru.sberbank.converter.data.service;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import org.simpleframework.xml.core.Persister;

import java.io.Reader;
import java.io.StringReader;

import ru.sberbank.converter.data.network.model.ValCurs;


/**
 * Занимается разобром xml с валютами и преобразованием его в объект
 *
 * @author Lord (Kuleshov M.V.)
 * @since 12.01.17
 */
class CurrencyXmlParser {

	private static final String TAG = "CurrencyXmlParser";

	private String xml;

	CurrencyXmlParser(String xml) {
		this.xml = xml;
	}

	@Nullable
	ValCurs parse() {
		if (!TextUtils.isEmpty(xml)) {
			Reader reader = new StringReader(xml);
			Persister serializer = new Persister();
			try {
				ValCurs valCurs = serializer.read(ValCurs.class, reader, false);
				Log.d(TAG, "Валюты: " + valCurs.valutes.size());
				return valCurs;
			} catch (Exception e) {
				Log.e(TAG, e.getMessage());
			}
		}

		return null;
	}
}
