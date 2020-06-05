package com.wiser.livechatmessagelayout.utils;

import android.content.Context;
import android.util.DisplayMetrics;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author wangxy
 *
 *         控制smoothScrollToPosition 滚动时间
 */
public class ChatSmoothLayoutManager extends LinearLayoutManager {

	public ChatSmoothLayoutManager(Context context) {
		super(context);
	}

	@Override
    public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, final int position) {

		LinearSmoothScroller smoothScroller = new LinearSmoothScroller(recyclerView.getContext()) {

			// 返回：滑过1px时经历的时间(ms)。
			@Override
            protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
				return 480f / displayMetrics.densityDpi;
			}
		};
		smoothScroller.setTargetPosition(position);
		startSmoothScroll(smoothScroller);
	}
}