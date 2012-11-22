package com.uiproject.headliner;

import java.util.HashMap;
import java.util.List;

import android.app.ListFragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class HomeListFragment extends ListFragment {
	
	private String topic;
	private MyAdapter myAdapter;
	private ListView listView;
	
	private List<HashMap<String, Object>> newsList;
	private List<HashMap<String, Object>> favoriteList;
	
	protected ActionMode mActionMode;
	
	public HomeListFragment(String _topic) {
		topic = _topic;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		
		getData();
		myAdapter = new MyAdapter(getActivity());
		setListAdapter(myAdapter);
		
		return inflater.inflate(R.layout.activity_list, container, false);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		listView = getListView();
		listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
		listView.setMultiChoiceModeListener(new ModeCallback());
		registerForContextMenu(listView);
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		HashMap<String, Object> map = (HashMap<String, Object>) newsList
				.get(position);
		Uri webpage = Uri.parse("http://www.android.com");
		Intent webIntent = new Intent(Intent.ACTION_VIEW, webpage);
		startActivity(webIntent);
	}
	
	
	public void getData() {
		if(topic.equals(Data.topics[0])) {
			newsList = Data.trendingList;
			favoriteList = Data.trendingFavorList;
		} else if(topic.equals(Data.topics[1])) {
			newsList = Data.nationalList;
			favoriteList = Data.nationaFavorlList;
		} else if(topic.equals(Data.topics[2])) {
			newsList = Data.internationalList;
			favoriteList = Data.internationalFavorList;
		} else if(topic.equals(Data.topics[3])) {
			newsList = Data.sportList;
			favoriteList = Data.sportFavorList;
		} else {
			newsList = Data.localList;
			favoriteList = Data.localFavorList;
		}
	}

	private class ModeCallback implements ListView.MultiChoiceModeListener {

		public boolean onCreateActionMode(ActionMode mode, Menu menu) {
			MenuInflater inflater = getActivity().getMenuInflater();
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
				Toast.makeText(getActivity(),
						"Shared " + listView.getCheckedItemCount() + " items",
						Toast.LENGTH_SHORT).show();
				mode.finish();
				break;
			default:
				Toast.makeText(getActivity(), "Clicked " + item.getTitle(),
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

		public MyAdapter(final Context context) {
			inflater = LayoutInflater.from(context);
		}

		public int getCount() {
			return newsList.size();
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
			HashMap<String, Object> map = (HashMap<String, Object>) newsList
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
						
						// compare title here
						if(((String) _map.get("title")).equals((String) map.get("title")))
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
