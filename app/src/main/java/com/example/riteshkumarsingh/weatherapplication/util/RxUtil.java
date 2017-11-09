package com.example.riteshkumarsingh.weatherapplication.util;

import io.reactivex.SingleTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by riteshkumarsingh on 09/11/17.
 */

public final class RxUtil {
  private RxUtil() {
    throw new RuntimeException("This class cannot be instantiated..!!");
  }

  public static <T> SingleTransformer<T, T> applySchedulersToSingleObservable() {
    return upstream -> upstream.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread());
  }
}
