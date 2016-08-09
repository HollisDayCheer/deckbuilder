package com.example.hollis.deckbuilder.Fragments;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.hollis.deckbuilder.Adapters.CardAdapter;
import com.example.hollis.deckbuilder.DatabaseHelper.DeckSQliteOpenHelper;
import com.example.hollis.deckbuilder.R;
import com.example.hollis.deckbuilder.StarterActivity;
import com.jakewharton.rxbinding.widget.RxTextView;

import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by hollis on 8/8/16.
 */
public class CardListFragment extends Fragment{
    DeckSQliteOpenHelper db;
    EditText searchTextView;
    ListView listView;
    Button button;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_card_list, container, false);
        db = DeckSQliteOpenHelper.getInstance(getContext());
        button = (Button) v.findViewById(R.id.activity_main_go_to_downloads_button);
        searchTextView = (EditText) v.findViewById(R.id.activity_main_auto_complete_text);
        listView = (ListView) v.findViewById(R.id.activity_main_list_view);
        final Cursor cursor = db.getLegacyCards();
        final CardAdapter cursorAdapter = new CardAdapter(getContext(), cursor, 0);
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
                Intent intent = new Intent(getContext(), StarterActivity.class);
                startActivity(intent);
            }
        });
        return v;
    }
}
