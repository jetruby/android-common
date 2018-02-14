package com.jetruby.flagsres;

import android.content.Context;
import android.content.res.Resources;

import com.jetruby.common.rxcountries.Country;

/**
 * Created by anton_azaryan on 2/14/18.
 */

public class FlagAssets {

    private FlagAssets() {
    }

    public static int getFlagResIdByCountry(Context context, Country country) {
        return getFlagResIdByCountryCode(context, country.getCountryCode());
    }

    public static int getFlagResIdByCountryCode(Context context, String countryCode) {
        final Resources res = context.getResources();
        return res.getIdentifier(countryCodeToResName(countryCode),
                "mipmap",
                context.getPackageName());
    }

    static String countryCodeToResName(String countryCode) {
        return countryCode.toLowerCase() + "_flag";
    }

}
