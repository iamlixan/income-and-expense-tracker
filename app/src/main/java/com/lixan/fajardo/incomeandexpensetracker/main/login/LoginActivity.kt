package com.lixan.fajardo.incomeandexpensetracker.main.login

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import com.jakewharton.rxbinding3.widget.textChanges
import com.lixan.fajardo.incomeandexpensetracker.R
import com.lixan.fajardo.incomeandexpensetracker.databinding.ActivityLoginBinding
import com.lixan.fajardo.incomeandexpensetracker.di.base.BaseViewModelActivity
import com.lixan.fajardo.incomeandexpensetracker.ext.hideKeyboardClearFocus
import com.lixan.fajardo.incomeandexpensetracker.ext.ninjaTap
import com.lixan.fajardo.incomeandexpensetracker.utils.NINJA_TAP_THROTTLE_TIME
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import timber.log.Timber
import java.util.concurrent.TimeUnit

class LoginActivity : BaseViewModelActivity<ActivityLoginBinding, LoginViewModel>() {

    override fun getLayoutId(): Int = R.layout.activity_login

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupViewModel()
        setupViews()
    }

    private fun setupViews() {
        binding.btnLogin
            .ninjaTap {
                doLogin()
        }.addTo(disposables)

        binding.etEmail
            .textChanges()
            .mergeWith(binding.etPassword.textChanges())
            .debounce(NINJA_TAP_THROTTLE_TIME, TimeUnit.MILLISECONDS)
            .observeOn(schedulers.ui())
            .subscribe {
                clearErrors()
            }.addTo(disposables)

        binding
            .tvForgotPassword
            .ninjaTap {
                Toast.makeText(this, "Coming Soon!", Toast.LENGTH_SHORT).show()
            }
    }

    private fun setupViewModel() {
        viewModel.state
            .observeOn(schedulers.ui())
            .subscribeBy(
                onNext = {
                    handleStates(it)
                },
                onError = {
                    Timber.e(it)
                }
            )
            .addTo(disposables)
    }

    private fun handleStates(state: LoginState) {
        when(state) {
            is LoginState.LoginSuccess -> {
                Toast.makeText(this, "Welcome, ${state.userData.user.firstName} !", Toast.LENGTH_LONG).show()
            }
            is LoginState.EmailIsEmpty -> {
                setEmailError(getString(R.string.error_email_is_empty))
            }
            is LoginState.EmailIsInvalid -> {
                setEmailError(getString(R.string.error_invalid_format))
            }
            is LoginState.PasswordIsEmpty -> {
                setPasswordError(getString(R.string.error_password_empty))
            }
            is LoginState.IsLoading -> {
                binding.progressView.visibility = View.VISIBLE
                binding.tvError.isVisible = false
            }
            is LoginState.IsFinishedLoading -> {
                binding.progressView.visibility = View.GONE
            }
            is LoginState.Error -> {
                binding.tvError.isVisible = true
                binding.tvError.text = state.errorMessage
            }
        }
    }

    private fun doLogin() {
        clearErrors()
        binding.root.hideKeyboardClearFocus()
        viewModel.login(
            binding.etEmail.text.toString().trim(),
            binding.etPassword.text.toString().trim()
        )
    }

    private fun clearErrors() {
        binding.etEmailContainer.isErrorEnabled = false
        binding.etEmailContainer.error = ""

        binding.etPasswordLayout.isErrorEnabled = false
        binding.etPasswordLayout.error = ""
    }

    private fun setEmailError(message: String) {
        binding.etEmailContainer.isErrorEnabled = true
        binding.etEmailContainer.error = message
    }

    private fun setPasswordError(message: String) {
        binding.etPasswordLayout.isErrorEnabled = true
        binding.etPasswordLayout.error = message
    }
}