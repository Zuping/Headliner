package com.uiproject.headliner;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.TabActivity;
import android.view.Menu;
import android.widget.TabHost;

public class FavoriteActivity extends TabActivity {
	
	private TabHost th;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        
        th = this.getTabHost();
        th.setup();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_favorite, menu);
        return true;
    }
    
    TabHost.OnTabChangeListener tabChangeListener = new TabHost.OnTabChangeListener() {
    	 
        @Override
        public void onTabChanged(String tabId) {
        	FragmentManager fm =   getFragmentManager();
        	
        	FragmentTransaction ft = fm.beginTransaction();
        	
        	ft.commit();
        }
    };
}
