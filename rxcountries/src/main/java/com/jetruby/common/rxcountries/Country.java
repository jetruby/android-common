package com.jetruby.common.rxcountries;

import com.google.gson.annotations.SerializedName;

/**
 * Created by anton_azaryan on 2/12/18.
 */

public class Country {

    String name;

    @SerializedName("country_code")
    String countryCode;

    @SerializedName("phone_code")
    String phoneCode;

    public Country(String code, String dialCode, String name) {
        this.countryCode = code;
        this.phoneCode = dialCode;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public String getPhoneCode() {
        return phoneCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Country)) return false;

        Country country = (Country) o;

        if (!countryCode.equals(country.countryCode)) return false;
        if (!phoneCode.equals(country.phoneCode)) return false;
        return name.equals(country.name);
    }

    @Override
    public int hashCode() {
        int result = countryCode.hashCode();
        result = 31 * result + phoneCode.hashCode();
        result = 31 * result + name.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Country{" +
                "countryCode='" + countryCode + '\'' +
                ", phoneCode='" + phoneCode + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
