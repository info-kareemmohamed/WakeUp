package com.example.alarmclock.recyclerview

import android.os.Build
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
import com.example.alarmclock.data.entity.Alarm

class RecyclerAdapter(private var dataList: List<Alarm>) :
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

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.onBindView(dataList[position])
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cardView: CardView = itemView.findViewById(R.id.CardTime_Card)
        val time: TextView = itemView.findViewById(R.id.CardTime_Time)
        val days: TextView = itemView.findViewById(R.id.CardTime_days)
        val abbreviations: TextView = itemView.findViewById(R.id.CardTime_Abbreviations)
        val image: ImageView = itemView.findViewById(R.id.CardTime_Image)
        val switch: SwitchCompat = itemView.findViewById(R.id.CardTime_Switc)


        fun onBindView(alarm: Alarm) {
            time.text = "${alarm.hour}:${alarm.minute}"
            days.text = alarm.days
            abbreviations.text = alarm.abbreviations
            image.setImageResource(alarm.modeIcon)
            switch.isChecked = alarm.active
        }

    }


}