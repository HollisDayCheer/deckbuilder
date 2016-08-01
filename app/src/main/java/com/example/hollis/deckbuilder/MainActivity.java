package com.example.hollis.deckbuilder;

import android.content.Intent;
import android.database.Cursor;
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
import android.widget.ListView;
import android.widget.TextView;

import com.example.hollis.deckbuilder.Adapters.CardAdapter;
import com.example.hollis.deckbuilder.Adapters.CardNameAutofillAdapter;
import com.example.hollis.deckbuilder.DatabaseHelper.DeckSQliteOpenHelper;
import com.example.hollis.deckbuilder.Models.Card;
import com.venmo.cursor.CursorList;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    Button button;
    AutoCompleteTextView searchTextView;
    DeckSQliteOpenHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = DeckSQliteOpenHelper.getInstance(this);
        button = (Button) findViewById(R.id.activity_main_go_to_downloads_button);
        searchTextView = (AutoCompleteTextView) findViewById(R.id.activity_main_auto_complete_text);
        listView = (ListView) findViewById(R.id.activity_main_list_view);
        final Cursor cursor = db.getLegacyCards();
        final CardAdapter cursorAdapter = new CardAdapter(this, cursor, 0);
        listView.setAdapter(cursorAdapter);
        CardNameAutofillAdapter cardNameAutofillAdapter = new CardNameAutofillAdapter(this, cursor, 0);
        searchTextView.setAdapter(cardNameAutofillAdapter);
        searchTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView textView = (TextView) view.findViewById(android.R.id.text1);
                String selected = textView.getText().toString();
                cursorAdapter.swapCursor(db.searchLegacyCardsByName(selected));
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
}
