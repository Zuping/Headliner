/*
 * Copyright (C) 2010 Eric Harlow
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.uiproject.headliner.dragdrop;

import java.util.ArrayList;
import java.util.HashMap;

import com.uiproject.headliner.Data;
import com.uiproject.headliner.HomeActivity;
import com.uiproject.headliner.R;
import android.app.ActionBar;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class DragNDropListActivity extends ListActivity {

	private ArrayList<String> topics;
	private ArrayList<HashMap<String, Object>> mapList;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.dragndroplistview);

		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		topics = (ArrayList<String>) bundle.get(Data.TOPICS);

		mapList = new ArrayList<HashMap<String, Object>>();
		for (int i = 0; i < topics.size(); i++) {
			String item = topics.get(i);
			HashMap<String, Object> map = new HashMap<String, Object>();
			if (item.charAt(0) == '0')
				map.put("checked", false);
			else
				map.put("checked", true);
			map.put("topic", item.substring(1));
			mapList.add(map);
		}

		setListAdapter(new DragNDropAdapter(this, mapList));
		ListView listView = getListView();

		if (listView instanceof DragNDropListView) {
			((DragNDropListView) listView).setDropListener(mDropListener);
			((DragNDropListView) listView).setRemoveListener(mRemoveListener);
			((DragNDropListView) listView).setDragListener(mDragListener);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.setting_menu, menu);
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setHomeButtonEnabled(true);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home: {
			Intent intent = new Intent(this, HomeActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			return true;
		}
		case R.id.done: {
			Intent intent = new Intent(this, HomeActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			ArrayList<String> topicList = new ArrayList<String>();
			for (int i = 0; i < mapList.size(); i++) {
				HashMap<String, Object> map = mapList.get(i);
				String stringItem = "";
				if ((Boolean) map.get("checked"))
					stringItem += 1;
				else
					stringItem += 0;
				stringItem += map.get("topic");
				topicList.add(stringItem);
			}
			Bundle bundle = new Bundle(0);
			bundle.putStringArrayList(Data.TOPICS, topicList);
			intent.putExtras(bundle);
			startActivity(intent);
			return true;
		}
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private DropListener mDropListener = new DropListener() {
		public void onDrop(int from, int to) {
			ListAdapter adapter = getListAdapter();
			if (adapter instanceof DragNDropAdapter) {
				((DragNDropAdapter) adapter).onDrop(from, to);
				getListView().invalidateViews();
			}
		}
	};

	private RemoveListener mRemoveListener = new RemoveListener() {
		public void onRemove(int which) {
			ListAdapter adapter = getListAdapter();
			if (adapter instanceof DragNDropAdapter) {
				((DragNDropAdapter) adapter).onRemove(which);
				getListView().invalidateViews();
			}
		}
	};

	private DragListener mDragListener = new DragListener() {

		int backgroundColor = 0xe0103010;
		int defaultBackgroundColor;

		public void onDrag(int x, int y, ListView listView) {
		}

		public void onStartDrag(View itemView) {
			itemView.setVisibility(View.INVISIBLE);
			defaultBackgroundColor = itemView.getDrawingCacheBackgroundColor();
			itemView.setBackgroundColor(backgroundColor);
			ImageView iv = (ImageView) itemView.findViewById(R.id.ImageView01);
			if (iv != null)
				iv.setVisibility(View.INVISIBLE);
		}

		public void onStopDrag(View itemView) {
			itemView.setVisibility(View.VISIBLE);
			itemView.setBackgroundColor(defaultBackgroundColor);
			ImageView iv = (ImageView) itemView.findViewById(R.id.ImageView01);
			if (iv != null)
				iv.setVisibility(View.VISIBLE);
		}

	};

	private class DragNDropAdapter extends BaseAdapter implements
			RemoveListener, DropListener {

		private LayoutInflater mInflater;

		public DragNDropAdapter(Context context,
				ArrayList<HashMap<String, Object>> content) {
			mInflater = LayoutInflater.from(context);
			mapList = content;
		}

		public int getCount() {
			return mapList.size();
		}

		public HashMap<String, Object> getItem(int position) {
			return mapList.get(position);
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;
			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.dragitem, null);
				holder = new ViewHolder();
				holder.text = (TextView) convertView
						.findViewById(R.id.topicTextView);
				holder.checkbox = (CheckBox) convertView
						.findViewById(R.id.topicCheckBox);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			final HashMap<String, Object> map = mapList.get(position);
			if ((Boolean) map.get("checked"))
				holder.checkbox.setChecked(true);
			else
				holder.checkbox.setChecked(false);
			holder.text.setText((String) map.get("topic"));
			holder.checkbox
					.setOnCheckedChangeListener(new MyOnCheckedChangeListener(
							map));
			return convertView;
		}

		private class MyOnCheckedChangeListener implements
				OnCheckedChangeListener {

			private HashMap<String, Object> map;

			public MyOnCheckedChangeListener(HashMap<String, Object> _map) {
				map = _map;
			}

			public void onCheckedChanged(CompoundButton checkbox,
					boolean isChecked) {
				if (checkbox.isChecked()) {
					map.remove("checked");
					map.put("checked", true);
				} else {
					map.remove("checked");
					map.put("checked", false);
				}
			}
		}

		private class ViewHolder {
			CheckBox checkbox;
			TextView text;
		}

		public void onRemove(int which) {
			if (which < 0 || which > mapList.size())
				return;
			mapList.remove(which);
		}

		public void onDrop(int from, int to) {
			HashMap<String, Object> temp = mapList.get(from);
			mapList.remove(from);
			mapList.add(to, temp);
		}
	}

}