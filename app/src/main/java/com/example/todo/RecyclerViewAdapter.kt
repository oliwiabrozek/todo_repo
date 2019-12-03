package com.example.todo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

interface AdapterOnClick {
    fun onClick(item: TextView)
}

class RecyclerViewAdapter(val tasks: MutableList<Task>, val adapterOnClick: AdapterOnClick) :
    RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_task, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = tasks.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.idTextView.text = tasks[position].id.toString()
        holder.titleTextView.text = tasks[position].title
        holder.priorityTextView.text = tasks[position].priority
        holder.statusTextView.text = tasks[position].status
        holder.dateTextView.text = tasks[position].date
        holder.setItem()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val idTextView: TextView = itemView.findViewById(R.id.task_id)
        val titleTextView: TextView = itemView.findViewById(R.id.task_title)
        val priorityTextView: TextView = itemView.findViewById(R.id.task_priority)
        val statusTextView: TextView = itemView.findViewById(R.id.task_status)
        val dateTextView: TextView = itemView.findViewById(R.id.task_date)

        fun setItem() {
            idTextView.setOnClickListener {
                idTextView.setOnClickListener {
                    adapterOnClick.onClick(idTextView)
                }
            }
        }

    }
}