package com.example.riteshkumarsingh.weatherapplication.di.components;

import android.content.SharedPreferences;
import com.example.riteshkumarsingh.weatherapplication.Constants;
import com.example.riteshkumarsingh.weatherapplication.di.modules.ApiModule;
import com.example.riteshkumarsingh.weatherapplication.di.modules.ApplicationModule;
import com.example.riteshkumarsingh.weatherapplication.di.modules.CacheModule;
import com.example.riteshkumarsingh.weatherapplication.di.modules.InterceptorModule;
import com.example.riteshkumarsingh.weatherapplication.network.ApiService;
import dagger.Component;
import javax.inject.Named;
import javax.inject.Singleton;

/**
 * Created by riteshkumarsingh on 21/10/17.
 */

@Singleton @Component(modules = {
    ApplicationModule.class, CacheModule.class, InterceptorModule.class, ApiModule.class
}) public interface ApplicationComponent {
  SharedPreferences getSharedPreferences();

  ApiService getApiService();

  @Named(Constants.weatherApiKey) String getApiKey();
}
