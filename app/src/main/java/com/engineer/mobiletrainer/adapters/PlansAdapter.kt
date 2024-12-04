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
import com.engineer.mobiletrainer.database.entity.Plans
import com.engineer.mobiletrainer.fragments.PlanDetails
import kotlin.coroutines.coroutineContext

class PlansAdapter(var plans: List<Plans>): RecyclerView.Adapter<PlansAdapter.PlansViewHolder>() {
    inner class PlansViewHolder(view: View):RecyclerView.ViewHolder(view) {
        val textView: TextView

        init {
            textView = view.findViewById(R.id.textView)
            view.setOnClickListener{
                
            }
        }
    }
    
    var onItemClick : ((Plans) -> Unit)? = null

    @SuppressLint("NotifyDataSetChanged")
    fun setFilteredList(list: List<Plans>) {
        this.plans = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlansViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_item, parent, false)

        return PlansViewHolder(view)
    }

    override fun getItemCount(): Int {
        return plans.size
    }

    override fun onBindViewHolder(holder: PlansViewHolder, position: Int) {
        holder.textView.text = plans[position].name
        
        //Set onClickListener for item
        holder.itemView.setOnClickListener{
            onItemClick?.invoke(plans[position])
        }
    }

}
