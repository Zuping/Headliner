package com.uiproject.headliner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.SearchManager;
import android.app.TabActivity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.TabHost;
import android.widget.TabHost.TabContentFactory;
import android.widget.TabHost.TabSpec;

public class HomeActivity extends TabActivity implements TabContentFactory {

	private TabHost th;
	private List<HashMap<String, Object>> topicList;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

		Data.init();
		topicList = Data.topicList;

		th = getTabHost();
		th.setup();

		TabHost.OnTabChangeListener tabChangeListener = new TabHost.OnTabChangeListener() {
			public void onTabChanged(String tabId) {
				FragmentManager fm = getFragmentManager();

				FragmentTransaction ft = fm.beginTransaction();

				for (int i = 0; i < Data.topics.length; i++) {
					HomeListFragment fragment = (HomeListFragment) fm
							.findFragmentByTag(Data.topics[i]);
					if (fragment != null)
						ft.detach(fragment);
				}

				System.out.println(tabId);

				for (int i = 0; i < Data.topics.length; i++) {
					if (tabId.equalsIgnoreCase(Data.topics[i])) {
						HomeListFragment fragment = (HomeListFragment) fm
								.findFragmentByTag(Data.topics[i]);
						if (fragment == null) {
							ft.add(android.R.id.tabcontent,
									new HomeListFragment(Data.topics[i]),
									Data.topics[i]);
						} else {
							ft.attach(fragment);
						}
						break;
					}
				}
				ft.commit();
			}
		};

		th.setOnTabChangedListener(tabChangeListener);

		for (int i = 0; i < topicList.size(); i++) {
			String topic = (String) topicList.get(i).get(Data.TOPICS);
			TabSpec tabSpec = th.newTabSpec(topic);
			tabSpec.setIndicator((String) topic);
			tabSpec.setContent(this);
			th.addTab(tabSpec);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.context_menu, menu);
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setHomeButtonEnabled(true);
		
		SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
	    SearchView searchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();
	    searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
	    searchView.setSubmitButtonEnabled(false);
	    
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
		// DBHelper dbHelper = new DBHelper(this);
		// dbHelper.deleteTopic();
		// for(int i = 0; i < topicList.size(); i++) {
		// ContentValues values = new ContentValues();
		// String item = topicList.get(i);
		// if(item.charAt(0) == '0') {
		// values.put("checked", 0);
		// } else {
		// values.put("checked", 1);
		// }
		// item = item.substring(1);
		// values.put("topic", item);
		// dbHelper.insertTopic(values);
		// }
	}

	public View createTabContent(String arg0) {
		View v = new View(getBaseContext());
		return v;
	}
}
