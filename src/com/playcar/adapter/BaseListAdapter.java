package com.playcar.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by chengrong on 2015/5/28.
 */
public abstract class BaseListAdapter<T> extends BaseAdapter {
    private List<T> data = new ArrayList<T>();
    private LayoutInflater inflater;
    private int dropDownItemView;
    private int itemView;
    private Context context;

    public BaseListAdapter(Context context) {
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context = context;
    }

    public void add(T object) {
        data.add(object);
        notifyDataSetChanged();
    }

    public void addAll(Collection<T> collection) {
        data.addAll(collection);
        notifyDataSetChanged();
    }

    public void setData(List<T> data) {
        this.data.clear();
        if (data != null) {
            this.data = data;
        }

        notifyDataSetChanged();
    }

    public void removeItem(int index) {
        if (data == null) {
            return;
        }
        data.remove(index);
        notifyDataSetChanged();
    }

    public void clear() {
        data.clear();
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public T getItem(int position) {
        return data.get(position);
    }

    public int getPosition(T object) {
        return data.indexOf(object);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    protected LayoutInflater getInflater() {
        return inflater;
    }

    protected Context getContext() {
        return context;
    }

    public void setDropDownViewResource(int resource) {
        this.dropDownItemView = resource;
    }

    public void setItemViewResource(int resource) {
        this.itemView = resource;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return createViewWithResource(position, convertView, parent, dropDownItemView);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return createViewWithResource(position, convertView, parent, itemView);
    }

    protected abstract View createViewWithResource(int position, View convertView, ViewGroup parent, int resource);

    public List<T> getData() {
        return data;
    }

}

