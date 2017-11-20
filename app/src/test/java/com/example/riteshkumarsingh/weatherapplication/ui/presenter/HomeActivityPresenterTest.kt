package com.example.riteshkumarsingh.weatherapplication.ui.presenter

import com.example.riteshkumarsingh.weatherapplication.network.ApiService
import com.example.riteshkumarsingh.weatherapplication.ui.view.HomeActivityView
import org.junit.After
import org.junit.Before

import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

/**
 * Created by admin1 on 17/11/17.
 */
class HomeActivityPresenterTest {

    @Mock
    private lateinit var homeActivityView:HomeActivityView

    @Mock
    private lateinit var apiService:ApiService

    private lateinit var homeActivityPresenter:HomeActivityPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        homeActivityPresenter = HomeActivityPresenter(homeActivityView,
                apiService,"")
    }

    @Test
    fun fetch_weather_data_when_success(){

    }

    @Test
    fun fetch_weather_data_when_fail(){
        fail()
    }

    @After
    fun tearDown() {
    }

}