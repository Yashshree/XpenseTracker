package com.com.xpensetracker.utils

import android.content.Context
import android.content.Context.INPUT_METHOD_SERVICE
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat.getSystemService
import java.util.*


fun getDaysLeftForMonthToEnd():Int{
    val lastDay=  Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH);
    val currentDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
    return lastDay-currentDay
}

fun Context.hideSoftKeyboard(view: View){
        val imm: InputMethodManager? =
            getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager?
        imm?.hideSoftInputFromWindow(view.getWindowToken(), 0)
}