package com.example.criminalintent

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Build
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import java.util.*

class DatePickerFragment : DialogFragment() {

    companion object {
        private const val ARG_DATE = "date"
        private const val ARG_REQUEST_CODE = "requestCode"
        private const val RESULT_DATE_KEY = "resultDateKey"

        fun newInstance(date: Date, requestCode: String): DatePickerFragment {
            val args = Bundle().apply {
                putSerializable(ARG_DATE, date)
                putString(ARG_REQUEST_CODE, requestCode)
            }

            return DatePickerFragment().apply {
                arguments = args
            }
        }

        @Suppress("DEPRECATION")
        fun getSelectedDate(result: Bundle): Date {

            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
                result.getSerializable(RESULT_DATE_KEY, Date::class.java)!!
            else result.getSerializable(RESULT_DATE_KEY) as Date
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val dateListener = DatePickerDialog.OnDateSetListener {
                _: DatePicker, year: Int, month: Int, day: Int ->
            val resultDate: Date = GregorianCalendar(year, month, day).time

            // create our result Bundle
            val result = Bundle().apply {
                putSerializable(RESULT_DATE_KEY, resultDate)
            }

            val resultRequestCode = requireArguments().getString(ARG_REQUEST_CODE, "")
            parentFragmentManager.setFragmentResult(resultRequestCode, result)
        }

        @Suppress("DEPRECATION") val date =
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
                arguments?.getSerializable(ARG_DATE, Date::class.java)!!
            else arguments?.getSerializable(ARG_DATE) as Date

        val calendar = Calendar.getInstance()
        calendar.time = date

        val initialYear = calendar.get(Calendar.YEAR)
        val initialMonth = calendar.get(Calendar.MONTH)
        val initialDay = calendar.get(Calendar.DAY_OF_MONTH)

        return DatePickerDialog(
            requireContext(),
            dateListener,
            initialYear,
            initialMonth,
            initialDay
        )
    }
}