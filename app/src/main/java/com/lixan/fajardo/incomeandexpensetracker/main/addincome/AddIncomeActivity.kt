package com.lixan.fajardo.incomeandexpensetracker.main.addincome

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.jakewharton.rxbinding3.widget.textChanges
import com.lixan.fajardo.incomeandexpensetracker.R
import com.lixan.fajardo.incomeandexpensetracker.databinding.ActivityAddIncomeBinding
import com.lixan.fajardo.incomeandexpensetracker.di.base.BaseViewModelActivity
import com.lixan.fajardo.incomeandexpensetracker.ext.*
import com.lixan.fajardo.incomeandexpensetracker.utils.*
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import timber.log.Timber
import java.util.*

class AddIncomeActivity : BaseViewModelActivity<ActivityAddIncomeBinding, AddIncomeViewModel>() {

    override fun getLayoutId(): Int = R.layout.activity_add_income

    private lateinit var calendar : Calendar
    private lateinit var dateSetListener: DatePickerDialog.OnDateSetListener

    private var subType = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViewModel()
        setupViews()
    }

    private fun setupViewModel() {
        viewModel.state
            .observeOn(schedulers.ui())
            .subscribeBy(
                onNext = {
                    handleState(it)
                },
                onError = {
                    Timber.e(it)
                }
            ).addTo(disposables)
    }

    private fun handleState(state: AddIncomeState) {
        when(state) {
            is AddIncomeState.Success -> {
                Toast.makeText(
                    this,
                    getString(R.string.income_added_successfully),
                    Toast.LENGTH_SHORT
                ).show()
                clearFields()
            }
            is AddIncomeState.ShowLoading -> {
                binding.progressView.visibility = View.VISIBLE
            }
            is AddIncomeState.HideLoading -> {
                binding.progressView.visibility = View.GONE
            }
            is AddIncomeState.InvalidAmount -> {
                binding.tvAmountError.visibility = View.VISIBLE
            }
            is AddIncomeState.SubtypeIsEmpty -> {
                binding.tvExpenseTypeError.visibility = View.VISIBLE
            }
            is AddIncomeState.OnAmountTextChange -> {
                binding.etAmount.setText(state.text)
                binding.etAmount.setSelection(state.text.length)
            }
            is AddIncomeState.OnAmountTextClear -> {
                binding.etAmount.text?.clear()
            }
            is AddIncomeState.Error -> {
                Toast.makeText(this, state.errorMessage, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun setupViews() {
        binding.btnBack.ninjaTap {
            onBackPressed()
        }

        binding.cvParent.setBackgroundResource(R.drawable.cv_top_rounded_corners_white)

        binding.btnAddIncome.ninjaTap {
            clearErrors()
            viewModel.addIncome(
                subType,
                binding.etAmount.text?.toString().orEmpty().convertAmountToDouble(),
                binding.etNotes.text.toString(),
                calendar.time.convertDateStringForAPI()
            )
//            viewModel.addExpense(
//                subType,
//                binding.etAmount.text?.toString().orEmpty().convertAmountToDouble(),
//                binding.etNotes.text.toString(),
//                calendar.time.convertDateStringForAPI()
//            )
        }

        setupRadioButtons()
        setupDatePicker()
        setupEtAmount()
    }

    private fun setupRadioButtons() {
        binding.rbWork.setOnCheckedChangeListener { compoundButton, isChecked ->
            if (isChecked) {
                subType = TRANSACTION_SUB_TYPE_WORK

                compoundButton.alpha = 1F
                binding.rbBusiness.alpha = .5F
                binding.rbOthers.alpha = .5F
            }
        }

        binding.rbBusiness.setOnCheckedChangeListener { compoundButton, isChecked ->
            if (isChecked) {
                subType = TRANSACTION_SUB_TYPE_BUSINESS

                compoundButton.alpha = 1F
                binding.rbWork.alpha = .5F
                binding.rbOthers.alpha = .5F
            }
        }

        binding.rbOthers.setOnCheckedChangeListener { compoundButton, isChecked ->
            if (isChecked) {
                subType = TRANSACTION_SUB_TYPE_OTHERS

                compoundButton.alpha = 1F
                binding.rbBusiness.alpha = .5F
                binding.rbWork.alpha = .5F
            }
        }
    }

    private fun setupDatePicker() {
        calendar = Calendar.getInstance()

        binding.etDate.setText(calendar.time.getDateString())

        dateSetListener = DatePickerDialog.OnDateSetListener { datePicker, year, month, day ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, day)

            binding.etDate.setText(calendar.time.getDateString())
        }

        binding.etDate.ninjaTap {
            it.hideKeyboardClearFocus()
            DatePickerDialog(this,
                dateSetListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
    }

    private fun setupEtAmount() {
        binding.etAmount
            .textChanges()
            .skipInitialValue()
            .observeOn(schedulers.ui())
            .subscribeBy(
                onNext = {
                    binding.etAmount.setSelection(it.length)
                    viewModel.onAmountTextChanges(it.toString())
                }
            ).addTo(disposables)
    }

    private fun clearFields() {
        binding.rgIncomeType.clearCheck()
        binding.etDate.setText(Calendar.getInstance().time.getDateString())
        binding.etAmount.text?.clear()
        binding.etNotes.text?.clear()

        binding.rbWork.alpha = 1F
        binding.rbBusiness.alpha = 1F
        binding.rbOthers.alpha = 1F

        subType = ""
        binding.root.hideKeyboardClearFocus()
    }

    private fun clearErrors() {
        binding.tvExpenseTypeError.visibility = View.GONE
        binding.tvAmountError.visibility = View.GONE
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