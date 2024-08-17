package com.example.alarmclock.presentation.alarm_challenge_screen.view_model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.alarmclock.common.Constant.EXTRA_ID
import com.example.alarmclock.common.Constant.EXTRA_TIME
import com.example.alarmclock.data.model.Alarm
import com.example.alarmclock.data.model.Question
import com.example.alarmclock.domain.use_case.GetAlarmByIdUseCase
import com.example.alarmclock.domain.use_case.GetQuestionUseCase
import com.example.alarmclock.domain.use_case.SnoozeAlarmUseCase
import com.example.alarmclock.domain.use_case.UpdateAlarmUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlarmChallengeViewModel @Inject constructor(
    application: Application,
    savedStateHandle: SavedStateHandle,
    getQuestionUseCase: GetQuestionUseCase,
    private val updateAlarmUseCase: UpdateAlarmUseCase,
    private val getAlarmByIdUseCase: GetAlarmByIdUseCase,
    private val snoozeAlarmUseCase: SnoozeAlarmUseCase
) :
    AndroidViewModel(application) {

    val question: LiveData<Question> = liveData { emit(getQuestionUseCase()) }
    val alarm: LiveData<Alarm?> = liveData(Dispatchers.IO) { emit(getAlarmByIdUseCase(savedStateHandle[EXTRA_ID] ?: 0)) }
    val time: LiveData<String?> = liveData { emit(savedStateHandle[EXTRA_TIME]) }

    fun updateAlarm(alarm: Alarm) = viewModelScope.launch(Dispatchers.IO) { updateAlarmUseCase(alarm) }

    fun snoozeAlarm() {
        alarm.value?.let { snoozeAlarmUseCase(getApplication<Application>().applicationContext, it) }
    }


}