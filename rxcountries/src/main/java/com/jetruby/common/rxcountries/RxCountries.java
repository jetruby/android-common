package com.jetruby.common.rxcountries;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStreamReader;
import java.util.List;

import io.reactivex.Single;

public class RxCountries {

    private RxCountries() {
    }

    public static Single<List<Country>> countryList() {
        return Single.just("/countries.json")
                .map(RxCountries.class::getResourceAsStream)
                .map(InputStreamReader::new)
                .map(reader -> new Gson().fromJson(reader, new TypeToken<List<Country>>() {
                }.getType()));
    }

//    public static Observable<List<Country>> search(Observable<String> searchStream) {
//        Single<List<Country>> countryStream = countryList().cache();
//        return searchStream.switchMap(s -> countryStream.flatMapObservable(countries ->
//                Observable.fromIterable(countries)
//                        .filter(country -> country.name().startsWith(s))
//                        .toList()
//                        .toObservable()));
//    }

}
