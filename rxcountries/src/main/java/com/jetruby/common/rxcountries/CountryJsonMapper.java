package com.jetruby.common.rxcountries;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

final class CountryJsonMapper {

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
