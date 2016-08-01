package com.example.hollis.deckbuilder.DatabaseHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.MergeCursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.hollis.deckbuilder.Models.Card;

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
        public static String STANDARD_PREFIX = "STANDARD_";
        public static String MODERN_PREFIX = "MODERN_";
        public static String LEGACY_PREFIX = "LEGACY";

        public static String TABLE_NAME = "TABLE";

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

        public static String CREATE_STANDARD_TABLE = "CREATE TABLE " + STANDARD_PREFIX + TABLE_NAME + "(" +
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

        public static String CREATE_MODERN_TABLE = "CREATE TABLE " + MODERN_PREFIX + TABLE_NAME + "(" +
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

        public static String CREATE_LEGACY_TABLE = "CREATE TABLE " + LEGACY_PREFIX + TABLE_NAME + "(" +
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
        db.execSQL(CardTable.CREATE_STANDARD_TABLE);
        db.execSQL(CardTable.CREATE_MODERN_TABLE);
        db.execSQL(CardTable.CREATE_LEGACY_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    public void addCard(final Card card, final String format){

                String formatPrefix = "";
        //intentionally not else-ifs in order to concatenate my all formats
        //This creates the allFormats String for my later db insertion
                if(format.contains("legacy")){
                    formatPrefix = CardTable.LEGACY_PREFIX;
                }if(format.contains("modern")){
                    formatPrefix = CardTable.MODERN_PREFIX;
                }if(format.contains("standard")){
                    formatPrefix = CardTable.STANDARD_PREFIX;
                }
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
                values.put(CardTable.COL_TYPES, typeString);
                values.put(CardTable.COL_COLORS, colorString);
                values.put(CardTable.COL_FORMATS, format);
                SQLiteDatabase db = getWritableDatabase();
                db.insert(formatPrefix+CardTable.TABLE_NAME,null, values);
                db.close();



    }

    public void resetCards(){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(CardTable.STANDARD_PREFIX + CardTable.TABLE_NAME, null, null);
        db.delete(CardTable.MODERN_PREFIX + CardTable.TABLE_NAME, null, null);
        db.delete(CardTable.LEGACY_PREFIX + CardTable.TABLE_NAME, null, null);
    }

    public Cursor getStandardCards(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor =  db.query(CardTable.STANDARD_PREFIX + CardTable.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null,
                null
        );
        Log.d("SQLITE OPEN HELPER ", "results size: " + cursor.getCount());
        return cursor;
    }

    public Cursor getModernCards(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor standardCards = db.query(CardTable.STANDARD_PREFIX + CardTable.TABLE_NAME,
                null,
                CardTable.COL_FORMATS + " LIKE ?",
                new String[]{"%modern%"},
                null,
                null,
                null
                );
        Cursor modernCards = db.query(CardTable.MODERN_PREFIX + CardTable.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null);
        MergeCursor mergeCursor = new MergeCursor(new Cursor[]{standardCards, modernCards});
        return mergeCursor;
    }
    public Cursor getLegacyCards(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor standardCards = db.query(CardTable.STANDARD_PREFIX + CardTable.TABLE_NAME,
                null,
                CardTable.COL_FORMATS + " LIKE ?",
                new String[]{"%legacy%"},
                null,
                null,
                null
        );
        Log.d("DeckSqliteOpen", "Standard cards is: " + standardCards.getCount());
        Cursor modernCards = db.query(CardTable.MODERN_PREFIX + CardTable.TABLE_NAME,
                null,
                CardTable.COL_FORMATS + " LIKE ?",
                new String[]{"%legacy%"},
                null,
                null,
                null);
        Cursor legacyCards = db.query(CardTable.LEGACY_PREFIX + CardTable.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null);
        MergeCursor mergeCursor = new MergeCursor(new Cursor[]{standardCards, modernCards, legacyCards});
        return mergeCursor;
    }

    public Cursor searchLegacyCardsByName(CharSequence query){
        SQLiteDatabase db = getReadableDatabase();
        Log.d("sqliteopenhelper", "%"+query+"%");
        Cursor standardCards = db.query(CardTable.STANDARD_PREFIX + CardTable.TABLE_NAME,
                null,
                CardTable.COL_FORMATS + " LIKE ? AND " + CardTable.COL_NAME + " LIKE ?",
                new String[]{"%legacy%", "%" + query + "%"},
                null,
                null,
                null
        );
        Cursor modernCards = db.query(CardTable.MODERN_PREFIX + CardTable.TABLE_NAME,
                null,
                CardTable.COL_FORMATS + " LIKE ? AND " + CardTable.COL_NAME + " LIKE ?",
                new String[]{"%legacy%", "%" + query + "%"},
                null,
                null,
                null);
        Cursor legacyCards = db.query(CardTable.LEGACY_PREFIX + CardTable.TABLE_NAME,
                null,
                CardTable.COL_NAME + " LIKE ?",
                new String[]{"%" + query + "%"},
                null,
                null,
                null);
        Log.d("Searching database", "num standard cards: " + standardCards.getCount());
        MergeCursor mergeCursor = new MergeCursor(new Cursor[]{standardCards, modernCards, legacyCards});
        return mergeCursor;
    }
}
