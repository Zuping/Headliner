package com.uiproject.headliner;

import java.util.ArrayList;

import com.uiproject.headliner.dragdrop.DragNDropListActivity;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.TabActivity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class HomeActivity extends TabActivity {

	private TabHost th;
	private String topics[] = Data.topics;

	ArrayList<String> topicList;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

		th = this.getTabHost();

		Bundle bundle = getIntent().getExtras();
		if (bundle != null) {
			topicList = bundle.getStringArrayList(Data.TOPICS);
			topicList.toArray(topics);
		}
		
		if (topicList == null) {
			Log.d("topicList", "the list is null");
			topicList = new ArrayList<String>();
			for (int i = 0; i < topics.length; i++)
				topicList.add(1 + topics[i]);
		}

		for (int i = 0; i < topicList.size(); i++) {
			String item = topicList.get(i);
			if (item.charAt(0) == '0')
				continue;
			item = item.substring(1);
			TabSpec ts = th.newTabSpec("Tag" + i);
			ts.setIndicator(item);
			Intent tmpIntent = new Intent(this, ListActivity.class);
			Bundle tmpBundle = new Bundle();
			tmpBundle.putInt("topics", i);
			tmpIntent.putExtra("param", tmpBundle);
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
			Intent intent = new Intent(this, ListActivity.class);
			startActivity(intent);
			return true;
		}
		case R.id.menu_settings: {
			Intent intent = new Intent(this, DragNDropListActivity.class);
			ArrayList<String> topicList = new ArrayList<String>();
			for (int i = 0; i < topics.length; i++)
				topicList.add(1 + topics[i]);
			Bundle bundle = new Bundle();
			bundle.putStringArrayList(Data.TOPICS, topicList);
			intent.putExtras(bundle);
			startActivity(intent);
			return true;
		}
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}
