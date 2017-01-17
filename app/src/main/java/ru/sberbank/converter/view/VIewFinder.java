package ru.sberbank.converter.view;

import android.app.Activity;
import android.app.Dialog;
import android.support.annotation.CheckResult;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.view.View;

/**
 * Линкер для связывания полей класса и ui-контролов layout
 * Заимствовано из известной библиотеки ButterKnife
 *
 * @author Lord (Kuleshov M.V.)
 * @since 22.12.16
 */
public class ViewFinder {
	private ViewFinder() {
	}

	/** Simpler version of {@link View#findViewById(int)} which infers the target type. */
	@SuppressWarnings({ "unchecked", "UnusedDeclaration" }) // Checked by runtime cast. Public API.
	@CheckResult
	public static <T extends View> T findById(@NonNull View view, @IdRes int id) {
		return (T) view.findViewById(id);
	}

	/** Simpler version of {@link Activity#findViewById(int)} which infers the target type. */
	@SuppressWarnings({ "unchecked", "UnusedDeclaration" }) // Checked by runtime cast. Public API.
	@CheckResult
	public static <T extends View> T findById(@NonNull Activity activity, @IdRes int id) {
		return (T) activity.findViewById(id);
	}

	/** Simpler version of {@link Dialog#findViewById(int)} which infers the target type. */
	@SuppressWarnings({ "unchecked", "UnusedDeclaration" }) // Checked by runtime cast. Public API.
	@CheckResult
	public static <T extends View> T findById(@NonNull Dialog dialog, @IdRes int id) {
		return (T) dialog.findViewById(id);
	}
}
