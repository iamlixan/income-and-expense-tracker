package com.lixan.fajardo.incomeandexpensetracker.utils

import android.content.Context
import androidx.annotation.*
import androidx.core.content.ContextCompat
import com.lixan.fajardo.incomeandexpensetracker.di.scope.ApplicationContext
import com.lixan.fajardo.incomeandexpensetracker.ext.isEmailValid
import javax.inject.Inject

class ResourceManager @Inject constructor(
    @ApplicationContext private val context: Context
) {

    @ColorInt
    fun getColor(@ColorRes resId: Int): Int {
        return ContextCompat.getColor(context, resId)
    }

    fun getString(@StringRes resId: Int): String {
        return context.getString(resId)
    }

    fun getString(@StringRes resId: Int, vararg formatArgs: Any): String {
        return context.getString(resId, *formatArgs)
    }

    fun getDimen(@DimenRes resId: Int) = context.resources.getDimension(resId)

    fun getInteger(@IntegerRes resId: Int) = context.resources.getInteger(resId)

    fun getAndroidPackageName() = context.applicationContext.packageName

    fun getQuantityString(@PluralsRes resId: Int, quantity: Int, vararg formatArgs: Any): String {
        return context.resources.getQuantityString(resId, quantity, *formatArgs)
    }

    fun validateEmail(email: String) : Boolean {
        return email.isEmailValid()
    }
}