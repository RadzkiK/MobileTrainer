package com.engineer.mobiletrainer.adapters

import android.graphics.Color
import android.view.ViewGroup
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.toDrawable
import androidx.recyclerview.widget.RecyclerView
import com.engineer.mobiletrainer.R
import com.engineer.mobiletrainer.database.entity.Exercise
import com.engineer.mobiletrainer.database.entity.TrainingSession
import com.engineer.mobiletrainer.databinding.TrainingSessionCalendarItemViewBinding
import com.engineer.mobiletrainer.utils.layoutInflater
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class CalendarAdapter :
    RecyclerView.Adapter<CalendarAdapter.TrainingSessionViewHolder>() {
        val trainingSessions = mutableListOf<TrainingSession>()

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrainingSessionViewHolder {
            return TrainingSessionViewHolder(
                TrainingSessionCalendarItemViewBinding.inflate(parent.context.layoutInflater, parent, false),
            )
        }

        override fun onBindViewHolder(viewHolder: TrainingSessionViewHolder, position: Int) {
            viewHolder.bind(trainingSessions[position])
            viewHolder.binding.trainingSessionItemCompletedLayout.setOnClickListener(View.OnClickListener {
                onItemClick?.invoke(trainingSessions[position])
            })
        }

        override fun getItemCount(): Int = trainingSessions.size

        var onItemClick : ((TrainingSession) -> Unit)? = null

        inner class TrainingSessionViewHolder(val binding: TrainingSessionCalendarItemViewBinding) :
            RecyclerView.ViewHolder(binding.root) {
            fun bind(trainingSession: TrainingSession) {
                binding.trainingSessionItemDateTextview.apply {
                    text = LocalDateTime.ofInstant(Instant.ofEpochMilli(trainingSession.date!!), ZoneId.systemDefault()).format(
                        DateTimeFormatter.ofPattern("yyyy:MM:dd \n HH:mm"))
                }

                binding.trainingSessionItemPlanName.text = trainingSession.planName
                if(trainingSession.completed == true) {
                    binding.trainingSessionItemCompletedTextview.text = "Completed"
                    binding.trainingSessionItemCompletedTextview.setTextColor(Color.GREEN)
                    binding.trainingSessionItemCompletedImageview.setImageDrawable(ContextCompat.getDrawable(binding.root.context, R.drawable.check_icon_green))
                } else {
                    binding.trainingSessionItemCompletedTextview.text = "Not completed"
                    binding.trainingSessionItemCompletedTextview.setTextColor(Color.RED)
                    binding.trainingSessionItemCompletedImageview.setImageDrawable(ContextCompat.getDrawable(binding.root.context, R.drawable.x_icon_red))
                }
            }
        }
}