package com.example.hollis.deckbuilder;

import android.content.Intent;
import android.database.Cursor;
import android.database.Observable;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
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
import com.example.hollis.deckbuilder.Models.Card;
import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.widget.RxTextView;

import java.util.concurrent.TimeUnit;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    ExpandableListView mNavBarListView;
    Button button;
    EditText searchTextView;
    DeckSQliteOpenHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_main);
        setNavigationDrawer();
        db = DeckSQliteOpenHelper.getInstance(this);
        button = (Button) findViewById(R.id.activity_main_go_to_downloads_button);
        searchTextView = (EditText) findViewById(R.id.activity_main_auto_complete_text);
        listView = (ListView) findViewById(R.id.activity_main_list_view);
        final Cursor cursor = db.getLegacyCards();
        final CardAdapter cursorAdapter = new CardAdapter(this, cursor, 0);
        listView.setAdapter(cursorAdapter);
        rx.Observable<CharSequence> editTextStream = RxTextView.textChanges(searchTextView).debounce(300, TimeUnit.MILLISECONDS);
        editTextStream.observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<CharSequence>() {
            @Override
            public void call(CharSequence charSequence) {
                if(charSequence.toString().equals("")) {
                    cursorAdapter.swapCursor(db.getLegacyCards());
                }else{
                    cursorAdapter.swapCursor(db.searchLegacyCardsByName(charSequence));
                }
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, StarterActivity.class);
                startActivity(intent);
            }
        });
    }

    public void setNavigationDrawer(){
        mNavBarListView = (ExpandableListView) findViewById(R.id.drawer_main_nav_bar_list_view);
        mNavBarListView.setAdapter(new CategoryAdapter(this));

    }
}
