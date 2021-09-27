package com.lixan.fajardo.incomeandexpensetracker.main.addincome

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.lixan.fajardo.incomeandexpensetracker.R
import com.lixan.fajardo.incomeandexpensetracker.databinding.ActivityAddIncomeBinding
import com.lixan.fajardo.incomeandexpensetracker.di.base.BaseViewModelActivity

class AddIncomeActivity : BaseViewModelActivity<ActivityAddIncomeBinding, AddIncomeViewModel>() {

    override fun getLayoutId(): Int = R.layout.activity_add_income

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }



    companion object {
        fun openActivity(context: Context) {
            context.startActivity(
                Intent(
                    context,
                    AddIncomeActivity::class.java
                )
            )
        }
    }
}