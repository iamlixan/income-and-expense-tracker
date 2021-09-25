package com.lixan.fajardo.incomeandexpensetracker.ext

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.jakewharton.rxbinding3.view.clicks
import com.lixan.fajardo.incomeandexpensetracker.utils.NINJA_TAP_THROTTLE_TIME
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import java.util.concurrent.TimeUnit

fun View.ninjaTap(onNext: (View) -> Unit): Disposable {
    return this.clicks().throttleFirst(NINJA_TAP_THROTTLE_TIME, TimeUnit.MILLISECONDS)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeBy (
            onNext = {
                onNext.invoke(this)
            }
        )
}

fun View.hideKeyboard() {
    val inputManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputManager.hideSoftInputFromWindow(windowToken, 0)
}

fun View.hideKeyboardClearFocus() {
    hideKeyboard()
    clearFocus()
}