package ru.sberbank.converter.data.service;

/**
 * @author Lord (Kuleshov M.V.)
 * @since 17.01.17
 */
public class DownloadException extends Throwable {
	public DownloadException(String errorMessage) {
		super(errorMessage);
	}
}
