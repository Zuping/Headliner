package com.uiproject.headliner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.ActionMode;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnLongClickListener;
import android.widget.AbsListView.MultiChoiceModeListener;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ListActivity extends Activity {

	private MyAdapter myAdapter;
	private ListView listView;
	
	private List<Map<String, Object>> listItems;
	private List<Map<String, Object>> favoriteList;
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list);
		listView = (ListView) findViewById(R.id.listView);
		getData();
		listView.setMultiChoiceModeListener(new ModeCallback());
		myAdapter = new MyAdapter(this, listItems);
		listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);

		listView.setAdapter(myAdapter);
		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				HashMap<String, Object> map = (HashMap<String, Object>) listItems
						.get(position);
				Log.d("list", (String) map.get("abstract"));
				Uri webpage = Uri.parse("http://www.android.com");
				Intent webIntent = new Intent(Intent.ACTION_VIEW, webpage);
				startActivity(webIntent);
			}
		});

		registerForContextMenu(listView);
	}

	protected ActionMode mActionMode;

	public void getData() {
		Intent intent = getIntent();
		String topic = intent.getStringExtra(Data.TOPICS);
		listItems = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < 10; i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("starBox", false);
			map.put("title", "title " + i);
			map.put("abstract",
					"abstract abstract abstract abstract abstract abstract");
			map.put("date", "11/4/2012");
			listItems.add(map);
		}
	}

	private class ModeCallback implements ListView.MultiChoiceModeListener {

		public boolean onCreateActionMode(ActionMode mode, Menu menu) {
			MenuInflater inflater = getMenuInflater();
			inflater.inflate(R.menu.actionmode_menu, menu);
			mode.setTitle("Select Items");
			return true;
		}

		public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
			return true;
		}

		public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
			switch (item.getItemId()) {
			case R.id.share:
				Toast.makeText(ListActivity.this,
						"Shared " + listView.getCheckedItemCount() + " items",
						Toast.LENGTH_SHORT).show();
				mode.finish();
				break;
			default:
				Toast.makeText(ListActivity.this, "Clicked " + item.getTitle(),
						Toast.LENGTH_SHORT).show();
				mode.finish();
				break;
			}
			return true;
		}

		public void onDestroyActionMode(ActionMode mode) {
		}

		public void onItemCheckedStateChanged(ActionMode mode, int position,
				long id, boolean checked) {
			final int checkedCount = listView.getCheckedItemCount();
			switch (checkedCount) {
			case 0:
				mode.setSubtitle(null);
				break;
			case 1:
				mode.setSubtitle("One item selected");
				break;
			default:
				mode.setSubtitle("" + checkedCount + " items selected");
				break;
			}
		}
	}

	private class MyAdapter extends BaseAdapter {

		private LayoutInflater inflater;
		private List<Map<String, Object>> listItem;

		public MyAdapter(final Context context,
				final List<Map<String, Object>> _listItem) {
			inflater = LayoutInflater.from(context);
			listItem = _listItem;
		}

		public int getCount() {
			return listItem.size();
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null) {
				holder = new ViewHolder();
				convertView = inflater.inflate(R.layout.listitem, null);
				holder.starBox = (CheckBox) convertView
						.findViewById(R.id.starCheckBox);
				holder.title = (TextView) convertView
						.findViewById(R.id.titleTextView);
				holder.abstracts = (TextView) convertView
						.findViewById(R.id.absTextView);
				holder.date = (TextView) convertView
						.findViewById(R.id.dateTextView);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			HashMap<String, Object> map = (HashMap<String, Object>) listItem
					.get(position);
			if ((Boolean) map.get("starBox"))
				holder.starBox.setChecked(true);
			else
				holder.starBox.setChecked(false);
			holder.title.setText((String) map.get("title"));
			holder.abstracts.setText((String) map.get("abstract"));
			holder.date.setText((String) map.get("date"));

			holder.starBox
					.setOnCheckedChangeListener(new MyOnCheckedChangeListener(map));
			return convertView;
		}

		public Object getItem(int arg0) {
			return null;
		}

		public long getItemId(int position) {
			return 0;
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
					map.remove("starBox");
					map.put("starBox", true);
					favoriteList.add(map);
				} else {
					map.remove("starBox");
					map.put("starBox", false);
					for(int i = 0; i < favoriteList.size(); i++) {
						HashMap<String, Object> _map = (HashMap<String, Object>) favoriteList.get(i);
						if(((String) _map.get("url")).equals((String) map.get("url")))
							favoriteList.remove(_map);
					}
				}
			}
		}

		private class ViewHolder {
			public CheckBox starBox;
			public TextView title;
			public TextView abstracts;
			public TextView date;
			public String url;
		}

	}

}
