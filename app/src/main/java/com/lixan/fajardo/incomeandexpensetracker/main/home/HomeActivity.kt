package com.lixan.fajardo.incomeandexpensetracker.main.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.lixan.fajardo.incomeandexpensetracker.R
import com.lixan.fajardo.incomeandexpensetracker.databinding.ActivityHomeBinding
import com.lixan.fajardo.incomeandexpensetracker.di.base.BaseViewModelActivity

class HomeActivity : BaseViewModelActivity<ActivityHomeBinding, HomeViewModel>() {

    override fun getLayoutId(): Int = R.layout.activity_home

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupBottomNavigation()
    }

    private fun setupBottomNavigation() {
        binding.bottomNav.menu.clear()
        binding.bottomNav.inflateMenu(R.menu.bottom_nav_menu)
        binding.bottomNav.setOnNavigationItemSelectedListener {

            return@setOnNavigationItemSelectedListener true
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
        }
    }
}