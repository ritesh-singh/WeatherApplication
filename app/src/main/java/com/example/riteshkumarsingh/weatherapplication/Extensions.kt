package com.example.riteshkumarsingh.weatherapplication

import android.content.Context
import android.widget.Toast

/**
 * Created by riteshkumarsingh on 24/10/17.
 */
fun Context.toast(message: String) {
  Toast.makeText(this, message, Toast.LENGTH_LONG)
      .show()
}


