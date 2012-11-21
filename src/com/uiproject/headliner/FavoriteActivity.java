package com.uiproject.headliner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.TabActivity;
import android.view.Menu;
import android.view.View;
import android.widget.TabHost;
import android.widget.TabHost.TabContentFactory;
import android.widget.TabHost.TabSpec;

public class FavoriteActivity extends TabActivity implements TabContentFactory {

	private TabHost th;
	private List<HashMap<String, Object>> topicList;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		
		topicList = Data.topicList;

		th = getTabHost();
		th.setup();
		th.setOnTabChangedListener(tabChangeListener);
		
		for(int i = 0; i < topicList.size(); i++) {
			String topic = (String) topicList.get(i).get(Data.TOPICS);
			TabSpec tabSpec = th.newTabSpec(topic);
			tabSpec.setIndicator((String) topic);
			tabSpec.setContent(this);
			th.addTab(tabSpec);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_favorite, menu);
		return true;
	}

	TabHost.OnTabChangeListener tabChangeListener = new TabHost.OnTabChangeListener() {

		@Override
		public void onTabChanged(String tabId) {
			FragmentManager fm = getFragmentManager();

			FragmentTransaction ft = fm.beginTransaction();

			for (int i = 0; i < Data.topics.length; i++) {
				FavorListFragment fragment = (FavorListFragment) fm
						.findFragmentByTag(Data.topics[i]);
				if (fragment != null)
					ft.detach(fragment);
			}

			System.out.println(tabId);
			
			for (int i = 0; i < Data.topics.length; i++) {
				if (tabId.equalsIgnoreCase(Data.topics[i])) {
					FavorListFragment fragment = (FavorListFragment) fm
							.findFragmentByTag(Data.topics[i]);
					if (fragment == null) {
						ft.add(android.R.id.tabcontent, new FavorListFragment(
								Data.topics[i]), Data.topics[i]);
					} else {
						ft.attach(fragment);
					}
					break;
				}
			}
			ft.commit();
		}
	};

	@Override
	public View createTabContent(String tag) {
		View v = new View(getBaseContext());
		return v;
	}
}
