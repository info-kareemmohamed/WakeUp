package com.example.alarmclock.ui


import android.content.Context
import android.os.Bundle
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import com.example.alarmclock.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class SoundBottomSheet : BottomSheetDialogFragment() {

    private lateinit var onSoundSaveClickListener: OnSoundSaveClickListener
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnSoundSaveClickListener) {
            onSoundSaveClickListener = context
        } else {
            throw RuntimeException("$context must implement OnSoundSaveClickListener")
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.cloneInContext(ContextThemeWrapper(activity, R.style.Theme_AlarmClock))
            .inflate(R.layout.fragment_sound_bottom_sheet, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<Button>(R.id.bottomsheet_button_save).setOnClickListener {

            val radioGroup = view.findViewById<RadioGroup>(R.id.bottomsheet_radiogroup)
            val saveButton = view.findViewById<Button>(R.id.bottomsheet_button_save)

            saveButton.setOnClickListener {
                val selectedRadioButtonId = radioGroup.checkedRadioButtonId
                if (selectedRadioButtonId != -1) {
                    val selectedRadioButton = view.findViewById<RadioButton>(selectedRadioButtonId)
                    val selectedTag = selectedRadioButton.tag.toString()

                    when (selectedTag) {
                        "default_sound" -> onSoundSaveClickListener.onButtonSave(R.raw.default_sound,"Default sound")
                        "classic_winner" -> onSoundSaveClickListener.onButtonSave(R.raw.classic_winner,"Classic winner")
                        "digital" -> onSoundSaveClickListener.onButtonSave(R.raw.digital,"Digital")
                        "emergency" -> onSoundSaveClickListener.onButtonSave(R.raw.emergency, "Emergency")
                        "facility" -> onSoundSaveClickListener.onButtonSave(R.raw.facility,"Facility")
                        "morning" -> onSoundSaveClickListener.onButtonSave(R.raw.morning,  "Morning")
                        "rooster_crowing" -> onSoundSaveClickListener.onButtonSave(R.raw.ooster_crowing, "Rooster crowing")
                        "security" -> onSoundSaveClickListener.onButtonSave(R.raw.security, "Security")
                        "sound_alert" -> onSoundSaveClickListener.onButtonSave(R.raw.sound_alert, "Sound alert")
                    }


                }
                dismiss()
            }
        }
    }


    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DayBottomSheet().apply {
                arguments = Bundle().apply {

                }
            }


        const val TAG = "ModalSoundBottomSheetDialog"


    }

    interface OnSoundSaveClickListener {

        fun onButtonSave(sound: Int,name:String)

    }

}