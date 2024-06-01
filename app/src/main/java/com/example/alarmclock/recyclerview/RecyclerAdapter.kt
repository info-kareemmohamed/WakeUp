package com.example.alarmclock.recyclerview

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.widget.SwitchCompat
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.alarmclock.R

import com.example.alarmclock.data.alarm.entity.Alarm

class RecyclerAdapter(
    private var dataList: List<Alarm> = emptyList(),
    private val switchListener: SwitchListener,
    private val cardListener: CardListener
) :
    RecyclerView.Adapter<RecyclerAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.card_activate_or_close_time, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    fun setList(dataList: List<Alarm>) {
        this.dataList = dataList
        notifyDataSetChanged()
    }


    fun getAlarmFromPosition(position: Int): Alarm {
        return this.dataList[position]
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.onBindView(dataList[position])
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cardView: CardView = itemView.findViewById(R.id.CardTime_Card)
        val time: TextView = itemView.findViewById(R.id.CardTime_Time)
        val days: TextView = itemView.findViewById(R.id.CardTime_days)
        val timePeriod: TextView = itemView.findViewById(R.id.CardTime_Abbreviations)
        val image: ImageView = itemView.findViewById(R.id.CardTime_Image)
        val switch: SwitchCompat = itemView.findViewById(R.id.CardTime_Switc)
        private lateinit var alarm: Alarm;

        init {
            switchOnClick()
            cardOnClick()
        }

        fun onBindView(alarm: Alarm) {
            time.text = handleTime(alarm.hour.toInt(), alarm.minute.toInt())
            days.text = alarm.getDaysList(alarm.getDaysOfWeek())
            timePeriod.text = alarm.timePeriod
            image.setImageResource(alarm.modeIcon)
            switch.isChecked = alarm.active
            this.alarm = alarm
        }


        private fun handleTime(hourOfDay: Int, minuteOfDay: Int): String {
            val hour12 = if (hourOfDay % 12 == 0) 12 else hourOfDay % 12
            val formattedHour = String.format("%02d", hour12)
            val formattedMinute = String.format("%02d", minuteOfDay)
            return "$formattedHour:$formattedMinute"
        }

        private fun switchOnClick() {
            switch.setOnClickListener {
                switchListener.onClick(alarm, switch.isChecked)
            }
        }

        private fun cardOnClick() {
            cardView.setOnClickListener {
                cardListener.onClick(alarm)
            }
        }


    }

}