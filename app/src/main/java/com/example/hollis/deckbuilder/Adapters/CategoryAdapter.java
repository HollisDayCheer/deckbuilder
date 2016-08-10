package com.example.hollis.deckbuilder.Adapters;

import android.content.ClipData;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.DataSetObserver;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ExpandableListAdapter;
import android.widget.TextView;

import com.example.hollis.deckbuilder.MainActivity;
import com.example.hollis.deckbuilder.R;
import com.example.hollis.deckbuilder.SearchProperties;

import java.security.acl.Group;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hollis on 8/4/16.
 */
public class CategoryAdapter implements ExpandableListAdapter {
    boolean[] checkBoxes;
    SearchProperties searchProperties;
    List<String> mCategoryList;
    Map<String, List<String>> mDataMap;
    OnSearchPropertiesChangedListener onSearchPropertiesChangedListener;
    Context context;

    public SearchProperties getSearchProperties(){
        return searchProperties;
    }


    public CategoryAdapter(Context context) {
        super();
        this.context = context;
        mCategoryList = Arrays.asList(context.getResources().getStringArray(R.array.group_names));
        mDataMap = new HashMap<>();
        mDataMap.put("Color", Arrays.asList(context.getResources().getStringArray(R.array.colors)));
        mDataMap.put("Type", Arrays.asList(context.getResources().getStringArray(R.array.card_types)));
        mDataMap.put("Format", Arrays.asList(context.getResources().getStringArray(R.array.formats)));
        mDataMap.put("Search Options", Arrays.asList(context.getResources().getStringArray(R.array.search_options)));
        searchProperties = new SearchProperties();
        searchProperties.setProperty("name", true);
        this.onSearchPropertiesChangedListener = (OnSearchPropertiesChangedListener) context;
        //hardcoded b/cuz lazy, total number of items in mDataMap's arrays
        //also too big b/cuz lazy
        checkBoxes = new boolean[100];
    }



    @Override
    public int getGroupCount() {
        return  mCategoryList.size()+1;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if(groupPosition== 0){
            return 0;
        }else{
            return mDataMap.get(mCategoryList.get(groupPosition-1)).size();
        }
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mDataMap.get(mCategoryList.get(groupPosition-1));
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return ((List<String>) getGroup(groupPosition-1)).get(childPosition);
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
        if(groupPosition==0){
            return LayoutInflater.from(context).inflate(R.layout.expandable_list_view_drawer_header, parent, false);
        }else {
            GroupViewHolder viewHolder;
            if (convertView == null || convertView.getTag() == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.expandable_list_view_header, parent, false);
                viewHolder = new GroupViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (GroupViewHolder) convertView.getTag();
            }
            viewHolder.textView.setText(mCategoryList.get(groupPosition - 1));
            return convertView;
        }
    }


    //TODO: Create custom XML for child view with color image/name, use it instead of simplelistitem
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        //TODO: Create a search properties object from shared preferences, maybe do that in onCreate...
        final String curItem = mDataMap.get(mCategoryList.get(groupPosition-1)).get(childPosition);
        final int position = (groupPosition*10)+childPosition;
        ItemViewHolder viewHolder;
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.expandable_list_view_item, parent, false);
            viewHolder = new ItemViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder =(ItemViewHolder) convertView.getTag();
            viewHolder.checkBox.setOnCheckedChangeListener(null);
        }

        boolean isChecked = checkBoxes[position];
        Log.d("CategoryAdapter", "getChildView: " + curItem + " is " + isChecked);
        viewHolder.checkBox.setChecked(isChecked);
        viewHolder.checkBox.setText(curItem);
        viewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                searchProperties.setProperty(curItem, isChecked);
                onSearchPropertiesChangedListener.onSearchPropertiesChanged(searchProperties);
                checkBoxes[position] = isChecked;
            }
        });
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
    public interface OnSearchPropertiesChangedListener {
        void onSearchPropertiesChanged (SearchProperties searchProperties);
    }


    public class GroupViewHolder{
        TextView textView;
        public GroupViewHolder(View v){
            this.textView =(TextView) v.findViewById(R.id.list_view_header_text);
        }
    }

    public class ItemViewHolder{
        CheckBox checkBox;
        public ItemViewHolder(View v){
            checkBox = (CheckBox) v.findViewById(R.id.expandable_list_view_check_box);
        }
    }

}
