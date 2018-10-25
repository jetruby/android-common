package com.jetruby.flagsres

import android.content.Context
import androidx.test.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.Matchers.`is`
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FlagAssetsTests {

  lateinit var context: Context

  @Before
  fun initContext() {
    context = InstrumentationRegistry.getInstrumentation().context
  }

  @Test
  fun testRandomCountriesResources() {
    assertThat(R.mipmap.us_flag, `is`(getFlagResIdByCountryCode(context, "US")))
    assertThat(R.mipmap.ab_flag, `is`(getFlagResIdByCountryCode(context, "ab")))
    assertThat(R.mipmap.ad_flag, `is`(getFlagResIdByCountryCode(context, "ad")))
    assertThat(R.mipmap.ae_flag, `is`(getFlagResIdByCountryCode(context, "ae")))
    assertThat(R.mipmap.af_flag, `is`(getFlagResIdByCountryCode(context, "AF")))
    assertThat(R.mipmap.ag_flag, `is`(getFlagResIdByCountryCode(context, "Ag")))
    assertThat(R.mipmap.ai_flag, `is`(getFlagResIdByCountryCode(context, "aI")))
    assertThat(R.mipmap.am_flag, `is`(getFlagResIdByCountryCode(context, "AM")))
    assertThat(R.mipmap.ao_flag, `is`(getFlagResIdByCountryCode(context, "ao")))
  }

}