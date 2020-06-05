package com.wiser.livechatmessagelayout.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wiser.livechatmessagelayout.ui.adapter.holder.BaseHolder;

import java.util.List;

/**
 * @author Wiser
 * @param <T>
 *            数据
 * @param <V>
 *            Holder
 *
 *            base adapter
 */
@SuppressWarnings("unchecked")
public abstract class BaseAdapter<T, V extends BaseHolder> extends RecyclerView.Adapter<V> {

	private LayoutInflater mInflater;

	private Context context;

	private List<T> mItems;

	public BaseAdapter(Context context) {
		this.context = context;
		mInflater = LayoutInflater.from(context);
	}

	public abstract V newViewHolder(ViewGroup viewGroup, int type);

	public void setItems(List<T> mItems) {
		this.mItems = mItems;
		notifyDataSetChanged();
	}

	public List<T> getItems() {
		return mItems;
	}

	public T getItem(int position) {
		if (mItems == null) return null;
		return mItems.get(position);
	}

	public void addItem(int position, T t) {
		if (t == null || getItems() == null || position < 0 || position > getItems().size()) {
			return;
		}
		mItems.add(position, t);
		notifyItemInserted(position);
	}

	public void addList(int position, List<T> list) {
		if (list == null || list.size() < 1 || getItems() == null || position < 0 || position > getItems().size()) {
			return;
		}
		mItems.addAll(position, list);
		notifyItemRangeInserted(position, list.size());
	}

	public void addList(List<T> list) {
		if (list == null || list.size() < 1 || getItems() == null) {
			return;
		}
		int position = getItemCount();
		mItems.addAll(list);
		notifyItemRangeInserted(position, list.size());
	}

	public void updateList(int position, T t) {
		getItems().set(position, t);
		notifyItemChanged(position);
	}

	public void delete(int position) {
		if (getItems() == null || position < 0 || getItems().size() < position) {
			return;
		}
		mItems.remove(position);
		notifyItemRemoved(position);
	}

	public void delete(List<T> list) {
		if (list == null || list.size() < 1 || getItems() == null) {
			return;
		}
		int position = getItemCount();
		mItems.removeAll(list);
		notifyItemRangeRemoved(position, list.size());
	}

	public void delete(int position, List<T> list) {
		if (list == null || list.size() < 1 || getItems() == null) {
			return;
		}
		mItems.removeAll(list);
		notifyItemRangeRemoved(position, list.size());
	}

	public void clear() {
		mItems.clear();
		notifyDataSetChanged();
	}

	public Context getContext() {
		return context;
	}

	public View inflate(@LayoutRes int layoutId, ViewGroup viewGroup) {
		return mInflater.inflate(layoutId, viewGroup, false);
	}

	@NonNull @Override
    public V onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
		V v = newViewHolder(viewGroup, i);
		v.setContext(context);
		v.setAdapter(BaseAdapter.this);
		return v;
	}

	@Override
    public void onBindViewHolder(@NonNull V v, int i) {
		v.bindData(getItem(i), i);
	}

	@Override
    public int getItemCount() {
		return mItems == null ? 0 : mItems.size();
	}

}
