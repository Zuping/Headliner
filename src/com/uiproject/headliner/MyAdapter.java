package com.uiproject.headliner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.uiproject.headliner.ViewHolder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

public class MyAdapter extends BaseAdapter {
	
	private LayoutInflater inflater;
	private List<Map<String, Object>> listItem;
	
	public MyAdapter(final Context context, final List<Map<String, Object>> _listItem) {
		inflater = LayoutInflater.from(context);
		listItem = _listItem;
	}
	
	public int getCount() {
		return listItem.size();
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if(convertView == null) {
			holder = new ViewHolder();
			
			convertView = inflater.inflate(R.layout.listitem, null);
			holder.starBox = (CheckBox) convertView.findViewById(R.id.starCheckBox);
			holder.title = (TextView) convertView.findViewById(R.id.titleTextView);
			holder.abstracts = (TextView) convertView.findViewById(R.id.absTextView);
			holder.date = (TextView) convertView.findViewById(R.id.dateTextView);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		HashMap<String, Object> map = (HashMap<String, Object>) listItem.get(position);
		if( (Boolean)map.get("starBox") ) 
			holder.starBox.setChecked(true);
		else
			holder.starBox.setChecked(false);
		holder.title.setText((String)map.get("title"));
		holder.abstracts.setText((String)map.get("abstract"));
		holder.date.setText((String)map.get("date"));
		return convertView;
	}

	public Object getItem(int arg0) {
		return null;
	}

	public long getItemId(int position) {
		return 0;
	}
	
}
