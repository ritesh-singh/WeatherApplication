package com.example.riteshkumarsingh.weatherapplication.ui.view.module

import com.example.riteshkumarsingh.weatherapplication.Constants
import com.example.riteshkumarsingh.weatherapplication.network.EndPoint
import dagger.Module
import dagger.Provides
import okhttp3.mockwebserver.MockWebServer
import javax.inject.Named
import javax.inject.Singleton

/**
 * Created by riteshkumarsingh on 31/10/17.
 */
@Module
class MockedModule {

  @Provides
  @Singleton
  fun providesMockWebServer(): MockWebServer = MockWebServer()

  @Provides @Named(Constants.mockedEndPoint)
  @Singleton
  fun providesMockedEndPoint(mockWebServer: MockWebServer): EndPoint {
    return object : EndPoint {
      override fun getUrl(): String =
          mockWebServer.url("/").toString()

    }
  }
}