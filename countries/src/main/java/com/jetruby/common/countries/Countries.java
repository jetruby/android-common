package com.jetruby.common.countries;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Countries {

    private static WeakReference<List<Country>> countriesInstance;

    private Countries() {
    }

    public static List<Country> countryList() throws IOException {
        WeakReference<List<Country>> countries = countriesInstance;
        if (countries == null || countries.get() == null) {
            synchronized (Countries.class) {
                countries = countriesInstance;
                if (countries == null || countries.get() == null) {
                    InputStreamReader reader = new InputStreamReader(
                            Countries.class.getResourceAsStream("/countries.json"));

                    List<Country> immutable = Collections.unmodifiableList(new CountryJsonMapper()
                            .parseList(new JsonFactory().createParser(reader)));

                    countries = new WeakReference<>(immutable);
                    countriesInstance = countries;
                }
            }
        }
        return countries.get();
    }

    static final class CountryJsonMapper {

        Country parse(JsonParser jsonParser) throws IOException {
            Country instance = new Country();
            if (jsonParser.getCurrentToken() == null) {
                jsonParser.nextToken();
            }
            if (jsonParser.getCurrentToken() != JsonToken.START_OBJECT) {
                jsonParser.skipChildren();
                return null;
            }
            while (jsonParser.nextToken() != JsonToken.END_OBJECT) {
                String fieldName = jsonParser.getCurrentName();
                jsonParser.nextToken();
                parseField(instance, fieldName, jsonParser);
                jsonParser.skipChildren();
            }
            return instance;
        }

        void parseField(Country instance, String fieldName, JsonParser jsonParser) throws
                IOException {
            if ("country_code".equals(fieldName)) {
                instance.countryCode = jsonParser.getValueAsString(null);
            } else if ("name".equals(fieldName)) {
                instance.name = jsonParser.getValueAsString(null);
            } else if ("phone_code".equals(fieldName)) {
                instance.phoneCode = jsonParser.getValueAsString(null);
            }
        }

        List<Country> parseList(JsonParser jsonParser) throws IOException {
            jsonParser.nextToken();
            List<Country> list = new ArrayList<>();
            if (jsonParser.getCurrentToken() == JsonToken.START_ARRAY) {
                while (jsonParser.nextToken() != JsonToken.END_ARRAY) {
                    list.add(parse(jsonParser));
                }
            }
            return list;
        }
    }
}
