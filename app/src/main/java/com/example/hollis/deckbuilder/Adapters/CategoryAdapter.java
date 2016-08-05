package com.example.hollis.deckbuilder.Adapters;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.TextView;

import com.example.hollis.deckbuilder.R;

import org.w3c.dom.Text;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hollis on 8/4/16.
 */
public class CategoryAdapter implements ExpandableListAdapter {
    List<String> mCategoryList;
    Map<String, List<String>> mDataMap;
    Context context;


    public CategoryAdapter(Context context) {
        super();
        this.context = context;
        mCategoryList = Arrays.asList(context.getResources().getStringArray(R.array.group_names));
        mDataMap = new HashMap<>();
        mDataMap.put("Color", Arrays.asList(context.getResources().getStringArray(R.array.colors)));
        mDataMap.put("Type", Arrays.asList(context.getResources().getStringArray(R.array.card_types)));
        mDataMap.put("Format", Arrays.asList(context.getResources().getStringArray(R.array.formats)));
        mDataMap.put("Search Options", Arrays.asList(context.getResources().getStringArray(R.array.search_options)));

    }



    @Override
    public int getGroupCount() {
        return  mCategoryList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mDataMap.get(mCategoryList.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mDataMap.get(mCategoryList.get(groupPosition));
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return ((List<String>) getGroup(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1, parent, false);
        }
        TextView textView = (TextView) convertView.findViewById(android.R.id.text1);
        textView.setText(mCategoryList.get(groupPosition));
        return convertView;
    }


    //TODO: Create custom XML for child view with color image/name, use it instead of simplelistitem
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1, parent, false);
        }
        TextView textView = (TextView) convertView.findViewById(android.R.id.text1);
        textView.setText(mDataMap.get(mCategoryList.get(groupPosition)).get(childPosition));
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public void onGroupExpanded(int groupPosition) {

    }

    @Override
    public void onGroupCollapsed(int groupPosition) {

    }

    @Override
    public long getCombinedChildId(long groupId, long childId) {
        return 0;
    }

    @Override
    public long getCombinedGroupId(long groupId) {
        return 0;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }
}
