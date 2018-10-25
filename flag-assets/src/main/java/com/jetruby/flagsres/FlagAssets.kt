package com.jetruby.flagsres

import android.content.Context

fun getFlagResIdByCountryCode(context: Context, countryCode: String): Int =
  context.resources.getIdentifier(countryCodeToResName(countryCode), "mipmap", context.packageName)

internal fun countryCodeToResName(countryCode: String): String = "${countryCode.toLowerCase()}_flag"