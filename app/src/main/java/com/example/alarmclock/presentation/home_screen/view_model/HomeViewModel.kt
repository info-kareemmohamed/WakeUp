package com.example.alarmclock.presentation.home_screen.view_model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.alarmclock.data.model.Alarm
import com.example.alarmclock.domain.use_case.CancelAlarmUseCase
import com.example.alarmclock.domain.use_case.DeleteAlarmUseCase
import com.example.alarmclock.domain.use_case.GetAlarmUseCase
import com.example.alarmclock.domain.use_case.SchedulerAlarmUseCase
import com.example.alarmclock.domain.use_case.UpdateAlarmUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    application: Application,
    private val schedulerAlarmUseCase: SchedulerAlarmUseCase,
    private val cancelAlarmUseCase: CancelAlarmUseCase,
    private val deleteAlarmUseCase: DeleteAlarmUseCase,
    private val updateAlarmUseCase: UpdateAlarmUseCase,
    private val getAlarmUseCase: GetAlarmUseCase
) : AndroidViewModel(application) {

    fun getAlarm(): LiveData<List<Alarm>> = getAlarmUseCase()


    fun deleteAlarm(alarm: Alarm) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteAlarmUseCase(alarm)
        }
    }

    fun updateAlarm(alarm: Alarm){
        viewModelScope.launch(Dispatchers.IO) {
                updateAlarmUseCase(alarm)
            }
    }

    fun schedulerAlarm(alarm: Alarm) {
        schedulerAlarmUseCase(alarm, getApplication<Application>().applicationContext)
    }

    fun cancelAlarm(alarm: Alarm) {
        cancelAlarmUseCase(alarm, getApplication<Application>().applicationContext)

    }


}