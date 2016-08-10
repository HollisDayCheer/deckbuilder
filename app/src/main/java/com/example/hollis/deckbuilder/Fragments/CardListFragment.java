package com.example.hollis.deckbuilder.Fragments;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.hollis.deckbuilder.Adapters.CardAdapter;
import com.example.hollis.deckbuilder.DatabaseHelper.DeckSQliteOpenHelper;
import com.example.hollis.deckbuilder.R;
import com.example.hollis.deckbuilder.Models.SearchProperties;
import com.example.hollis.deckbuilder.StarterActivity;
import com.jakewharton.rxbinding.widget.RxTextView;

import java.util.concurrent.TimeUnit;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by hollis on 8/8/16.
 */
public class CardListFragment extends Fragment{
    SearchProperties searchProperties = new SearchProperties();
    DeckSQliteOpenHelper db;
    EditText searchTextView;
    ListView listView;
    Button button;
    CardAdapter cardAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        searchProperties = new SearchProperties();
        searchProperties.setName(true);
        View v = inflater.inflate(R.layout.fragment_card_list, container, false);
        setViews(v);
        setAdapter();
        createOnTextChangedObservable();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), StarterActivity.class);
                startActivity(intent);
            }
        });
        return v;
    }

    public void setSearchProperties(SearchProperties searchProperties){
        if(searchProperties == null){
            this.searchProperties = new SearchProperties();
        }
        else{
            this.searchProperties = searchProperties;
        }
        if(searchTextView!=null){
            String query = searchTextView.getText().toString();
            cardAdapter.swapCursor(db.searchLegacyCards(query, searchProperties));
        }
    }

    public void setViews(View v){
        button = (Button) v.findViewById(R.id.activity_main_go_to_downloads_button);
        searchTextView = (EditText) v.findViewById(R.id.activity_main_auto_complete_text);
        listView = (ListView) v.findViewById(R.id.activity_main_list_view);
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public void setAdapter(){
        db = DeckSQliteOpenHelper.getInstance(getContext());
        Cursor cursor = db.searchLegacyCards("",searchProperties);
        cardAdapter = new CardAdapter(getContext(), cursor, 0);
        listView.setAdapter(cardAdapter);
    }

    public void createOnTextChangedObservable(){
        rx.Observable<CharSequence> editTextStream = RxTextView.textChanges(searchTextView).debounce(300, TimeUnit.MILLISECONDS);
        editTextStream.observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<CharSequence>() {
            @Override
            public void call(CharSequence charSequence) {
                if(charSequence.toString().equals("")) {
                    cardAdapter.swapCursor(db.searchLegacyCards("", searchProperties));
                }else{
                    cardAdapter.swapCursor(db.searchLegacyCards(charSequence, searchProperties));
                }
            }
        });
    }



}
