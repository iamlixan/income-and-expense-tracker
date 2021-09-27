package com.lixan.fajardo.incomeandexpensetracker.main.home

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.lixan.fajardo.incomeandexpensetracker.R
import com.lixan.fajardo.incomeandexpensetracker.databinding.ActivityHomeBinding
import com.lixan.fajardo.incomeandexpensetracker.di.base.BaseViewModelActivity
import com.lixan.fajardo.incomeandexpensetracker.ext.ninjaTap
import com.lixan.fajardo.incomeandexpensetracker.main.addexpense.AddExpenseActivity
import com.lixan.fajardo.incomeandexpensetracker.main.addincome.AddIncomeActivity

class HomeActivity : BaseViewModelActivity<ActivityHomeBinding, HomeViewModel>() {

    override fun getLayoutId(): Int = R.layout.activity_home

    private val fromBottomAnim : Animation by lazy { AnimationUtils.loadAnimation(this, R.anim.anim_from_bottom) }
    private val toBottomAnim : Animation by lazy { AnimationUtils.loadAnimation(this, R.anim.anim_to_bottom) }
    private val openAnim : Animation by lazy { AnimationUtils.loadAnimation(this, R.anim.anim_rotate_open) }
    private val closeAnim : Animation by lazy { AnimationUtils.loadAnimation(this, R.anim.anim_rotate_close) }

    private var isFabOptionsShown = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupBottomNavigation()
        setupFAB()
    }

    private fun setupBottomNavigation() {
        binding.bottomNav.menu.clear()
        binding.bottomNav.inflateMenu(R.menu.bottom_nav_menu)
        binding.bottomNav.setOnNavigationItemSelectedListener {

            return@setOnNavigationItemSelectedListener true
        }
    }

    private fun setupFAB() {
        binding.fabAdd.ninjaTap {
            isFabOptionsShown = !isFabOptionsShown
            openFabOptions(!isFabOptionsShown)
        }

        binding.fabExpense.ninjaTap {
            AddExpenseActivity.openActivity(this)
            binding.fabAdd.performClick()
        }

        binding.fabIncome.ninjaTap {
            AddIncomeActivity.openActivity(this)
            binding.fabAdd.performClick()
        }

    }

    private fun openFabOptions(isShowFabOptions: Boolean) {
        if (!isShowFabOptions) {
            binding.fabIncome.visibility = View.VISIBLE
            binding.fabExpense.visibility = View.VISIBLE
            binding.fabOverlay.visibility = View.VISIBLE

            binding.fabIncome.startAnimation(fromBottomAnim)
            binding.fabExpense.startAnimation(fromBottomAnim)
            binding.fabAdd.startAnimation(openAnim)
        } else {
            binding.fabIncome.visibility = View.GONE
            binding.fabExpense.visibility = View.GONE
            binding.fabOverlay.visibility = View.GONE

            binding.fabIncome.startAnimation(toBottomAnim)
            binding.fabExpense.startAnimation(toBottomAnim)
            binding.fabAdd.startAnimation(closeAnim)
        }
    }

    companion object {
        fun openActivity(context: Context) {
            context.startActivity(
                Intent(
                    context,
                    HomeActivity::class.java
                )
            )
            (context as Activity).finish()
        }
    }
}