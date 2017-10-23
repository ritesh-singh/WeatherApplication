package com.example.riteshkumarsingh.weatherapplication.di.components;

import com.example.riteshkumarsingh.weatherapplication.di.modules.ApiModule;
import com.example.riteshkumarsingh.weatherapplication.di.modules.ApplicationModule;
import com.example.riteshkumarsingh.weatherapplication.ui.view.HomeActivity;
import dagger.Component;
import javax.inject.Singleton;

/**
 * Created by riteshkumarsingh on 21/10/17.
 */

@Singleton @Component(modules = { ApplicationModule.class, ApiModule.class })
public interface ApplicationComponent {
  void inject(HomeActivity homeActivity);
}
