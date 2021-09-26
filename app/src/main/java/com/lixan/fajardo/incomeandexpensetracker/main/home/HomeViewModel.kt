package com.lixan.fajardo.incomeandexpensetracker.main.home

import android.os.Bundle
import com.lixan.fajardo.incomeandexpensetracker.di.base.BaseViewModel
import javax.inject.Inject

class HomeViewModel @Inject constructor(

): BaseViewModel() {

    override fun isFirstTimeUICreated(bundle: Bundle?) = Unit

}