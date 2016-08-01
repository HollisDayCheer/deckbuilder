package com.example.hollis.deckbuilder.Adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FilterQueryProvider;
import android.widget.TextView;

import com.example.hollis.deckbuilder.DatabaseHelper.DeckSQliteOpenHelper;

/**
 * Created by hollis on 8/1/16.
 */
public class CardNameAutofillAdapter extends CursorAdapter {
    public CardNameAutofillAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1,parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        AutoFillViewHolder autoFillViewHolder;
        if(view.getTag() == null){
             autoFillViewHolder = new AutoFillViewHolder(view);
            view.setTag(autoFillViewHolder);
        }else{
             autoFillViewHolder = (AutoFillViewHolder) view.getTag();
        }
        autoFillViewHolder.nameText.setText(cursor.getString(cursor.getColumnIndex(DeckSQliteOpenHelper.CardTable.COL_NAME)));
    }

    public class AutoFillViewHolder{
        TextView nameText;

        public AutoFillViewHolder(View v){
            nameText = (TextView) v.findViewById(android.R.id.text1);
        }
    }

    @Override
    public CharSequence convertToString(Cursor cursor) {
        return cursor.getString(cursor.getColumnIndex(DeckSQliteOpenHelper.CardTable.COL_NAME));
    }

    @Override
    public Cursor runQueryOnBackgroundThread(CharSequence constraint) {
        return DeckSQliteOpenHelper.getInstance(mContext).searchLegacyCardsByName(constraint);

    }
}
