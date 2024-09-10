package com.example.alarmclock.presentation.home_screen.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.alarmclock.core.Constant
import com.example.alarmclock.data.model.Alarm
import com.example.alarmclock.databinding.ActivityMainBinding
import com.example.alarmclock.presentation.alarm_screen.ui.AlarmActivity
import com.example.alarmclock.presentation.home_screen.recyclerview.CardListener
import com.example.alarmclock.presentation.home_screen.recyclerview.RecyclerAdapter
import com.example.alarmclock.presentation.home_screen.recyclerview.SwipeItem
import com.example.alarmclock.presentation.home_screen.recyclerview.SwitchListener
import com.example.alarmclock.presentation.home_screen.view_model.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), SwitchListener, CardListener {

    private val viewModel: HomeViewModel by viewModels()
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val adapter by lazy { RecyclerAdapter(switchListener = this, cardListener = this) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        installSplashScreen()
        setContentView(binding.root)
        setupUI()
    }

    private fun setupUI() {
        viewModel.getAlarm().observe(this) { adapter.setList(it) }
        binding.MainRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            setHasFixedSize(true)
            adapter = this@MainActivity.adapter
        }
        binding.MainFloatingActionButton.setOnClickListener { startActivity(Intent(this, AlarmActivity::class.java)) }
        attachSwipeHandler()
    }


    private fun attachSwipeHandler() {
        val swipeItem = object : SwipeItem(this) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                if (direction == ItemTouchHelper.LEFT) {
                    val alarm: Alarm =
                        adapter.getAlarmFromPosition(viewHolder.bindingAdapterPosition)
                    viewModel.cancelAlarm(alarm)
                    viewModel.deleteAlarm(alarm)
                }
            }
        }
        ItemTouchHelper(swipeItem).attachToRecyclerView(binding.MainRecyclerView)
    }

    override fun onClick(alarm: Alarm, isChecked: Boolean) {
        viewModel.updateAlarm(alarm.apply { active = isChecked })
        if (isChecked) viewModel.schedulerAlarm(alarm) else viewModel.cancelAlarm(alarm)
    }

    override fun onClick(alarm: Alarm) {
        startActivity(Intent(this, AlarmActivity::class.java).putExtra(Constant.EXTRA_ALARM, alarm))
    }
}

