package ru.sberbank.converter.data.interactor;

import org.junit.Assert;
import org.junit.Test;

import ru.sberbank.converter.data.db.entity.Currency;
import ru.sberbank.converter.data.repository.CurrencyMockRepository;

/**
 * @author Lord (Kuleshov M.V.)
 * @since 10.01.17
 */
public class ConverterUseCaseTest {
	@Test
	public void getCurrencies() throws Exception {

	}

	@Test
	public void fetchData() throws Exception {

	}

	@Test
	public void performConvert() throws Exception {
		ConverterUseCase useCase = new ConverterUseCase(new CurrencyMockRepository());

		Currency fromCurrency = new Currency();
		fromCurrency.id = "R01235";
		fromCurrency.numCode = "840";
		fromCurrency.charCode = "USD";
		fromCurrency.nominal = 1;
		fromCurrency.name = "Доллар США";
		fromCurrency.value = 60.9084;

		Currency toCurrency = new Currency();
		toCurrency.id = "R01239";
		toCurrency.charCode = "EUR";
		toCurrency.numCode = "978";
		toCurrency.nominal = 1;
		toCurrency.name = "Евро";
		toCurrency.value = 63.7285;

		double expected = 14.17;

		double result = useCase.performConvert(fromCurrency, toCurrency, 15);

		Assert.assertEquals(expected, result, .3);
	}

	@Test
	public void performConvertNominalIsNull() throws Exception {
		ConverterUseCase useCase = new ConverterUseCase(new CurrencyMockRepository());

		Currency fromCurrency = new Currency();
		fromCurrency.id = "R01235";
		fromCurrency.numCode = "840";
		fromCurrency.charCode = "USD";
		fromCurrency.nominal = 0;
		fromCurrency.name = "Доллар США";
		fromCurrency.value = 60.9084;

		Currency toCurrency = new Currency();
		toCurrency.id = "R01239";
		toCurrency.charCode = "EUR";
		toCurrency.numCode = "978";
		toCurrency.nominal = 0;
		toCurrency.name = "Евро";
		toCurrency.value = 63.7285;

		double expected = 14.17;

		double result = useCase.performConvert(fromCurrency, toCurrency, 15);

		Assert.assertEquals("Необработано деление на 0", false, Double.isNaN(result));

		Assert.assertEquals(expected, result, .3);
	}
}
