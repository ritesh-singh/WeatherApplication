package com.example.riteshkumarsingh.weatherapplication.di.modules;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

/**
 * Created by riteshkumarsingh on 21/10/17.
 */

@Module public class ApplicationModule {

  private Application application;

  public ApplicationModule(Application application) {
    this.application = application;
  }

  @Provides @Singleton public Context providesContext() {
    return application;
  }

  @Provides @Singleton public SharedPreferences providesSharedPref(Context context) {
    return PreferenceManager.getDefaultSharedPreferences(context);
  }
}
