package com.example.hollis.deckbuilder;

import com.example.hollis.deckbuilder.Models.Card;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by hollis on 7/20/16.
 */
public interface DeckBrewInterface {

    @GET("mtg/cards")
    Observable<Card[]> getCards(@Query("page") int pageNum, @Query("format") List<String> formats);
}
