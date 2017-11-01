package com.example.riteshkumarsingh.weatherapplication.ui.view.activities

import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.IdlingRegistry
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withText
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.example.riteshkumarsingh.weatherapplication.Constants
import com.example.riteshkumarsingh.weatherapplication.R
import com.example.riteshkumarsingh.weatherapplication.WeatherApplication
import com.example.riteshkumarsingh.weatherapplication.ui.view.HomeActivity
import com.example.riteshkumarsingh.weatherapplication.ui.view.component.ApplicationTestComponent
import com.example.riteshkumarsingh.weatherapplication.ui.view.utils.RestServiceTestHelper
import com.jakewharton.espresso.OkHttp3IdlingResource
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject


/**
 * Created by riteshkumarsingh on 30/10/17.
 */
@RunWith(AndroidJUnit4::class)
class HomeActivityTest {

  @Rule
  @JvmField
  var homeActivity = ActivityTestRule(
      HomeActivity::class.java,
      true,
      false)

  @Inject
  lateinit var okHttpClient: OkHttpClient

  private lateinit var mockWebServer: MockWebServer

  @Before
  fun setUp() {
    mockWebServer = MockWebServer()
    mockWebServer.start()

    Constants.BASE_URL = mockWebServer.url("/").toString()


    val applicationTestComponent: ApplicationTestComponent =
        WeatherApplication.applicationComponent as ApplicationTestComponent

    applicationTestComponent.inject(this)

  }

  @Test
  fun shouldBeAbleToShowCurrentTemperature() {
    val fileName = "forecast_data.json"
    val instrumentation = InstrumentationRegistry.getInstrumentation()

    mockWebServer
        .enqueue(MockResponse()
            .setResponseCode(200)
            .setBody(
                RestServiceTestHelper.getStringFromFile(
                    instrumentation
                        .context, fileName)))

    homeActivity.launchActivity(null)


    val idlingResource = OkHttp3IdlingResource.create(
        "okhttp", okHttpClient)

    IdlingRegistry.getInstance().register(idlingResource)

    onView(withText("18.0"))
        .check(matches(isDisplayed()))

    IdlingRegistry.getInstance().unregister(idlingResource)
  }


  @Test
  fun shouldBeAbleToShowErrorMessage() {
    val fileName = "not_found.json"
    val instrumentation = InstrumentationRegistry.getInstrumentation()

    mockWebServer
        .enqueue(MockResponse()
            .setResponseCode(404)
            .setBody(RestServiceTestHelper.getStringFromFile(instrumentation.context, fileName)))

    homeActivity.launchActivity(null)

    val idlingResource = OkHttp3IdlingResource.create(
        "okhttp", okHttpClient)

    IdlingRegistry.getInstance().register(idlingResource)

    onView(withText(instrumentation.targetContext.getString(R.string.error_msg)))
        .check(matches(isDisplayed()))

    IdlingRegistry.getInstance().unregister(idlingResource)
  }

  @After
  fun tearUp() {
    mockWebServer.shutdown()
  }

}