package com.example.hollis.deckbuilder;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ExpandableListView;
import com.example.hollis.deckbuilder.Adapters.CategoryAdapter;
import com.example.hollis.deckbuilder.Fragments.CardListFragment;

public class MainActivity extends AppCompatActivity implements CategoryAdapter.OnSearchPropertiesChangedListener {
    public final static String SHARED_PREFERENCES_KEY = "deckbrew";
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    ExpandableListView mNavBarListView;
    CardListFragment curFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_main);
        setNavigationDrawer();
        setFragment();
    }


    public void setNavigationDrawer(){
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_main_layout);
        mNavBarListView = (ExpandableListView) findViewById(R.id.drawer_main_nav_bar_list_view);
        mNavBarListView.setAdapter(new CategoryAdapter(this));
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close);
        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.addDrawerListener(mDrawerToggle);
    }

    public void setFragment(){
        curFragment = new CardListFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.activity_main_frame, curFragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onSearchPropertiesChanged(SearchProperties searchProperties) {
        curFragment.setSearchProperties(searchProperties);
    }
}
