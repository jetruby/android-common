package com.aza.integrationtests

import androidx.test.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.jetruby.common.countries.countryList
import com.jetruby.flagsres.getFlagResIdByCountryCode
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class InstrumentedTests {

  @Test
  fun testJsonAndResourcesMatching() {
    val context = InstrumentationRegistry.getInstrumentation().context
    val countries = countryList(context)
    countries.forEach {
      val resourceId = getFlagResIdByCountryCode(context, it.countryCode)
      assert(resourceId != 0)
    }
  }

}