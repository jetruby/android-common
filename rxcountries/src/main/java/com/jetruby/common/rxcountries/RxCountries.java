package com.jetruby.common.rxcountries;

import com.fasterxml.jackson.core.JsonFactory;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;

public class RxCountries {

    private RxCountries() {
    }

    public static Single<List<Country>> countryListSingle() {
        return Single.fromCallable(RxCountries::countryList);
    }

    public static Observable<List<Country>> countryListObservable() {
        return Observable.fromCallable(RxCountries::countryList);
    }

    public static Flowable<List<Country>> countryListFlowable() {
        return Flowable.fromCallable(RxCountries::countryList);
    }

    public static List<Country> countryList() {
        InputStreamReader reader = new InputStreamReader(
                RxCountries.class.getResourceAsStream("/countries.json"));
        try {
            return new CountryJsonMapper()
                    .parseList(new JsonFactory().createParser(reader));
        } catch (IOException e) {
            return Collections.emptyList();
        }
    }

}
