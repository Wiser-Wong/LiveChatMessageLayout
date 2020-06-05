package com.wiser.livechatmessagelayout.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.IdRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LiveHelper {

	/**
	 * dip转换成px
	 *
	 * @param context
	 * @param dpValue
	 * @return
	 */
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * 获取手机状态栏高度
	 */
	@SuppressLint("PrivateApi") public static int getStatusBarHeight(Context context) {
		Class<?> c;
		Object obj;
		Field field;
		int x, statusBarHeight = 0;
		try {
			c = Class.forName("com.android.internal.R$dimen");
			obj = c.newInstance();
			field = c.getField("status_bar_height");
			x = Integer.parseInt(field.get(obj).toString());
			statusBarHeight = context.getResources().getDimensionPixelSize(x);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return statusBarHeight;
	}

	/**
	 * @param activity
	 *            参数
	 * @param tagName
	 *            参数
	 * @return 返回值
	 */
	public static Fragment findFragment(FragmentActivity activity, String tagName) {
		if (tagName == null || "".equals(tagName)) return null;
		if (activity == null) return null;
		return activity.getSupportFragmentManager().findFragmentByTag(tagName);
	}

	/**
	 * @param srcFragment
	 *            参数
	 * @param tagName
	 *            参数
	 * @return 返回值
	 */
	public static Fragment findFragment(Fragment srcFragment, String tagName) {
		if (tagName == null || "".equals(tagName)) return null;
		if (srcFragment == null) return null;
		return srcFragment.getChildFragmentManager().findFragmentByTag(tagName);
	}

	/**
	 * 获得屏幕的宽度
	 *
	 * @return
	 */
	public static int getScreenWidth(Context context) {
		return context.getResources().getDisplayMetrics().widthPixels;
	}

	/**
	 * 获得屏幕的高度
	 *
	 * @return
	 */
	public static int getScreenHeight(Context context) {
		return context.getResources().getDisplayMetrics().heightPixels;
	}

	/**
	 * 全透状态栏
	 */
	public static void setStatusBarFullTransparent(FragmentActivity activity) {
		if (activity == null) return;
		if (Build.VERSION.SDK_INT >= 21) {// 21表示5.0
			Window window = activity.getWindow();
			window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
			window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
			window.setStatusBarColor(Color.TRANSPARENT);
		} else if (Build.VERSION.SDK_INT >= 19) {// 19表示4.4
			activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			// 虚拟键盘也透明
			// getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
		}
	}

	/**
	 * 获取虚拟按键的高度
	 * 1. 全面屏下
	 * 1.1 开启全面屏开关-返回0
	 * 1.2 关闭全面屏开关-执行非全面屏下处理方式
	 * 2. 非全面屏下
	 * 2.1 没有虚拟键-返回0
	 * 2.1 虚拟键隐藏-返回0
	 * 2.2 虚拟键存在且未隐藏-返回虚拟键实际高度
	 */
	public static int getNavigationBarHeightRoom(Context context) {
		if (navigationGestureEnabled(context)) {
			return 0;
		}
		return getCurrentNavigationBarHeight(((Activity) context));
	}

	/**
	 * 全面屏（是否开启全面屏开关 0 关闭  1 开启）
	 *
	 * @param context
	 * @return
	 */
	public static boolean navigationGestureEnabled(Context context) {
		int val = 0;
		if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR1) {
			val = Settings.Global.getInt(context.getContentResolver(), getDeviceInfo(), 0);
		}
		return val != 0;
	}

	/**
	 * 获取设备信息（目前支持几大主流的全面屏手机，亲测华为、小米、oppo、魅族、vivo都可以）
	 *
	 * @return
	 */
	public static String getDeviceInfo() {
		String brand = Build.BRAND;
		if (TextUtils.isEmpty(brand)) return "navigationbar_is_min";

		if (brand.equalsIgnoreCase("HUAWEI")) {
			return "navigationbar_is_min";
		} else if (brand.equalsIgnoreCase("XIAOMI")) {
			return "force_fsg_nav_bar";
		} else if (brand.equalsIgnoreCase("VIVO")) {
			return "navigation_gesture_on";
		} else if (brand.equalsIgnoreCase("OPPO")) {
			return "navigation_gesture_on";
		} else {
			return "navigationbar_is_min";
		}
	}

	/**
	 * 非全面屏下 虚拟键实际高度(隐藏后高度为0)
	 *
	 * @param activity
	 * @return
	 */
	public static int getCurrentNavigationBarHeight(Activity activity) {
		if (isNavigationBarShown(activity)) {
			return getNavigationBarHeight(activity);
		} else {
			return 0;
		}
	}

	/**
	 * 非全面屏下 虚拟按键是否打开
	 *
	 * @param activity
	 * @return
	 */
	public static boolean isNavigationBarShown(Activity activity) {
		//虚拟键的view,为空或者不可见时是隐藏状态
		View view = activity.findViewById(android.R.id.navigationBarBackground);
		if (view == null) {
			return false;
		}
		int visible = view.getVisibility();
		if (visible == View.GONE || visible == View.INVISIBLE) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 非全面屏下 虚拟键高度(无论是否隐藏)
	 *
	 * @param context
	 * @return
	 */
	public static int getNavigationBarHeight(Context context) {
		int result = 0;
		int resourceId = context.getResources().getIdentifier("navigation_bar_height", "dimen", "android");
		if (resourceId > 0) {
			result = context.getResources().getDimensionPixelSize(resourceId);
		}
		return result;
	}

	/**
	 * 替换子Fragment中的Fragment
	 * @param srcFragment
	 * @param id
	 * @param fragment
	 */
	public static void commitChildReplace(Fragment srcFragment, @IdRes int id, Fragment fragment) {
		if (fragment == null) return;
		if (srcFragment == null) return;
		srcFragment.getChildFragmentManager().beginTransaction().replace(id, fragment, fragment.getClass().getName()).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
				.commitAllowingStateLoss();
	}

	/**
	 * 替换子Fragment中的Fragment
	 * @param id
	 * @param fragment
	 * @param tagName
	 */
	public static void commitReplace(FragmentActivity activity, @IdRes int id, Fragment fragment, String tagName) {
		if (activity == null) return;
		if (fragment == null) return;
		activity.getSupportFragmentManager().beginTransaction().replace(id, fragment, tagName).commit();
	}

	// 移除大于最大条时最开始的数据
	public static <T> void checkRemoveList(List<T> list , int size) {
		if (size <= 0) return;
		for (int i = 0; i < size; i++) {
			if (list != null) {
				list.remove(i);
				i--;
				size--;
			}
		}
	}

	/**
	 * 处理超过8个字符显示省略号
	 * @param content
	 * @return
	 */
	public static String handleEllipsis(String content){
		if (TextUtils.isEmpty(content)) return "";
		if (content.length() > 8){
			return content.substring(0,8) + "...";
		}
		return content;
	}

	/**
	 * 获取价格 小数点末尾为0去掉
	 * @param d
	 * @return
	 */
	public static String getPriceValue(String d){
		if (isDouble(d)){
			DecimalFormat df = new DecimalFormat("##.##");
			return df.format(returnDouble(d));
		}else {
			return "";
		}
	}

	/**
	 * 是否是Double类型
	 *
	 * @param str
	 * @return
	 */
	public static boolean isDouble(String str) {
		if (TextUtils.isEmpty(str)) return false;
		try {
			double ss = Double.parseDouble(str);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	// 返回Double类型
	public static double returnDouble(String str) {
		if (TextUtils.isEmpty(str)) return 0.00;
		double ss;
		String newStr = saveDoubleNumber(str);
		try {
			ss = Double.parseDouble(newStr);
		} catch (Exception e) {
			return 0.00;
		}
		return ss;
	}

	/**
	 * 保存两位小数
	 *
	 * @param str
	 * @return
	 */
	public static String saveDoubleNumber(String str) {
		if (TextUtils.isEmpty(str)) return "0.00";
		double d;
		if (isDouble(str)) {
			d = Double.parseDouble(str);
		} else {
			return "0.00";
		}
		DecimalFormat df = new DecimalFormat("0.00");
		return String.valueOf(df.format(saveDoubleNum(d)));
	}

	// 四舍五入
	public static double saveDoubleNum(double f) {
		BigDecimal b = new BigDecimal(f);
		return b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/**
	 * 将多个空格合成一个空格
	 * @param s
	 * @return
	 */
	public static String trimPattern(String s){
		if (TextUtils.isEmpty(s)) return "";
		Pattern p = Pattern.compile("\\s+");
		Matcher m = p.matcher(s);
		return m.replaceAll(" ");
	}

	/**
	 * 判断字符串是否为数字
	 *
	 * @param str
	 * @return
	 */
	public static boolean isNumber(String str) {
		if (TextUtils.isEmpty(str)) return false;
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		return isNum.matches();
	}

	/**
	 * 是否是Double类型
	 *
	 * @param str
	 * @return
	 */
	public static boolean isLong(String str) {
		if (TextUtils.isEmpty(str)) return false;
		try {
			long ss = Long.parseLong(str);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

}
