package com.example.hollis.deckbuilder;

import android.content.Intent;
import android.database.Cursor;
import android.database.Observable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.SimpleExpandableListAdapter;
import android.widget.TextView;

import com.example.hollis.deckbuilder.Adapters.CardAdapter;
import com.example.hollis.deckbuilder.Adapters.CardNameAutofillAdapter;
import com.example.hollis.deckbuilder.Adapters.CategoryAdapter;
import com.example.hollis.deckbuilder.DatabaseHelper.DeckSQliteOpenHelper;
import com.example.hollis.deckbuilder.Fragments.CardListFragment;
import com.example.hollis.deckbuilder.Models.Card;
import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.widget.RxTextView;

import java.util.concurrent.TimeUnit;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    ExpandableListView mNavBarListView;

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
        FragmentManager fragmentManager = (FragmentManager) getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.activity_main_frame, new CardListFragment());
        fragmentTransaction.commit();
    }
}
