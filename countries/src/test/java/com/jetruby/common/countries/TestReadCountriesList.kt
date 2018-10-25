package com.jetruby.common.countries

import com.fasterxml.jackson.core.JsonFactory
import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.assertThat
import org.junit.Test
import java.io.FileInputStream
import java.io.InputStreamReader

class TestReadCountriesList {

  @Test
  fun testCountriesCount() {
    val path = "/Users/anton_azaryan/AndroidStudioProjects/JetrubyAndroidCommon/countries/src/main/res/raw/countries.json"
    val iStream = InputStreamReader(FileInputStream(path))
    val parser = JsonFactory().createParser(iStream)
    val countriesList = parseList(parser)

    assertThat(countriesList.size, `is`(232))
  }

  @Test
  fun testReadRandomCountries() {
    val path = "/Users/anton_azaryan/AndroidStudioProjects/JetrubyAndroidCommon/countries/src/main/res/raw/countries.json"
    val iStream = InputStreamReader(FileInputStream(path))
    val parser = JsonFactory().createParser(iStream)
    val countriesList = parseList(parser)

    val vietnam = Country("Vietnam", "VN", "84")
    val russia = Country("Russia", "RU", "7")
    val ukraine = Country("Ukraine", "UA", "380")
    val armenia = Country("Armenia", "AM", "374")
    val us = Country("United States", "US", "1")

    assertThat(vietnam, `is`(countriesList.find { it == vietnam }))
    assertThat(russia, `is`(countriesList.find { it == russia }))
    assertThat(ukraine, `is`(countriesList.find { it == ukraine }))
    assertThat(armenia, `is`(countriesList.find { it == armenia }))
    assertThat(us, `is`(countriesList.find { it == us }))
  }

}