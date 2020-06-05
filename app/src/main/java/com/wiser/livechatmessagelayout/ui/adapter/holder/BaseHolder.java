package com.wiser.livechatmessagelayout.ui.adapter.holder;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wiser.livechatmessagelayout.ui.adapter.BaseAdapter;


/**
 * @author Wiser
 * 
 *         Holder base
 */
public abstract class BaseHolder<T> extends RecyclerView.ViewHolder {

	private BaseAdapter adapter;

	public Context mContext;

	public BaseHolder(@NonNull View itemView) {
		super(itemView);
	}

	public void setContext(Context context) {
		this.mContext = context;
	}

	public Context getContext() {
		return mContext;
	}

	public void setAdapter(BaseAdapter adapter) {
		this.adapter = adapter;
	}

	public BaseAdapter adapter() {
		return adapter;
	}

	public abstract void bindData(T t, int position);

}
