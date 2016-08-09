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

    public void setRed(boolean red) {
        this.red = red;
    }

    public boolean isGreen() {
        return green;
    }

    public void setGreen(boolean green) {
        this.green = green;
    }

    public boolean isBlue() {
        return blue;
    }

    public void setBlue(boolean blue) {
        this.blue = blue;
    }

    public boolean isBlack() {
        return black;
    }

    public void setBlack(boolean black) {
        this.black = black;
    }

    public boolean isWhite() {
        return white;
    }

    public void setWhite(boolean white) {
        this.white = white;
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

    public void setType(boolean type) {
        this.type = type;
    }

    public boolean isSubtype() {
        return subtype;
    }

    public void setSubtype(boolean subtype) {
        this.subtype = subtype;
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

    public void setStandard(boolean standard) {
        this.standard = standard;
    }

    public boolean isModern() {
        return modern;
    }

    public void setModern(boolean modern) {
        this.modern = modern;
    }

    public boolean isLegacy() {
        return legacy;
    }

    public void setLegacy(boolean legacy) {
        this.legacy = legacy;
    }

    public boolean isArtifact() {
        return artifact;
    }

    public void setArtifact(boolean artifact) {
        this.artifact = artifact;
    }

    public boolean isCreature() {
        return creature;
    }

    public void setCreature(boolean creature) {
        this.creature = creature;
    }

    public boolean isEnchantment() {
        return enchantment;
    }

    public void setEnchantment(boolean enchantment) {
        this.enchantment = enchantment;
    }

    public boolean isInstant() {
        return instant;
    }

    public void setInstant(boolean instant) {
        this.instant = instant;
    }

    public boolean isPlaneswalker() {
        return planeswalker;
    }

    public void setPlaneswalker(boolean planeswalker) {
        this.planeswalker = planeswalker;
    }

    public boolean isLand() {
        return land;
    }

    public void setLand(boolean land) {
        this.land = land;
    }

    public boolean isSorcery() {
        return sorcery;
    }

    public void setSorcery(boolean sorcery) {
        this.sorcery = sorcery;
    }

    public String getSqliteQuery(String query){
        String sqliteString = "SELECT * FROM " + DeckSQliteOpenHelper.CardTable.TABLE_NAME + " WHERE ";
        //Search Formats
        if(isStandard()){
            sqliteString = "FORMAT LIKE %";
        }
        return null;
    }
}
