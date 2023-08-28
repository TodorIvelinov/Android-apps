package com.example.todolist.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.R
import com.example.todolist.database.Task

class TodoAdapter(private var items: List<Task>, private val onItemClickListener: IOnItemClick) :
	RecyclerView.Adapter<TodoAdapter.ViewHolder>() {

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
		LayoutInflater.from(parent.context).inflate(R.layout.todo_task_item, parent, false)
	)

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {
		val item = items[position]
		with(holder) {
			todoTaskText.text = item.text
			todoTaskStatus.text = if (item.isCompleted) "complete" else "not complete"
			val todoTaskStatusColor = ContextCompat.getColor(
				/* context = */ holder.todoTaskText.context,
				/* id = */ if (item.isCompleted) R.color.electric_green else R.color.black
			)
			todoTaskStatus.setTextColor(todoTaskStatusColor)
			todoTaskContainer.setOnClickListener {
				onItemClickListener.onItemClick(item)
			}
		}
	}

	override fun getItemCount(): Int = items.size

	@SuppressLint("NotifyDataSetChanged")
	fun updateData(list: List<Task>) {
		items = list
		notifyDataSetChanged()
	}

	inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

		val todoTaskText: TextView = itemView.findViewById(R.id.todo_task_text)
		val todoTaskStatus: TextView = itemView.findViewById(R.id.todo_task_status)
		val todoTaskContainer: ConstraintLayout = itemView.findViewById(R.id.todo_task_container)
	}
}
