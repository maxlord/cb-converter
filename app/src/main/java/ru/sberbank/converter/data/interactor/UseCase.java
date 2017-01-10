package ru.sberbank.converter.data.interactor;

/**
 * Базовый класс бизнес-логики
 *
 * @author Lord (Kuleshov M.V.)
 * @since 29.12.16
 */
public abstract class UseCase<T> {
	protected T data;

	UseCase() {

	}

	public void execute() {
		data = fetchData();
	}

	protected abstract T fetchData();

	public T getData() {
		return data;
	}
}
