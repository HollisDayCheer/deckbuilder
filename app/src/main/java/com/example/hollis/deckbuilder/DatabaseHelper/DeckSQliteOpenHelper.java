package com.example.hollis.deckbuilder.DatabaseHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.hollis.deckbuilder.Models.Card;
import com.example.hollis.deckbuilder.SearchProperties;

/**
 * Created by hollis on 7/20/16.
 */
public class DeckSQliteOpenHelper extends SQLiteOpenHelper {
    private static DeckSQliteOpenHelper instance;
    public static String DATABASE_NAME = "SpicyBrewDatabase";
    public static int DATABASE_VERSION = 1;

    private DeckSQliteOpenHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static synchronized DeckSQliteOpenHelper getInstance(Context context){
        if(instance == null){
            instance = new DeckSQliteOpenHelper(context.getApplicationContext());
        }
        return instance;
    }

    public static class CardTable{
        public static String TABLE_NAME = "CARD_TABLE";
        public static  String COL_NAME = "NAME";
        public static String COL_API_ID = "api_id";
        public static String COL_DB_ID = "_id";
        public static String COL_CMC = "CMC";
        public static String COL_COST = "COST";
        public static String COL_TEXT = "CARD_TEXT";
        public static String COL_IMAGE_URL = "IMAGE_URL";
        public static String COL_TYPES = "TYPES";
        public static String COL_COLORS = "COLOR";
        public static String COL_FORMATS = "FORMAT";

        public static String CREATE_CARD_TABLE = "CREATE TABLE " + TABLE_NAME + "(" +
                COL_NAME +  " TEXT, " +
                COL_API_ID +" TEXT, " +
                COL_DB_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_CMC +" INTEGER, " +
                COL_COST +" TEXT, " +
                COL_TEXT +" TEXT, " +
                COL_IMAGE_URL +" TEXT, " +
                COL_TYPES +" TEXT, " +
                COL_FORMATS + " TEXT, " +
                COL_COLORS +" TEXT)";

        public static String[] COLUMNS = {COL_NAME,
                COL_API_ID,
                COL_DB_ID,
                COL_CMC,
                COL_COST,
                COL_TEXT,
                COL_IMAGE_URL,
                COL_TYPES,
                COL_FORMATS,
                COL_COLORS};

    }

    public static class DeckTable{
        public static String DECK_TABLE_NAME = "DECK_TABLE";
        public static String COL_DECK_ID = "_id";
        public static String COL_DECK_FORMAT = "DECK_FORMAT";
        public static  String COL_DECK_NAME = "DECK_NAME";
        public static  String COL_DECK_COLORS = "DECK_COLORS";
        public static String CREATE_DECK_TABLE = "CREATE TABLE " + DECK_TABLE_NAME + "(" +
                COL_DECK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_DECK_NAME + " TEXT, " +
                COL_DECK_FORMAT + " TEXT, " +
                COL_DECK_COLORS + " TEXT)";
    }




    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DeckTable.CREATE_DECK_TABLE);
        db.execSQL(CardTable.CREATE_CARD_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    public void addCard(final Card card){

                ContentValues values = new ContentValues();
                values.put(CardTable.COL_NAME, card.getName());
                values.put(CardTable.COL_API_ID, card.getId());
                values.put(CardTable.COL_CMC, card.getCmc());
                values.put(CardTable.COL_COST, card.getCost());
                values.put(CardTable.COL_TEXT, card.getText());
                values.put(CardTable.COL_IMAGE_URL, card.getEditions()[0].getImage_url());
                String typeString = "";
                for(int i = 0; i<card.getTypes().length; i++){
                    typeString+=card.getTypes()[i] + ",";
                }
                String colorString ="";
                if(card.getColors()!= null) {
                    for (int j = 0; j < card.getColors().length; j++) {
                        colorString += card.getColors()[j] + ",";
                    }
                }else {
                    colorString = "colorless";
                }
                String allFormats = "";
                //intentionally not else ifs, want to go through each individually
                if(card.getFormats().getStandard() != null){
                    allFormats += "standard";
                }
                if(card.getFormats().getModern() != null){
                    allFormats += "modern";
                }
                if(card.getFormats().getLegacy() != null){
                    allFormats += "legacy";
                }
                values.put(CardTable.COL_TYPES, typeString);
                values.put(CardTable.COL_COLORS, colorString);
                values.put(CardTable.COL_FORMATS, allFormats);
                SQLiteDatabase db = getWritableDatabase();
                db.insert(CardTable.TABLE_NAME,null, values);
                db.close();

    }

    public void resetCards(){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(CardTable.TABLE_NAME, null, null);
    }

    public Cursor getLegacyCards(){
        SQLiteDatabase db = getReadableDatabase();
        String sqliteString = "SELECT * FROM " + CardTable.TABLE_NAME
                + " ORDER BY " + CardTable.COL_NAME;
        return db.rawQuery(sqliteString, null);
    }

    public Cursor searchLegacyCardsByName(String search, SearchProperties searchProperties){
        String query = search.toString().trim();
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery(searchProperties.getSqliteQuery(query), null);
    }
}
