package com.jetruby.android.common;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.jetruby.common.rxcountries.Country;
import com.jetruby.common.rxcountries.RxCountries;
import com.jetruby.flagsres.FlagAssets;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RxCountries.countryListSingle()
                .flatMapObservable(Observable::fromIterable)
                .map(Country::toString)
                .subscribeOn(Schedulers.io())
                .subscribe(s -> Log.d("AZA", s));

        RxCountries.countryListSingle()
                .flatMapObservable(Observable::fromIterable)
                .subscribeOn(Schedulers.io())
                .subscribe(country -> {
                    Log.d("AZA", country.getCountryCode());
                    Log.d("AZA", "id:" + FlagAssets.getFlagResIdByCountryCode(MainActivity.this, country.getCountryCode()));
                });

    }

}
