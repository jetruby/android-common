package com.jetruby.common.rxcountries;

import com.fasterxml.jackson.core.JsonFactory;

import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;

public class RxCountries {

    private static WeakReference<List<Country>> countries;

    private RxCountries() {
    }

    public static Observable<Country> countryObservable() {
        return countryListObservable()
                .flatMap(Observable::fromIterable);
    }

    public static Flowable<Country> countryFlowable() {
        return countryListFlowable()
                .flatMap(Flowable::fromIterable);
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

    public static List<Country> countryList() throws IOException {
        synchronized (RxCountries.class) {
            if (countries == null || countries.get() == null) {
                InputStreamReader reader = new InputStreamReader(
                        RxCountries.class.getResourceAsStream("/countries.json"));

                countries = new WeakReference<>(new CountryJsonMapper()
                        .parseList(new JsonFactory().createParser(reader)));
            }
        }
        return countries.get();
    }

}
