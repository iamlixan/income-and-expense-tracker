package com.lixan.fajardo.incomeandexpensetracker.main.addexpense

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.jakewharton.rxbinding3.widget.textChanges
import com.lixan.fajardo.incomeandexpensetracker.R
import com.lixan.fajardo.incomeandexpensetracker.databinding.ActivityAddExpenseBinding
import com.lixan.fajardo.incomeandexpensetracker.di.base.BaseViewModelActivity
import com.lixan.fajardo.incomeandexpensetracker.ext.hideKeyboardClearFocus
import com.lixan.fajardo.incomeandexpensetracker.ext.ninjaTap
import com.lixan.fajardo.incomeandexpensetracker.utils.DATE_FORMAT
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import java.text.SimpleDateFormat
import java.util.*

class AddExpenseActivity : BaseViewModelActivity<ActivityAddExpenseBinding, AddExpenseViewModel>() {

    override fun getLayoutId(): Int = R.layout.activity_add_expense

    private lateinit var calendar : Calendar
    private lateinit var dateSetListener: DatePickerDialog.OnDateSetListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        binding.btnBack.ninjaTap {
            onBackPressed()
        }

        binding.cvParent.setBackgroundResource(R.drawable.cv_top_rounded_corners_white)

        setupDatePicker()
        setupEtAmount()
    }

    private fun setupDatePicker() {
        calendar = Calendar.getInstance()

        binding.etDate.setText(getUpdatedDateText(calendar.time))

        dateSetListener = DatePickerDialog.OnDateSetListener { datePicker, year, month, day ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, day)

            binding.etDate.setText(getUpdatedDateText(calendar.time))
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

    private fun getUpdatedDateText(time: Date): String {
        val format = DATE_FORMAT
        val sdf = SimpleDateFormat(format, Locale.getDefault())

        return sdf.format(time)
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