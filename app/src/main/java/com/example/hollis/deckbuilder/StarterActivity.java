package com.example.hollis.deckbuilder;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hollis.deckbuilder.DatabaseHelper.DeckSQliteOpenHelper;
import com.example.hollis.deckbuilder.Models.Card;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by hollis on 7/20/16.
 */
public class StarterActivity extends AppCompatActivity {
    public static final String TAG = "StarterActivity";
    private DeckBrewInterface deckBrewInterface;
    private int curCard = 0;
    private DeckSQliteOpenHelper db;
    private ProgressBar mProgress;
    private Button updateButton;
    private TextView textView;
    private Observable<Card> cardStream;
    private boolean isStandard, isModern, isLegacy;
    private CheckBox standardBox, modernBox, legacyBox;
    private List<String> formats;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starter);
        setViews();
        createRetrofitInterface();
        db = DeckSQliteOpenHelper.getInstance(getApplicationContext());
        setOnClickListeners();

    }

    public void setViews(){
        standardBox = (CheckBox) findViewById(R.id.activity_starter_standard_check_box);
        modernBox = (CheckBox) findViewById(R.id.activity_starter_modern_check_box);
        legacyBox = (CheckBox) findViewById(R.id.activity_starter_legacy_check_box);
        textView = (TextView) findViewById(R.id.activity_starter_text);
        mProgress = (ProgressBar) findViewById(R.id.activity_starter_progress_bar);
        mProgress.setMax(Integer.valueOf(getResources().getString(R.string.total_cards)));
        updateButton = (Button) findViewById(R.id.activity_starter_button);

    }

    public void createRetrofitInterface(){
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl("https://api.deckbrew.com/")
                .build();
        deckBrewInterface = retrofit.create(DeckBrewInterface.class);

    }
    public void setFormatBooleans(){
        isStandard = standardBox.isChecked();
        isModern = modernBox.isChecked();
        isLegacy = legacyBox.isChecked();
    }

    public void setFormatList(){
        formats = new ArrayList<>(3);
        if(isStandard){
            formats.add("standard");
        }
        if(isModern){
            formats.add("modern");
        }
        if(isLegacy){
            formats.add("legacy");
        }
    }

    public void setOnClickListeners(){
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFormatBooleans();
                setFormatList();
                db.resetCards();
                createGlobalObservable();
                if(cardStream!= null) {
                    cardStream.observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Action1<Card>() {
                                @Override
                                public void call(Card card) {
                                    if (curCard % 50 == 0) {
                                            textView.setText(card.getName());
                                    }
                                }
                            });
                }
            }
        });
    }
    //Creates a global cardStream variable that is our finished set up global observable
    public void createGlobalObservable(){
        if(formats.size()>0) {
            cardStream = getCardObservable(0).concatMap(new Func1<Card[], Observable<Card>>() {
                @Override
                public Observable<Card> call(Card[] cards) {
                    return Observable.from(cards);
                }
            }).observeOn(AndroidSchedulers.mainThread())
                    .doOnNext(new Action1<Card>() {
                        @Override
                        public void call(Card card) {
                            curCard++;
                            mProgress.setProgress(curCard);
                            db.addCard(card);
                        }
                    }).doOnCompleted(new Action0() {
                        @Override
                        public void call() {
                            mProgress.setVisibility(View.INVISIBLE);
                            textView.setText("Complete!");
                        }
                    }).doOnSubscribe(new Action0() {
                        @Override
                        public void call() {
                            mProgress.setVisibility(View.VISIBLE);
                        }
                    }).doOnError(new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {
                            throwable.printStackTrace();
                        }
                    });
        }else{
            Toast.makeText(this, "Please Select a Format or Formats", Toast.LENGTH_LONG).show();
        }
    }

    // helper method that recursively creates our observable from api calls.
    public Observable<Card[]> getCardObservable(final int curPage){
        return deckBrewInterface.getCards(curPage, formats).doOnError(new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                throwable.printStackTrace();
            }
        })
                .subscribeOn(Schedulers.io())
                .flatMap(new Func1<Card[], Observable<Card[]>>() {
                    @Override
                    public Observable<Card[]> call(Card[] cards) {
                        if (cards != null) {
                            if (cards.length == 0) {
                                return Observable.just(cards);
                            }
                            return Observable.just(cards).concatWith(getCardObservable(curPage + 1));
                        }else{
                            return Observable.just(cards);
                        }
                    }
                })
                .doOnError(new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                    }
                });
    }
}
