package com.example.alarmclock.presentation.alarm_screen.ui


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
import com.example.alarmclock.core.AlarmSound
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class SoundBottomSheet : BottomSheetDialogFragment() {
    private var sound: AlarmSound? = null

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

        val radioGroup = view.findViewById<RadioGroup>(R.id.bottomsheet_radiogroup)
        val saveButton = view.findViewById<Button>(R.id.bottomsheet_button_save)
        var soundResource = Pair(R.raw.default_sound, "Default sound")
        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            val selectedRadioButton = view.findViewById<RadioButton>(checkedId)
            soundResource = getSoundAndName(selectedRadioButton.tag.toString())

            sound?.release()
            sound = AlarmSound(context, soundResource.first).apply { startSound() }


        }
        saveButton.setOnClickListener {
            onSoundSaveClickListener.onButtonSave(soundResource.first, soundResource.second)
            dismiss()
        }

    }

    override fun onDetach() {
        sound?.release()
        super.onDetach()
    }


    private fun getSoundAndName(tag: String): Pair<Int, String> {
        return when (tag) {
            "default_sound" -> Pair(R.raw.default_sound, "Default sound")
            "classic_winner" -> Pair(R.raw.classic_winner, "Classic winner")
            "digital" -> Pair(R.raw.digital, "Digital")
            "emergency" -> Pair(R.raw.emergency, "Emergency")
            "facility" -> Pair(R.raw.facility, "Facility")
            "morning" -> Pair(R.raw.morning, "Morning")
            "rooster_crowing" -> Pair(R.raw.ooster_crowing, "Rooster crowing")
            "security" -> Pair(R.raw.security, "Security")
            "sound_alert" -> Pair(R.raw.sound_alert, "Sound alert")
            else -> Pair(R.raw.default_sound, "Default sound")
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

        fun onButtonSave(sound: Int, name: String)

    }

}