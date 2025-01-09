package com.engineer.mobiletrainer.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.engineer.mobiletrainer.R
import com.engineer.mobiletrainer.database.entity.Exercise
import com.engineer.mobiletrainer.database.entity.Plans
import com.engineer.mobiletrainer.fragments.PlanDetails
import kotlin.coroutines.coroutineContext

class ExercisesAdapter(var exercises: List<Exercise>): RecyclerView.Adapter<ExercisesAdapter.ExercisesViewHolder>() {
    inner class ExercisesViewHolder(view: View):RecyclerView.ViewHolder(view) {
        val textView: TextView

        init {
            textView = view.findViewById(R.id.textView)
            view.setOnClickListener{
                
            }
        }
    }
    
    var onItemClick : ((Exercise) -> Unit)? = null

    @SuppressLint("NotifyDataSetChanged")
    fun setFilteredList(list: List<Exercise>) {
        this.exercises = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExercisesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_item, parent, false)

        return ExercisesViewHolder(view)
    }

    override fun getItemCount(): Int {
        return exercises.size
    }

    override fun onBindViewHolder(holder: ExercisesViewHolder, position: Int) {
        holder.textView.text = exercises[position].name
        
        //Set onClickListener for item
        holder.itemView.setOnClickListener{
            onItemClick?.invoke(exercises[position])
        }
    }

}
