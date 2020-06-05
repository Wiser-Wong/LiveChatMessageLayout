package com.wiser.livechatmessagelayout.utils;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * @author Wiser
 * @version 版本
 */
public class InputHelper {

	private InputMethodManager manager;

	private static InputHelper input;

	public static InputHelper getInstance(Context context){
		if (input == null){
			synchronized (InputHelper.class){
				if (input == null) input = new InputHelper(context);
			}
		}
		return input;
	}

	public InputHelper(Context context) {
		if (manager == null) manager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
	}

	/**
	 * 隐藏软键盘
	 */
	public void hideSoftInput(View view) {
		if (view == null) return;
		manager.hideSoftInputFromWindow(view.getWindowToken(), 0);
	}

	/**
	 * 显示软键盘
	 */
	public void showSoftInput(EditText editText) {
		if (editText == null) return;
		manager.showSoftInput(editText, InputMethodManager.SHOW_FORCED);
	}
}
