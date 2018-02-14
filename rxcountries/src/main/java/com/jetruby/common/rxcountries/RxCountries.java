package com.jetruby.common.rxcountries;

import com.fasterxml.jackson.core.JsonFactory;

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
                .map(reader -> new CountryJsonMapper()
                        .parseList(new JsonFactory().createParser(reader)));
    }

}
