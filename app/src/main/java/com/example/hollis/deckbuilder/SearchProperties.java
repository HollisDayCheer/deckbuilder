package com.example.hollis.deckbuilder;

import com.example.hollis.deckbuilder.DatabaseHelper.DeckSQliteOpenHelper;

/**
 * Created by hollis on 8/9/16.
 */
public class SearchProperties {
    boolean red, green, blue, black, white;
    boolean name, type, subtype, text;
    boolean standard, modern, legacy;
    boolean artifact, creature, enchantment, instant, planeswalker, land, sorcery;

    public boolean isRed() {
        return red;
    }

    public boolean isGreen() {
        return green;
    }

    public boolean isBlue() {
        return blue;
    }

    public boolean isBlack() {
        return black;
    }

    public boolean isWhite() {
        return white;
    }

    public boolean isName() {
        return name;
    }

    public void setName(boolean name) {
        this.name = name;
    }

    public boolean isType() {
        return type;
    }

    public boolean isSubtype() {
        return subtype;
    }

    public boolean isText() {
        return text;
    }

    public void setText(boolean text) {
        this.text = text;
    }

    public boolean isStandard() {
        return standard;
    }

    public boolean isModern() {
        return modern;
    }


    public boolean isLegacy() {
        return legacy;
    }

    public boolean isArtifact() {
        return artifact;
    }

    public boolean isCreature() {
        return creature;
    }

    public boolean isEnchantment() {
        return enchantment;
    }

    public boolean isInstant() {
        return instant;
    }

    public boolean isPlaneswalker() {
        return planeswalker;
    }

    public boolean isLand() {
        return land;
    }

    public boolean isSorcery() {
        return sorcery;
    }

    public String getSqliteQuery(String query){
        String sqliteString = "SELECT * FROM " + DeckSQliteOpenHelper.CardTable.TABLE_NAME + " WHERE (";
        //Search Formats
        if(isStandard()){
            sqliteString += DeckSQliteOpenHelper.CardTable.COL_FORMATS + " LIKE '%standard%'";
        }
        else if(isModern()){
            sqliteString += DeckSQliteOpenHelper.CardTable.COL_FORMATS + " LIKE '%modern%'";
        }else {
            sqliteString += DeckSQliteOpenHelper.CardTable.COL_FORMATS + " LIKE '%legacy%'";
        }
        sqliteString += " ) ";
        String conjunction = "";
        String colorSqliteString = "";
        //Search Colors
        if(isWhite()){
            colorSqliteString += conjunction + DeckSQliteOpenHelper.CardTable.COL_COLORS + " LIKE '%white%' ";
            conjunction = " OR ";
        }
        if(isBlue()){
            colorSqliteString += conjunction  + DeckSQliteOpenHelper.CardTable.COL_COLORS + " LIKE '%blue%' ";
            conjunction = " OR ";
        }
        if(isBlack()){
            colorSqliteString += conjunction  + DeckSQliteOpenHelper.CardTable.COL_COLORS + " LIKE '%black%' ";
            conjunction = " OR ";
        }
        if(isRed()){
            colorSqliteString += conjunction + DeckSQliteOpenHelper.CardTable.COL_COLORS + " LIKE '%red%' ";
            conjunction = " OR ";
        }
        if(isGreen()){
            colorSqliteString +=conjunction + DeckSQliteOpenHelper.CardTable.COL_COLORS + " LIKE '%green%' ";
        }
        if(!colorSqliteString.isEmpty()){
            colorSqliteString +=")";
            sqliteString += "AND (" + colorSqliteString;
        }

        //Types
        String typeSqliteString = "";
        conjunction = "";
        if(isArtifact()){
            typeSqliteString += conjunction + DeckSQliteOpenHelper.CardTable.COL_TYPES + " LIKE '%artifact%' ";
            conjunction = "OR";
        }
        if(isCreature()){
            typeSqliteString += conjunction +  DeckSQliteOpenHelper.CardTable.COL_TYPES + " LIKE '%creature%' ";
            conjunction = "OR";
        }
        if(isEnchantment()){
            typeSqliteString += conjunction +  DeckSQliteOpenHelper.CardTable.COL_TYPES + " LIKE '%enchantment%' ";
            conjunction = "OR";
        }if(isLand()){
            typeSqliteString += conjunction +  DeckSQliteOpenHelper.CardTable.COL_TYPES + " LIKE '%land%' ";
            conjunction = "OR";
        }if(isPlaneswalker()){
            typeSqliteString+= conjunction +  DeckSQliteOpenHelper.CardTable.COL_TYPES + " LIKE '%planeswalker%' ";
            conjunction = "OR";
        }if(isInstant()){
            typeSqliteString += conjunction +  DeckSQliteOpenHelper.CardTable.COL_TYPES + " LIKE '%instant%' ";
            conjunction = "OR";
        }if(isSorcery()){
            typeSqliteString += conjunction +  DeckSQliteOpenHelper.CardTable.COL_TYPES + " LIKE '%sorcery%' ";
        }
        if(!typeSqliteString.isEmpty()){
           typeSqliteString += ")";
            sqliteString += "AND (" + typeSqliteString;
        }

        //SearchOptions
        String optionsSqliteString = "";
        conjunction = "";
        if(isName()){
            optionsSqliteString += DeckSQliteOpenHelper.CardTable.COL_NAME + " LIKE '%" + query + "%' ";
            conjunction = " OR ";
        }
        if(isText()){
            optionsSqliteString += conjunction + DeckSQliteOpenHelper.CardTable.COL_TEXT + " LIKE '%" + query + "%' ";
            conjunction = " OR ";
        }
        if(!optionsSqliteString.isEmpty()){
            optionsSqliteString += ")";
            sqliteString += " AND (" + optionsSqliteString;
        }
        sqliteString += " ORDER BY " + DeckSQliteOpenHelper.CardTable.COL_NAME;
        return sqliteString;
    }

    public void setProperty(String property, boolean value){
        if(property.equalsIgnoreCase("standard")){
            this.standard = value;
        }else if(property.equalsIgnoreCase("modern")){
            this.modern=value;
        }else if(property.equalsIgnoreCase("legacy")){
            this.legacy=value;
        }else if(property.equalsIgnoreCase("name")){
            this.name=value;
        }else if(property.equalsIgnoreCase("type")){
            this.type=value;
        }else if(property.equalsIgnoreCase("text")){
            this.text=value;
        }else if(property.equalsIgnoreCase("white")){
            this.white=value;
        }else if(property.equalsIgnoreCase("blue")){
            this.blue=value;
        }else if(property.equalsIgnoreCase("black")){
            this.black=value;
        }else if(property.equalsIgnoreCase("red")){
            this.red=value;
        }else if(property.equalsIgnoreCase("green")){
            this.green=value;
        }else if(property.equalsIgnoreCase("artifact")){
            this.artifact=value;
        }else if(property.equalsIgnoreCase("creature")){
            this.creature=value;
        }else if(property.equalsIgnoreCase("enchantment")){
            this.enchantment=value;
        }else if(property.equalsIgnoreCase("planeswalker")){
            this.planeswalker=value;
        }else if(property.equalsIgnoreCase("land")){
            this.land=value;
        }else if(property.equalsIgnoreCase("instant")){
            this.instant=value;
        }else if(property.equalsIgnoreCase("sorcery")){
            this.sorcery=value;
        }
    }
}
