package com.example.alarmclock.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.alarmclock.data.model.Alarm
import com.example.alarmclock.data.model.Question
import com.example.alarmclock.domain.use_case.AlarmUseCase
import com.example.alarmclock.domain.use_case.GetQuestionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AlarmViewModel @Inject constructor(
    private val alarmUseCase: AlarmUseCase,
    private val getQuestionUseCase: GetQuestionUseCase
) : ViewModel() {


    fun insertAlarm(alarm: Alarm): Long {

        return runBlocking {
            withContext(Dispatchers.IO) {
                alarmUseCase.insertAlarmUseCase(alarm)
            }
        }

    }

    fun deleteAlarm(alarm: Alarm) {

        viewModelScope.launch(Dispatchers.IO) {
            alarmUseCase.deleteAlarmUseCase(alarm)
        }
    }


    fun updateAlarm(alarm: Alarm): Int {
        return runBlocking {
            withContext(Dispatchers.IO) {
                alarmUseCase.updateAlarmUseCase(alarm)
            }
        }
    }

    fun getAlarm(): LiveData<List<Alarm>> = alarmUseCase.getAlarmUseCase()

    fun getActiveAlarm(): List<Alarm> = alarmUseCase.getActiveAlarmUseCase()
    fun getNotActiveAlarm(): LiveData<List<Alarm>> = alarmUseCase.getNotActiveAlarmUseCase()
    fun getLastAlarm(): LiveData<Alarm?> = liveData(Dispatchers.IO) {
        emit(alarmUseCase.getLastAlarmUseCase())
    }

    fun getAlarmById(id: Int): Alarm {
        return runBlocking {
            withContext(Dispatchers.IO) {
                alarmUseCase.getAlarmByIdUseCase(id)
            }
        }
    }

    fun getQuestion(): Question = getQuestionUseCase()

}