package com.example.hollis.deckbuilder.Models;

import com.example.hollis.deckbuilder.Models.Card;

import java.util.List;


/**
 * Created by hollis on 7/20/16.
 */
public class Deck {
    List<Card> cardList;
    String name;
    String[] colors;

    public List<Card> getCardList() {
        return cardList;
    }

    public void setCardList(List<Card> cardList) {
        this.cardList = cardList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getColors() {
        return colors;
    }

    public void setColors(String[] colors) {
        this.colors = colors;
    }
}
