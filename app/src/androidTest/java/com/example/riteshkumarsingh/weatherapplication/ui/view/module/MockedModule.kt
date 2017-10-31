package com.example.riteshkumarsingh.weatherapplication.ui.view.module

import dagger.Module
import dagger.Provides
import okhttp3.mockwebserver.MockWebServer
import javax.inject.Singleton

/**
 * Created by riteshkumarsingh on 31/10/17.
 */
@Module
class MockedModule {

  @Provides
  @Singleton
  fun providesMockWebServer(): MockWebServer = MockWebServer()
}