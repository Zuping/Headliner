package com.uiproject.headliner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.TabActivity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class HomeActivity extends TabActivity {

	private TabHost th;
	private List<HashMap<String, Object>> topicList;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

		Data.init();
		topicList = Data.topicList;
		
		th = this.getTabHost();

		for (int i = 0; i < topicList.size(); i++) {
			HashMap<String, Object> map = topicList.get(i);
			if (!(Boolean) map.get(Data.CHECKED))
				continue;
			TabSpec ts = th.newTabSpec("Tag" + i);
			ts.setIndicator((String) map.get(Data.TOPICS));
			Intent tmpIntent = new Intent(this, ListActivity.class);
			tmpIntent.putExtra(Data.TAB_KEY, (String) map.get(Data.TOPICS));
			ts.setContent(tmpIntent);
			th.addTab(ts);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.context_menu, menu);
		ActionBar actionBar = getActionBar();
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
		case R.id.favorite: {
			Intent intent = new Intent(this, FavoriteActivity.class);
			startActivity(intent);
			return true;
		}
		case R.id.menu_settings: {
			Intent intent = new Intent(this, DragListActivity.class);
			startActivity(intent);
			return true;
		}
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	

	@Override
	protected void onDestroy() {
		super.onDestroy();
//		DBHelper dbHelper = new DBHelper(this);
//		dbHelper.deleteTopic();
//		for(int i = 0; i < topicList.size(); i++) {
//			ContentValues values = new ContentValues();
//			String item = topicList.get(i);
//			if(item.charAt(0) == '0') {
//				values.put("checked", 0);
//			} else {
//				values.put("checked", 1);
//			}
//			item = item.substring(1);
//			values.put("topic", item);
//			dbHelper.insertTopic(values);
//		}
	}
}
