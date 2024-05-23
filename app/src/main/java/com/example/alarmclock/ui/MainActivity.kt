package com.example.alarmclock.ui

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.alarmclock.R

import com.example.alarmclock.alarmmanager.AndroidAlarmScheduler
import com.example.alarmclock.alarmmanager.RestartAlarmsService
import com.example.alarmclock.databinding.ActivityMainBinding
import com.example.alarmclock.data.entity.Alarm
import com.example.alarmclock.recyclerview.RecyclerAdapter
import com.example.alarmclock.recyclerview.SwipeItem
import com.example.alarmclock.recyclerview.SwitchListener
import com.example.alarmclock.viewmodel.AlarmViewModel

class MainActivity : AppCompatActivity(), SwitchListener {
    private lateinit var viewModel: AlarmViewModel
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: RecyclerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_NO)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[AlarmViewModel::class.java]
        initialRecyclerView()
        setOnClickListenerToFloatingActionButton()
        getDataFromViewModel()
        swipe()
        setStatusBarIcon(true)

   //  startForegroundService(Intent(this, RestartAlarmsService::class.java))
    }


    private fun getDataFromViewModel() {

        viewModel.getAlarm().observe(this) { alarms ->
            adapter.setList(alarms)
        }

    }


//    private fun setDataToAlarmScheduler() {
//
//        AndriodAlarmScheduler(context = applicationContext).scheduler(
//            Alarm(
//                1,
//                "",
//                "Monday",
//                "03",
//                "05",
//                true,
//                R.drawable.ic_nightlight,
//                "AM"
//            )
//        )
//
//
//        AndriodAlarmScheduler(context = applicationContext).scheduler(
//            Alarm(
//                1,
//                "",
//                "Monday",
//                "03",
//                "05",
//                true,
//                R.drawable.ic_nightlight,
//                "AM"
//            )
//        )
//
//
//    }

    private fun setStatusBarIcon(enabled: Boolean) {
        val alarmChanged = Intent("android.intent.action.ALARM_CHANGED")
        alarmChanged.putExtra("alarmSet", enabled)
        sendBroadcast(alarmChanged)
    }


    fun setOnClickListenerToFloatingActionButton() {
        binding.MainFloatingActionButton.setOnClickListener {
            val intent: Intent = Intent(this, AlarmActivity::class.java)
            startActivity(intent)
        }

    }


    fun swipe() {
        val swipeItem = object : SwipeItem(this) {

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                if (direction == ItemTouchHelper.LEFT) {
                    val alarm: Alarm =
                        adapter.getAlarmFromPosition(viewHolder.bindingAdapterPosition)
                    AndroidAlarmScheduler(context = applicationContext).cancel(alarm)
                    viewModel.deleteAlarm(alarm)
                }
            }
        }


        ItemTouchHelper(swipeItem).attachToRecyclerView(binding.MainRecyclerView)

    }


    fun initialRecyclerView() {

        binding.MainRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.MainRecyclerView.setHasFixedSize(true)
        adapter = RecyclerAdapter(switchListener = this)
        binding.MainRecyclerView.adapter = adapter
    }

    override fun onClick(alarm: Alarm, isChecked: Boolean) {
        Log.d("SwitchStatus", "Switch is ${if (isChecked) "ON" else "OFF"}")
        if (isChecked) {
            AndroidAlarmScheduler(context = this).scheduler(
                alarm
            )
            alarm.active = true
        } else {
            AndroidAlarmScheduler(this).cancel(alarm)
            alarm.active = false
        }

        viewModel.updateAlarm(alarm)

    }
}