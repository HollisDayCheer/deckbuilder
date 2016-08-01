package com.example.hollis.deckbuilder.Models;


/**
 * Created by hollis on 7/20/16.
 */
public class Card  {
    String name, id, cost, text, image_url;
    int cmc;
    String[] types, colors;
    Formats formats;
    Edition[] editions;

    public Card(String name, String id, String cost, String text, String image_url, int cmc, String[] types, String[] colors) {
        this.name = name;
        this.id = id;
        this.cost = cost;
        this.text = text;
        this.image_url = image_url;
        this.cmc = cmc;
        this.types = types;
        this.colors = colors;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public int getCmc() {
        return cmc;
    }

    public void setCmc(int cmc) {
        this.cmc = cmc;
    }

    public String[] getTypes() {
        return types;
    }

    public void setTypes(String[] types) {
        this.types = types;
    }

    public String[] getColors() {
        return colors;
    }

    public void setColors(String[] colors) {
        this.colors = colors;
    }

    public Formats getFormats() {
        return formats;
    }

    public void setFormats(Formats formats) {
        this.formats = formats;
    }

    public Edition[] getEditions() {
        return editions;
    }

    public void setEditions(Edition[] editions) {
        this.editions = editions;
    }
}
