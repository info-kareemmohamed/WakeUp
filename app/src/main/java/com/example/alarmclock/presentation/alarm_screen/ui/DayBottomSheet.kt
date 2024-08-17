package com.example.alarmclock.presentation.alarm_screen.ui

import android.content.Context
import android.os.Bundle
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import com.example.alarmclock.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class DayBottomSheet : BottomSheetDialogFragment() {

    private lateinit var onDaysSaveClickListener: OnDaysSaveClickListener
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnDaysSaveClickListener) {
            onDaysSaveClickListener = context
        } else {
            throw RuntimeException("$context must implement OnDaysSaveClickListener")
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for BottomSheetDialogFragment and add Theme AlarmClock

        return inflater.cloneInContext(ContextThemeWrapper(activity, R.style.Theme_AlarmClock))
            .inflate(R.layout.fragment_bottom_sheet, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<Button>(R.id.bottomsheet_button_save).setOnClickListener {
            val checkBoxIds = listOf(
                R.id.bottomsheet_checkbox_sunday,
                R.id.bottomsheet_checkbox_monday,
                R.id.bottomsheet_checkbox_tuesday,
                R.id.bottomsheet_checkbox_wednesday,
                R.id.bottomsheet_checkbox_thursday,
                R.id.bottomsheet_checkbox_friday,
                R.id.bottomsheet_checkbox_saturday
            )

            val checkedDays = mutableListOf<String>()

            checkBoxIds.forEach { checkBoxId ->
                val checkBox = view.findViewById<CheckBox>(checkBoxId)
                if (checkBox.isChecked) {
                    checkedDays.add(checkBox.tag.toString())
                }
            }

            onDaysSaveClickListener.onButtonSave(checkedDays)
            dismiss()


        }
    }


    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DayBottomSheet().apply {
                arguments = Bundle().apply {

                }
            }


        const val TAG = "ModalBottomSheetDialog"


    }

    interface OnDaysSaveClickListener {

        fun onButtonSave(days: List<String>)

    }

}