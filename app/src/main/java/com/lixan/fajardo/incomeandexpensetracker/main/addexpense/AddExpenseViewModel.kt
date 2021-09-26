package com.lixan.fajardo.incomeandexpensetracker.main.addexpense

import android.os.Bundle
import com.lixan.fajardo.incomeandexpensetracker.di.base.BaseViewModel
import com.lixan.fajardo.incomeandexpensetracker.utils.ResourceManager
import javax.inject.Inject

class AddExpenseViewModel @Inject constructor(
    private val resourceManager: ResourceManager
): BaseViewModel() {

    override fun isFirstTimeUICreated(bundle: Bundle?) = Unit

}