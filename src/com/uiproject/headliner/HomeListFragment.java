package com.uiproject.headliner;

import java.util.HashMap;
import java.util.List;

import com.uiproject.headliner.data.Data;

import android.app.AlertDialog;
import android.app.ListFragment;
import android.content.Context;
import android.content.DialogInterface;
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
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class HomeListFragment extends ListFragment {

	private String topic;
	private MyAdapter myAdapter;
	private ListView listView;
	private TextView textLocation;

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

		View v = getView();
		if (topic.equals(Data.topics[4]))
			v.findViewById(R.id.locationLayout).setVisibility(
					android.view.View.VISIBLE);

		textLocation = (TextView) v.findViewById(R.id.textLocation);
		textLocation.setText(Data.location);
		Button changeLocation = (Button) v
				.findViewById(R.id.buttonChangeLocation);
		changeLocation.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				new AlertDialog.Builder(getActivity())
						.setTitle(R.string.choose_location)
						.setItems(R.array.locations,
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int which) {
										Data.changeLocation(getResources()
												.getStringArray(
														R.array.locations)[which]);
										newsList = Data.localList;
										favoriteList = Data.localFavorList;
										myAdapter.notifyDataSetChanged();
										textLocation
												.setText(getResources()
														.getStringArray(
																R.array.locations)[which]);
									}
								}).show();
			}
		});
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		HashMap<String, Object> map = (HashMap<String, Object>) newsList
				.get(position);
		Uri webpage = Uri.parse((String) map.get("url"));
		Intent webIntent = new Intent(Intent.ACTION_VIEW, webpage);
		startActivity(webIntent);
	}

	public void getData() {
		if (topic.equals(Data.topics[0])) {

		} else if (topic.equals(Data.topics[1])) {
			newsList = Data.nationalList;
			favoriteList = Data.nationalFavorlList;
		} else if (topic.equals(Data.topics[2])) {
			newsList = Data.internationalList;
			favoriteList = Data.internationalFavorList;
		} else if (topic.equals(Data.topics[3])) {
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
				holder.news = (TextView) convertView
						.findViewById(R.id.newsTextView);
				holder.image = (ImageView) convertView
						.findViewById(R.id.newsIcon);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			HashMap<String, Object> map = (HashMap<String, Object>) newsList
					.get(position);
			for (int i = 0; i < favoriteList.size(); i++) {
				HashMap<String, Object> _map = favoriteList.get(i);
				String _news = (String) _map.get("news");
				String news = (String) map.get("news");

				if (_news.equals(news)) {
					System.out.println(_news);
					System.out.println(news);

					map.remove("starBox");
					map.put("starBox", true);
					break;
				}
			}
			if ((Boolean) map.get("starBox"))
				holder.starBox.setChecked(true);
			else
				holder.starBox.setChecked(false);
			holder.news.setText((String) map.get("news"));
			holder.image.setImageResource((Integer) map.get("image"));
			holder.starBox
					.setOnClickListener(new MyOnCheckedChangeListener(map));
			return convertView;
		}

		public Object getItem(int arg0) {
			return null;
		}

		public long getItemId(int position) {
			return 0;
		}

		private class MyOnCheckedChangeListener implements OnClickListener {

			private HashMap<String, Object> map;

			public MyOnCheckedChangeListener(HashMap<String, Object> _map) {
				map = _map;
			}

			@Override
			public void onClick(View v) {
				CheckBox checkbox = (CheckBox) v;
				if (checkbox.isChecked()) {
					map.remove("starBox");
					map.put("starBox", true);
					favoriteList.add(map);
				} else {
					map.remove("starBox");
					map.put("starBox", false);
					for (int i = 0; i < favoriteList.size(); i++) {
						HashMap<String, Object> _map = (HashMap<String, Object>) favoriteList
								.get(i);

						// compare news here
						if (((String) _map.get("news")).equals((String) map
								.get("news")))
							favoriteList.remove(i);
					}
					notifyDataSetChanged();
				}
			}
		}

		private class ViewHolder {
			public CheckBox starBox;
			public TextView news;
			public ImageView image;
		}

	}
}
