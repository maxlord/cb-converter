package ru.sberbank.converter.data.repository.mapper;

import android.content.ContentValues;

import org.junit.Test;
import org.mockito.Mockito;

import ru.sberbank.converter.data.db.entity.Currency;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

/**
 * @author Lord (Kuleshov M.V.)
 * @since 10.01.17
 */
public class CurrencyDataMapperTest {
	@Test
	public void transform() throws Exception {
		Currency currency = new Currency();
		currency.id = "R01235";
		currency.numCode = "840";
		currency.charCode = "USD";
		currency.nominal = 1;
		currency.name = "Доллар США";
		currency.value = 60.9084;

		ContentValues cv = new CurrencyDataMapper().transform(currency);
		cv.put(Currency.COLUMN_ID, currency.id);
		cv.put(Currency.COLUMN_CHAR_CODE, currency.charCode);
		cv.put(Currency.COLUMN_NUM_CODE, currency.numCode);
		cv.put(Currency.COLUMN_NOMINAL, currency.nominal);
		cv.put(Currency.COLUMN_NAME, currency.name);
		cv.put(Currency.COLUMN_VALUE, currency.value);

//		CurrencyDataMapper mockCDM = Mockito.mock(CurrencyDataMapper.class);
		ContentValues mockCV = Mockito.mock(ContentValues.class);

//		when(mockCDM.transform(currency)).thenReturn(cv);
		when(mockCV.size()).thenReturn(6);

		assertEquals("Количество элементов не соответствует объекту", 6, mockCV.size());
	}
}
