package com.uiproject.headliner;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class HomeActivity extends TabActivity {

	private TabHost th;
	private String topics[] = { "topic 1", "topic 2", "topic 3" };

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

		th = this.getTabHost();

		for (int i = 0; i < topics.length; i++) {
			TabSpec ts = th.newTabSpec("Tag" + i);
			ts.setIndicator(topics[i]);
			Intent intent = new Intent(this, ListActivity.class);
			Bundle bundle = new Bundle();
			bundle.putInt("topics", i);
			intent.putExtra("param", bundle);
			ts.setContent(intent);
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
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}
