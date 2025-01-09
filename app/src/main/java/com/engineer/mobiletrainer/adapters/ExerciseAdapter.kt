package com.engineer.mobiletrainer.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.engineer.mobiletrainer.R
import com.engineer.mobiletrainer.database.entity.Exercise
import com.engineer.mobiletrainer.database.entity.Plans
import com.engineer.mobiletrainer.fragments.PlanDetails
import kotlin.coroutines.coroutineContext

class ExerciseAdapter(var exercises: List<Exercise>): RecyclerView.Adapter<ExerciseAdapter.ExerciseViewHolder>() {
    inner class ExerciseViewHolder(view: View):RecyclerView.ViewHolder(view) {
        val name: TextView
        val desc: TextView
        val deleteButton: ImageButton

        init {
            name = view.findViewById(R.id.recycler_view_exercise_name)
            desc = view.findViewById(R.id.recycler_view_exercise_desc)
            deleteButton = view.findViewById(R.id.recycler_view_excercise_delete)
            view.setOnClickListener{
            }
        }
    }
    
    var onItemClick : ((Exercise) -> Unit)? = null
    var onDeleteClick: ((Exercise) -> Unit)? = null

    @SuppressLint("NotifyDataSetChanged")
    fun setFilteredList(list: List<Exercise>) {
        this.exercises = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_exercise, parent, false)

        return ExerciseViewHolder(view)
    }

    override fun getItemCount(): Int {
        return exercises.size
    }


    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        holder.name.text = exercises[position].name
        holder.desc.text = exercises[position].desc
        holder.deleteButton.setOnClickListener {
            onDeleteClick?.invoke(exercises[position])
        }
        //Set onClickListener for item
        holder.itemView.setOnClickListener{
            onItemClick?.invoke(exercises[position])
        }
    }

}
