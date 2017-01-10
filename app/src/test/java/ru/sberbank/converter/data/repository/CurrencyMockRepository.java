package ru.sberbank.converter.data.repository;

import java.util.List;

import ru.sberbank.converter.data.db.entity.Currency;

/**
 * @author Lord (Kuleshov M.V.)
 * @since 10.01.17
 */
public class CurrencyMockRepository implements ICurrencyRepository {
	@Override
	public List<Currency> getList() {
		return null;
	}

	@Override
	public Currency getByCharCode(String charCode) {
		return null;
	}

	@Override
	public void store(Currency item) {

	}

	@Override
	public void storeList(List<Currency> currencies) {

	}
}
