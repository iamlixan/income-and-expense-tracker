package com.lixan.fajardo.incomeandexpensetracker.di.base

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProviders
import com.lixan.fajardo.incomeandexpensetracker.ViewModelFactory
import java.lang.reflect.ParameterizedType
import javax.inject.Inject


abstract class BaseViewModelActivity<BINDING: ViewDataBinding, VM: BaseViewModel> : BaseActivity<BINDING>() {

    @Inject
    lateinit var vmFactory: ViewModelFactory<VM>

    lateinit var viewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val vmClass = (javaClass.genericSuperclass as ParameterizedType)
            .actualTypeArguments[1] as Class<VM>

        viewModel = ViewModelProviders
            .of(this, vmFactory)
            .get(vmClass)
        viewModel.onCreate(intent.extras)
    }

}