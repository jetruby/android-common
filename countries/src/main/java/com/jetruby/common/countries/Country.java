package com.jetruby.common.countries;

import java.util.Comparator;

/**
 * Created by anton_azaryan on 2/12/18.
 */

public class Country implements Comparable<Country> {

    String name;
    String countryCode;
    String phoneCode;

    public static Country clone(Country from) {
        Country newCountry = new Country();
        newCountry.name = from.name;
        newCountry.countryCode = from.countryCode;
        newCountry.phoneCode = from.phoneCode;
        return newCountry;
    }

    public static Country china() {
        Country newCountry = new Country();
        newCountry.name = "China";
        newCountry.countryCode = "CN";
        newCountry.phoneCode = "86";
        return newCountry;
    }

    public static Country usa() {
        Country newCountry = new Country();
        newCountry.name = "United States";
        newCountry.countryCode = "US";
        newCountry.phoneCode = "1";
        return newCountry;
    }

    public static Comparator<Country> countryCodeComparator() {
        return (c1, c2) -> c1.getCountryCode().compareTo(c2.getCountryCode());
    }

    Country() {
    }

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

    @Override
    public int compareTo(Country country) {
        return name.compareTo(country.getName());
    }
}
