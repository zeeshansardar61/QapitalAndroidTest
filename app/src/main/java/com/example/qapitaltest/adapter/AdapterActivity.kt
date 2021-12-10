package com.example.qapitaltest.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.qapitaltest.R
import com.example.qapitaltest.entity.ActivitiesResponse
import com.example.qapitaltest.databinding.ItemActivityBinding

class AdapterActivity : RecyclerView.Adapter<ActivityViewHolder>() {

    private val list = ArrayList<ActivitiesResponse.ActivityModel>()

    lateinit var binding: ItemActivityBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActivityViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = DataBindingUtil.inflate(inflater, R.layout.item_activity, parent, false)
        return ActivityViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ActivityViewHolder, position: Int) {
        holder.binding.model = list[position]
        holder.binding.executePendingBindings()

    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun updateList(newList : List<ActivitiesResponse.ActivityModel>) {
        list.addAll(newList)
        notifyDataSetChanged()
    }
}




