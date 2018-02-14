package com.jetruby.android.common;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.jetruby.common.rxcountries.Country;
import com.jetruby.common.rxcountries.RxCountries;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RxCountries.countryList()
                .flatMapObservable(Observable::fromIterable)
                .map(Country::toString)
                .subscribeOn(Schedulers.io())
                .subscribe(s -> Log.d("AZA", s));


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                RxCountries.countryList()
                        .flatMapObservable(Observable::fromIterable)
                        .map(Country::toString)
                        .subscribeOn(Schedulers.io())
                        .subscribe(s -> Log.d("AZA", s));
            }
        }, 3000);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                RxCountries.countryList()
                        .flatMapObservable(Observable::fromIterable)
                        .map(Country::toString)
                        .subscribeOn(Schedulers.io())
                        .subscribe(s -> Log.d("AZA", s));
            }
        }, 6000);
    }

}
