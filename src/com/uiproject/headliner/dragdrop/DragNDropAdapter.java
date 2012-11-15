/*
 * Copyright (C) 2010 Eric Harlow
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.uiproject.headliner.dragdrop;

import java.util.ArrayList;

import com.uiproject.headliner.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

public final class DragNDropAdapter extends BaseAdapter implements RemoveListener, DropListener{

	private int[] mIds;
    private int[] mLayouts;
    private LayoutInflater mInflater;
    private ArrayList<String> mContent;

    public DragNDropAdapter(Context context, ArrayList<String> content) {
        init(context,new int[]{android.R.layout.simple_list_item_1},new int[]{android.R.id.text1}, content);
    }
    
    public DragNDropAdapter(Context context, int[] itemLayouts, int[] itemIDs, ArrayList<String> content) {
    	init(context,itemLayouts,itemIDs, content);
    }

    private void init(Context context, int[] layouts, int[] ids, ArrayList<String> content) {
    	// Cache the LayoutInflate to avoid asking for a new one each time.
    	mInflater = LayoutInflater.from(context);
    	mIds = ids;
    	mLayouts = layouts;
    	mContent = content;
    }
    
    /**
     * The number of items in the list
     * @see android.widget.ListAdapter#getCount()
     */
    public int getCount() {
        return mContent.size();
    }

    /**
     * Since the data comes from an array, just returning the index is
     * sufficient to get at the data. If we were using a more complex data
     * structure, we would return whatever object represents one row in the
     * list.
     *
     * @see android.widget.ListAdapter#getItem(int)
     */
    public String getItem(int position) {
        return mContent.get(position);
    }

    /**
     * Use the array index as a unique id.
     * @see android.widget.ListAdapter#getItemId(int)
     */
    public long getItemId(int position) {
        return position;
    }

    /**
     * Make a view to hold each row.
     *
     * @see android.widget.ListAdapter#getView(int, android.view.View,
     *      android.view.ViewGroup)
     */
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;       
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.dragitem, null);
            holder = new ViewHolder();
            holder.text = (TextView) convertView.findViewById(R.id.topicTextView);
            holder.checkbox = (CheckBox) convertView.findViewById(R.id.topicCheckBox);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.text.setText(mContent.get(position));

        return convertView;
    }

    private class ViewHolder {
    	CheckBox checkbox;
        TextView text;
    }

	public void onRemove(int which) {
		if (which < 0 || which > mContent.size()) return;		
		mContent.remove(which);
	}

	public void onDrop(int from, int to) {
		String temp = mContent.get(from);
		mContent.remove(from);
		mContent.add(to,temp);
	}
}