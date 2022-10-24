package com.example.criminalintent

import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Build
import android.os.Bundle
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import java.util.Calendar
import java.util.Date

class TimePickerFragment : DialogFragment() {

    companion object {
        private const val ARG_TIME = "time"
        private const val ARG_REQUEST_CODE = "requestCode"
        private const val RESULT_TIME_KEY = "resultTimeKey"

        // Pack received date
        fun newInstance(date: Date, requestCode: String): TimePickerFragment {
            val args = Bundle().apply {
                putSerializable(ARG_TIME, date)
                putString(ARG_REQUEST_CODE, requestCode)
            }

            return TimePickerFragment().apply {
                arguments = args
            }
        }

        // Send dialog result
        @Suppress("DEPRECATION")
        fun getSelectedTime(result: Bundle): String {

            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
                result.getSerializable(RESULT_TIME_KEY, String::class.java)!!
            else result.getSerializable(RESULT_TIME_KEY) as String
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val timeListener = TimePickerDialog.OnTimeSetListener {
            _: TimePicker, hourOfDay: Int, minute: Int ->

            val resultTime = "$hourOfDay:$minute"
            val result = Bundle().apply {
                putSerializable(RESULT_TIME_KEY, resultTime)
            }

            val resultRequestCode = requireArguments().getString(ARG_REQUEST_CODE, "")
            parentFragmentManager.setFragmentResult(resultRequestCode, result)
        }

        // Unpack received date
        @Suppress("DEPRECATION") val date =
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
                arguments?.getSerializable(ARG_TIME, Date::class.java)!!
            else arguments?.getSerializable(ARG_TIME) as Date

        val calendar = Calendar.getInstance()
        calendar.time = date

        val initialHours = calendar.get(Calendar.HOUR_OF_DAY)
        val initialMinutes = calendar.get(Calendar.MINUTE)

        return TimePickerDialog(
            requireContext(),
            timeListener,
            initialHours,
            initialMinutes,
            false
        )
    }
}