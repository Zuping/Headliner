package com.uiproject.headliner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.uiproject.headliner.data.Data;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnClickListener;

public class TrendingFragment extends Fragment implements OnChildClickListener {

	private List<String> groupItem = Data.trendingTopics;
	private List<List<HashMap<String, Object>>> childItem = Data.trendingChildList;

	private ExpandableListView expandbleList;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);

		return inflater.inflate(R.layout.trending_list, container, false);
	}

	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		expandbleList = (ExpandableListView) getActivity().findViewById(
				R.id.expandableListView);

		expandbleList.setDividerHeight(2);
		expandbleList.setGroupIndicator(null);
		expandbleList.setClickable(true);

		MyAdapter mNewAdapter = new MyAdapter(getActivity());
		expandbleList.setAdapter(mNewAdapter);
		expandbleList.setOnChildClickListener(this);
	}

	@Override
	public boolean onChildClick(ExpandableListView parent, View v,
			int groupPosition, int childPosition, long id) {
		System.out.println("haha");
		return true;
	}

	public class MyAdapter extends BaseExpandableListAdapter {

		public LayoutInflater minflater;

		public MyAdapter(final Context context) {
			minflater = LayoutInflater.from(context);
		}

		@Override
		public Object getChild(int groupPosition, int childPosition) {
			return null;
		}

		@Override
		public long getChildId(int groupPosition, int childPosition) {
			return 0;
		}

		@Override
		public View getChildView(int groupPosition, final int childPosition,
				boolean isLastChild, View convertView, ViewGroup parent) {

			ChildHolder holder = null;
			if (convertView == null) {
				holder = new ChildHolder();
				convertView = minflater.inflate(R.layout.listitem, null);
				holder.starBox = (CheckBox) convertView
						.findViewById(R.id.starCheckBox);
				holder.news = (TextView) convertView
						.findViewById(R.id.newsTextView);
				holder.image = (ImageView) convertView
						.findViewById(R.id.newsIcon);
				convertView.setTag(holder);
			} else {
				holder = (ChildHolder) convertView.getTag();
			}
			HashMap<String, Object> map = childItem.get(groupPosition).get(
					childPosition);
			if ((Boolean) map.get("starBox"))
				holder.starBox.setChecked(true);
			else
				holder.starBox.setChecked(false);
			holder.news.setText((String) map.get("news"));
			holder.image.setImageResource((Integer) map.get("image"));

			convertView.setOnClickListener(new MyOnClickListener(map));

			return convertView;
		}
		
		class MyOnClickListener implements OnClickListener {

			HashMap<String, Object> map;
			
			MyOnClickListener(HashMap<String, Object> _map) {
				map = _map;
			}
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Uri webpage = Uri.parse((String) map.get("url"));
				Intent webIntent = new Intent(Intent.ACTION_VIEW, webpage);
				startActivity(webIntent);
			}
			
		}

		@Override
		public int getChildrenCount(int groupPosition) {
			return childItem.get(groupPosition).size();
		}

		@Override
		public Object getGroup(int groupPosition) {
			return null;
		}

		@Override
		public int getGroupCount() {
			return groupItem.size();
		}

		@Override
		public void onGroupCollapsed(int groupPosition) {
			super.onGroupCollapsed(groupPosition);
		}

		@Override
		public void onGroupExpanded(int groupPosition) {
			super.onGroupExpanded(groupPosition);
		}

		@Override
		public long getGroupId(int groupPosition) {
			return 0;
		}

		@Override
		public View getGroupView(int groupPosition, boolean isExpanded,
				View convertView, ViewGroup parent) {
			GroupHolder holder = null;
			if (convertView == null) {
				convertView = minflater.inflate(R.layout.list_row, null);
				holder = new GroupHolder();
				holder.image = (ImageView) convertView
						.findViewById(R.id.imageView);
				holder.trendingTopics = (TextView) convertView
						.findViewById(R.id.groupTextView);
				convertView.setTag(holder);
			} else {
				holder = (GroupHolder) convertView.getTag();
			}
			holder.trendingTopics.setText(groupItem.get(groupPosition));

			return convertView;
		}

		@Override
		public boolean hasStableIds() {
			return false;
		}

		@Override
		public boolean isChildSelectable(int groupPosition, int childPosition) {
			return false;
		}

		private class GroupHolder {
			public TextView trendingTopics;
			public ImageView image;
		}

		private class ChildHolder {
			public CheckBox starBox;
			public TextView news;
			public ImageView image;
		}

	}

}
