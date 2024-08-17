package com.example.alarmclock.presentation.alarm_screen.view_model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.liveData
import com.example.alarmclock.common.Constant.EXTRA_ALARM
import com.example.alarmclock.data.model.Alarm
import com.example.alarmclock.domain.use_case.CancelAlarmUseCase
import com.example.alarmclock.domain.use_case.GetLastAlarmUseCase
import com.example.alarmclock.domain.use_case.InsertAlarmUseCase
import com.example.alarmclock.domain.use_case.SchedulerAlarmUseCase
import com.example.alarmclock.domain.use_case.UpdateAlarmUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.util.Calendar
import javax.inject.Inject
@HiltViewModel
class AlarmViewModel @Inject constructor(
    application: Application,
    savedStateHandle: SavedStateHandle,
    private val insertAlarmUseCase: InsertAlarmUseCase,
    private val updateAlarmUseCase: UpdateAlarmUseCase,
    private val getLastAlarmUseCase: GetLastAlarmUseCase,
    private val cancelAlarmUseCase: CancelAlarmUseCase,
    private val schedulerAlarmUseCase: SchedulerAlarmUseCase,

    ) : AndroidViewModel(application) {
    private val calendar = Calendar.getInstance()

    private val _alarm = MutableLiveData<Alarm?>()
    val alarm: MutableLiveData<Alarm?> = _alarm

    val hour: LiveData<Int> = liveData {
        emit(calendar.get(Calendar.HOUR))
    }
    val minute: LiveData<Int> = liveData {
        emit(calendar.get(Calendar.MINUTE))
    }
    val timePeriod: LiveData<Int> = liveData {
        emit(calendar.get(Calendar.AM_PM))
    }


init {
    _alarm.value = savedStateHandle.get<Alarm>(EXTRA_ALARM)

}


    fun schedulerAlarm(alarm: Alarm) {
        if ( _alarm.value != null) cancelAlarmUseCase(_alarm.value!!, getApplication<Application>().applicationContext)
        schedulerAlarmUseCase(alarm, getApplication<Application>().applicationContext)
    }


    fun insertAlarm(alarm: Alarm): Long {
        return runBlocking {
            withContext(Dispatchers.IO) {
                insertAlarmUseCase(alarm)
            }
        }

    }
    fun getLastAlarm(): LiveData<Alarm?> = liveData(Dispatchers.IO) {
        emit(getLastAlarmUseCase())
    }

    fun updateAlarm(alarm: Alarm): Int {
        return runBlocking {
            withContext(Dispatchers.IO) {
                updateAlarmUseCase(alarm)
            }
        }
    }

}