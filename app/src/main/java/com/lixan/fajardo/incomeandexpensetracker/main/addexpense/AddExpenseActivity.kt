package com.lixan.fajardo.incomeandexpensetracker.main.addexpense

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.lixan.fajardo.incomeandexpensetracker.R
import com.lixan.fajardo.incomeandexpensetracker.databinding.ActivityAddExpenseBinding
import com.lixan.fajardo.incomeandexpensetracker.di.base.BaseViewModelActivity
import com.lixan.fajardo.incomeandexpensetracker.ext.ninjaTap

class AddExpenseActivity : BaseViewModelActivity<ActivityAddExpenseBinding, AddExpenseViewModel>() {

    override fun getLayoutId(): Int = R.layout.activity_add_expense

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }


    private fun setupViews() {
        binding.btnBack.ninjaTap {
            onBackPressed()
        }

        binding.cvParent.setBackgroundResource(R.drawable.cv_top_rounded_corners_white)
    }

    companion object {
        fun openActivity(context: Context) {
            context.startActivity(
                Intent(
                    context, AddExpenseActivity::class.java
                )
            )
        }
    }
}