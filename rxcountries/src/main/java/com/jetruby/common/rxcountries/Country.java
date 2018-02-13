package com.jetruby.common.rxcountries;

import com.google.gson.annotations.SerializedName;

/**
 * Created by anton_azaryan on 2/12/18.
 */

public class Country {

    String code;

    @SerializedName("dial_code")
    String dialCode;

    String name;

    public Country(String code, String dialCode, String name) {
        this.code = code;
        this.dialCode = dialCode;
        this.name = name;
    }

    public String code() {
        return code;
    }

    public String dialCode() {
        return dialCode;
    }

    public String name() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Country)) return false;

        Country country = (Country) o;

        if (!code.equals(country.code)) return false;
        if (!dialCode.equals(country.dialCode)) return false;
        return name.equals(country.name);
    }

    @Override
    public int hashCode() {
        int result = code.hashCode();
        result = 31 * result + dialCode.hashCode();
        result = 31 * result + name.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Country{" +
                "code='" + code + '\'' +
                ", dialCode='" + dialCode + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
