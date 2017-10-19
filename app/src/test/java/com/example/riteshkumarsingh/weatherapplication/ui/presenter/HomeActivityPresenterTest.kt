package com.example.riteshkumarsingh.weatherapplication.ui.presenter

import com.example.riteshkumarsingh.gojek.data.models.WeatherForecast
import com.example.riteshkumarsingh.weatherapplication.service.ApiService
import com.example.riteshkumarsingh.weatherapplication.ui.view.HomeActivityView
import okhttp3.ResponseBody
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


/**
 * Created by riteshkumarsingh on 18/10/17.
 */
class HomeActivityPresenterTest {

  @Mock
  lateinit var homeActivityView: HomeActivityView

  @Mock
  lateinit var apiService: ApiService

  @Mock
  lateinit var mockCall: Call<WeatherForecast>

  @Mock
  lateinit var responseBody: ResponseBody

  @Captor
  lateinit var argumentCaptor: ArgumentCaptor<Callback<WeatherForecast>>

  lateinit var homeActivityPresenter: HomeActivityPresenter

  lateinit var weatherForecast: WeatherForecast

  @Before fun setUp() {
    MockitoAnnotations.initMocks(this)
    homeActivityPresenter = HomeActivityPresenter(homeActivityView, apiService)
    weatherForecast = WeatherForecast()
  }


  @Test
  fun fetchWeatherData_shouldGetWeatherData() {
    `when`(apiService.getWeatherData("Bangalore", 4))
        .thenReturn(mockCall)

    val response: Response<WeatherForecast> =
        Response.success(weatherForecast)

    homeActivityPresenter.fetchWeatherData()


    verify(mockCall)
        .enqueue(argumentCaptor.capture())

    argumentCaptor
        .value
        .onResponse(null, response)

    verify(homeActivityView)
        .showWeatherForecast(weatherForecast)
  }


  @Test
  fun fetchWeatherData_shouldShowErrorView_whenFailRequest() {
    `when`(apiService.getWeatherData("Bangalore", 4))
        .thenReturn(mockCall)
    val throwable: Throwable = Throwable()

    homeActivityPresenter.fetchWeatherData()

    verify(mockCall)
        .enqueue(argumentCaptor.capture())

    argumentCaptor
        .value
        .onFailure(null, throwable)

    verify(homeActivityView)
        .showErrorView()

  }


  @Test
  fun fetchWeatherData_shouldDoNothing_whenBadRequest(){
      `when`(apiService.getWeatherData("Bangalore",4))
          .thenReturn(mockCall)

    val response:Response<WeatherForecast> =
        Response.error(500,responseBody)

    homeActivityPresenter.fetchWeatherData()

    verify(mockCall)
        .enqueue(argumentCaptor.capture())

    argumentCaptor.value
        .onResponse(null,response)

    verifyNoMoreInteractions(homeActivityView)
  }

}