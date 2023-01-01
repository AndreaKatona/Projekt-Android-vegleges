package com.zoltanlorinczi.project_retrofit.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.zoltanlorinczi.project_retorfit.R
import com.zoltanlorinczi.project_retrofit.api.model.TaskResponse
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class TasksListAdapter(
    private var list: ArrayList<TaskResponse>,
    private val context: Context,
    private val listener: OnItemClickListener,
    private val listener2: OnItemLongClickListener
) :
    RecyclerView.Adapter<TasksListAdapter.SimpleDataViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    interface OnItemLongClickListener {
        fun onItemLongClick(position: Int)
    }

    open inner class SimpleDataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener, View.OnLongClickListener {

        override fun onClick(v: View?) {
            TODO("Not yet implemented")
        }

        override fun onLongClick(v: View?): Boolean {
            TODO("Not yet implemented")
        }
    }

    // 1. user defined ViewHolder type - Embedded class!
    inner class DataViewHolder(itemView: View) : SimpleDataViewHolder(itemView),
        View.OnClickListener, View.OnLongClickListener {
        val taskTitleTextView: TextView = itemView.findViewById(R.id.my_task_title_view)
        val taskDescriptionTextView: TextView = itemView.findViewById(R.id.my_task_description_view)
        val taskDeadlineTextView: TextView = itemView.findViewById(R.id.my_task_deadline_view)
        val taskPriorityView: TextView = itemView.findViewById(R.id.my_task_priority_view)
        val progressBar : ProgressBar = itemView.findViewById(R.id.progressBar)
        val progress : TextView = itemView.findViewById(R.id.progress)
        val button: Button = itemView.findViewById(R.id.my_task_button_status)

        init {
            itemView.setOnClickListener(this)
            itemView.setOnLongClickListener(this)
        }

        override fun onClick(p0: View?) {
            val currentPosition = this.adapterPosition
            listener.onItemClick(currentPosition)

        }

        override fun onLongClick(p0: View?): Boolean {
            val currentPosition = this.adapterPosition
            listener2.onItemLongClick(currentPosition)
            return true
        }
    }

    // 2. Called only a few times = number of items on screen + a few more ones
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpleDataViewHolder {
        return when (viewType) {
            TaskListItemType.SIMPLE.value -> {
                val itemView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.simple_task_list_item, parent, false)
                SimpleDataViewHolder(itemView)
            }
            TaskListItemType.COMPLEX.value -> {
                val itemView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.tasks_list_item, parent, false)
                DataViewHolder(itemView)
            }
            else -> {
                throw IllegalStateException("Type is not supported!")
            }
        }

    }

    override fun getItemViewType(position: Int): Int {

        return TaskListItemType.COMPLEX.value

    }

    // 3. Called many times, when we scroll the list
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: SimpleDataViewHolder, position: Int) {
        if (getItemViewType(position) == TaskListItemType.COMPLEX.value) {
            val complexHolder = (holder as DataViewHolder)
            val currentItem = list[position]
            val statusValue = currentItem.status

            complexHolder.taskTitleTextView.text = currentItem.title
            complexHolder.taskDescriptionTextView.text = currentItem.description
            complexHolder.progressBar.progress = currentItem.progress
            complexHolder.progress.text = "${currentItem.progress}%"

            when (currentItem.priority) {
                0 -> {
                    //complexHolder.taskPriorityTextView.setBackgroundColor(Color.RED)
                    complexHolder.taskPriorityView.text = "URGENT"
                }
                1 -> {
                    //complexHolder.taskPriorityTextView.setBackgroundColor(Color.YELLOW)
                    complexHolder.taskPriorityView.text = "NON-CRITICAL"
                }
                2 -> {
                    //complexHolder.taskPriorityTextView.setBackgroundColor(Color.GREEN)
                    complexHolder.taskPriorityView.text = "LOW"
                }
            }


            when(statusValue)
            {
                0 -> {
                    complexHolder.button.text = "New"
                    complexHolder.button.setBackgroundColor(Color.GREEN)
                }
                1 -> {
                    complexHolder.button.text = "In progress"
                    complexHolder.button.setBackgroundColor(Color.GRAY)
                }
                2 -> {
                    complexHolder.button.text = "Done"
                    complexHolder.button.setBackgroundColor(Color.BLUE)
                }
                3 -> {
                    complexHolder.button.text = "Blocked"
                    complexHolder.button.setBackgroundColor(Color.RED)
                }

            }
            val simpleDateFormat = SimpleDateFormat("dd MM yyy, HH:mm", Locale.ENGLISH)
            if(currentItem.deadline.toString()=="0")
            {
                holder.taskDeadlineTextView.text = "No deadline specified"
            }
            else
            {
                holder.taskDeadlineTextView.text = simpleDateFormat.format(currentItem.deadline*1000)
            }



        }
    }

    override fun getItemCount() = list.size

    // Update the list
    fun setData(newList: ArrayList<TaskResponse>) {
        list = newList
    }

    private enum class TaskListItemType(val value: Int) {
        SIMPLE(0),
        COMPLEX(1)
    }
}