package com.topjet.shipper.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;


public abstract class ArrayBaseAdapter<T>
extends BaseAdapter {
	protected ArrayList<T> mList;
	protected Activity mContext;
	protected ListView mListView;
	
	public ArrayBaseAdapter(Activity context){
		this.mContext = context;
	}
	@Override
	public int getCount() {
		return mList == null ? 0 : mList.size();

	}

	@Override
	public Object getItem(int position) {
		return mList == null ? null : mList.get(position);
		
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	abstract public View getView(int position, View convertView, ViewGroup parent);
	
	public void setList(ArrayList<T> list){
		this.mList = list;
		notifyDataSetChanged();
	}
	
	public ArrayList<T> getList(){
		return mList;
	}
	
	public void setList(T[] list){
		ArrayList<T> arrayList = new ArrayList<T>(list.length);  
		for (T t : list) {  
			arrayList.add(t);  
		}  
		setList(arrayList);
	}
	
	public ListView getListView(){
		return mListView;
	}
	
	public void setListView(ListView listView){
		mListView = listView;
	}
}
