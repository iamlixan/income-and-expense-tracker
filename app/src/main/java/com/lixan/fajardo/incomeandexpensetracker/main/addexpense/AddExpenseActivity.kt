package com.lixan.fajardo.incomeandexpensetracker.main.addexpense

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import com.jakewharton.rxbinding3.widget.textChanges
import com.lixan.fajardo.incomeandexpensetracker.R
import com.lixan.fajardo.incomeandexpensetracker.databinding.ActivityAddExpenseBinding
import com.lixan.fajardo.incomeandexpensetracker.di.base.BaseViewModelActivity
import com.lixan.fajardo.incomeandexpensetracker.ext.*
import com.lixan.fajardo.incomeandexpensetracker.utils.DATE_FORMAT
import com.lixan.fajardo.incomeandexpensetracker.utils.TRANSACTION_SUB_TYPE_FOOD
import com.lixan.fajardo.incomeandexpensetracker.utils.TRANSACTION_SUB_TYPE_OTHERS
import com.lixan.fajardo.incomeandexpensetracker.utils.TRANSACTION_SUB_TYPE_TRANSPORTATION
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*

class AddExpenseActivity : BaseViewModelActivity<ActivityAddExpenseBinding, AddExpenseViewModel>() {

    override fun getLayoutId(): Int = R.layout.activity_add_expense

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

    private fun handleState(state: AddExpenseState) {
        when(state) {
            is AddExpenseState.Success -> {
                Toast.makeText(
                    this,
                    getString(R.string.expense_added_successfully),
                    Toast.LENGTH_SHORT
                ).show()
                clearFields()
            }
            is AddExpenseState.ShowLoading -> {
                binding.progressView.visibility = View.VISIBLE
            }
            is AddExpenseState.HideLoading -> {
                binding.progressView.visibility = View.GONE
            }
            is AddExpenseState.InvalidAmount -> {
                binding.etAmountContainer.isErrorEnabled = true
                binding.etAmountContainer.error = getString(R.string.error_amount_invalid)
            }
            is AddExpenseState.Error -> {
                Toast.makeText(this, state.errorMessage, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun setupViews() {
        binding.btnBack.ninjaTap {
            onBackPressed()
        }

        binding.cvParent.setBackgroundResource(R.drawable.cv_top_rounded_corners_white)

        binding.btnAddExpense.ninjaTap {
            viewModel.addExpense(
                subType,
                binding.etAmount.text?.toString().orEmpty().convertAmountToDouble(),
                binding.etNotes.text.toString(),
                calendar.time.convertDateStringForAPI()
            )
        }

        setupRadioButtons()
        setupDatePicker()
        setupEtAmount()
    }

    private fun setupRadioButtons() {
        binding.rbFood.setOnCheckedChangeListener { compoundButton, isChecked ->
            if (isChecked) {
                subType = TRANSACTION_SUB_TYPE_FOOD

                compoundButton.alpha = 1F
                binding.rbTransportation.alpha = .5F
                binding.rbOthers.alpha = .5F
            }
        }

        binding.rbTransportation.setOnCheckedChangeListener { compoundButton, isChecked ->
            if (isChecked) {
                subType = TRANSACTION_SUB_TYPE_TRANSPORTATION

                compoundButton.alpha = 1F
                binding.rbFood.alpha = .5F
                binding.rbOthers.alpha = .5F
            }
        }

        binding.rbOthers.setOnCheckedChangeListener { compoundButton, isChecked ->
            if (isChecked) {
                subType = TRANSACTION_SUB_TYPE_OTHERS

                compoundButton.alpha = 1F
                binding.rbTransportation.alpha = .5F
                binding.rbFood.alpha = .5F
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
//                  TODO: Move Logic to ViewModel

                    binding.etAmount.setSelection(it.length)
                    if (it.isNullOrEmpty()) return@subscribeBy
                    if (it.toString() == "$") {
                        binding.etAmount.text?.clear()
                        return@subscribeBy
                    }
                    if (it.startsWith("$", false)) return@subscribeBy
                    val amountText = "$$it"
                    binding.etAmount.setText(amountText)
                }
            ).addTo(disposables)
    }

    private fun clearFields() {
        binding.rgExpenseType.clearCheck()
        binding.etDate.setText(Calendar.getInstance().time.getDateString())
        binding.etAmount.text?.clear()
        binding.etNotes.text?.clear()
        subType = ""
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