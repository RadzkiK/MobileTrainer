package com.engineer.mobiletrainer

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.engineer.mobiletrainer.database.entity.Plans

class PlansAdapter(val plans: List<Plans>): RecyclerView.Adapter<PlansAdapter.PlansViewHolder>() {
    inner class PlansViewHolder(view: View):RecyclerView.ViewHolder(view) {
        val textView: TextView

        init {
            textView = view.findViewById(R.id.textView)
        }
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
    }
}