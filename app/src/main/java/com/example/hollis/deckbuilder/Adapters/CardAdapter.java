package com.example.hollis.deckbuilder.Adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hollis.deckbuilder.DatabaseHelper.DeckSQliteOpenHelper;
import com.example.hollis.deckbuilder.Models.Card;
import com.example.hollis.deckbuilder.Models.Deck;
import com.example.hollis.deckbuilder.R;
import com.squareup.picasso.Picasso;
import com.venmo.cursor.IterableCursor;
import com.venmo.cursor.IterableCursorAdapter;

/**
 * Created by hollis on 7/29/16.
 */
public class CardAdapter extends CursorAdapter {
    public CardAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.card_adapter_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        CardViewHolder cardViewHolder;
        if(view.getTag() == null){
            cardViewHolder = new CardViewHolder(view);
            view.setTag(cardViewHolder);
        }else{
            cardViewHolder = (CardViewHolder) view.getTag();
        }

        cardViewHolder.cardText.setText(cursor.getString(cursor.getColumnIndex(DeckSQliteOpenHelper.CardTable.COL_TEXT)));
        cardViewHolder.nameText.setText(cursor.getString(cursor.getColumnIndex(DeckSQliteOpenHelper.CardTable.COL_NAME)));
        Picasso.with(context).load(cursor.getString(cursor.getColumnIndex(DeckSQliteOpenHelper.CardTable.COL_IMAGE_URL))).into(cardViewHolder.cardImage);
    }

    private class CardViewHolder{
        ImageView cardImage;
        TextView cardText,nameText;
        public CardViewHolder(View v){
            cardImage =(ImageView) v.findViewById(R.id.card_adapter_item_image);
            nameText = (TextView) v.findViewById(R.id.card_adapter_item_card_name);
            cardText = (TextView) v . findViewById(R.id.card_adapter_item_card_text);
        }
    }
}
