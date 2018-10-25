package com.jetruby.common.countries

import android.content.Context
import com.fasterxml.jackson.core.JsonFactory
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.core.JsonToken
import com.jetruby.common.countries.Country.Companion.EMPTY
import java.io.IOException
import java.io.InputStreamReader
import java.util.Comparator


data class Country(
  var name: String = "",
  var countryCode: String = "",
  var phoneCode: String = ""
) {

  companion object {
    val EMPTY = Country()
    fun china() = Country("China", "CN", "86")
    fun usa() = Country("United States", "US", "1")
    fun comparator(): Comparator<Country> =
      Comparator { c1, c2 -> c1.countryCode.compareTo(c2.countryCode) }
  }

}

@Throws(IOException::class)
fun countryList(context: Context): List<Country> = parseList(
  JsonFactory().createParser(
    InputStreamReader(context.resources.openRawResource(R.raw.countries))
  )
)

/* INTERNAL */

internal class MutableCountry {
  var name: String = ""
  var countryCode: String = ""
  var phoneCode: String = ""
}

@Throws(IOException::class)
internal fun parse(jsonParser: JsonParser): Country {
  val instance = MutableCountry()
  if (jsonParser.currentToken == null) {
    jsonParser.nextToken()
  }
  if (jsonParser.currentToken != JsonToken.START_OBJECT) {
    jsonParser.skipChildren()
    return EMPTY
  }
  while (jsonParser.nextToken() != JsonToken.END_OBJECT) {
    val fieldName = jsonParser.currentName
    jsonParser.nextToken()
    parseField(instance, fieldName, jsonParser)
    jsonParser.skipChildren()
  }
  return Country(instance.name, instance.countryCode, instance.phoneCode)
}

@Throws(IOException::class)
internal fun parseField(instance: MutableCountry, fieldName: String, jsonParser: JsonParser) {
  when (fieldName) {
    "country_code" -> instance.countryCode = jsonParser.getValueAsString(null)
    "name" -> instance.name = jsonParser.getValueAsString(null)
    "phone_code" -> instance.phoneCode = jsonParser.getValueAsString(null)
  }
}

@Throws(IOException::class)
internal fun parseList(jsonParser: JsonParser): List<Country> {
  jsonParser.nextToken()
  val list: MutableList<Country> = mutableListOf()
  if (jsonParser.currentToken == JsonToken.START_ARRAY) {
    while (jsonParser.nextToken() != JsonToken.END_ARRAY)
      list.add(parse(jsonParser))
  }
  return list
}