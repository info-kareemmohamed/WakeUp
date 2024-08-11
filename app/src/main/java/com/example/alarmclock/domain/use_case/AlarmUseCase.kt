package com.example.alarmclock.domain.use_case

data class AlarmUseCase(
    val insertAlarmUseCase: InsertAlarmUseCase,
    val deleteAlarmUseCase: DeleteAlarmUseCase,
    val updateAlarmUseCase: UpdateAlarmUseCase,
    val getLastAlarmUseCase: GetLastAlarmUseCase,
    val getAlarmByIdUseCase: GetAlarmByIdUseCase,
    val getAlarmUseCase: GetAlarmUseCase,
    val getActiveAlarmUseCase: GetActiveAlarmUseCase,
    val getNotActiveAlarmUseCase: GetNotActiveAlarmUseCase
)