package com.uiproject.headliner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.ActionMode;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.widget.AbsListView.MultiChoiceModeListener;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class ListActivity extends Activity {

	MyAdapter myAdapter;
	private List<Map<String, Object>> listItems;
	private ListView listView;

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
                Toast.makeText(ListActivity.this, "Shared " + listView.getCheckedItemCount() +
                        " items", Toast.LENGTH_SHORT).show();
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

        public void onItemCheckedStateChanged(ActionMode mode,
                int position, long id, boolean checked) {
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

}
