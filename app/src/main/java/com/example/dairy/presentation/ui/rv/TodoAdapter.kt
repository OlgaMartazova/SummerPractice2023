package com.example.dairy.presentation.ui.rv

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dairy.R
import com.example.dairy.databinding.ItemTodoBinding
import com.example.dairy.domain.entity.TodoEntity
import com.example.dairy.presentation.utils.DateConverter

class TodoAdapter(
    private val list: List<TodoEntity>,
    private val selectItem: (Int) -> Unit
) : RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {

    private val dateConverter by lazy {
        DateConverter()
    }

    inner class TodoViewHolder(
        private val binding: ItemTodoBinding,
        private val selectItem: (Int) -> Unit,
        private val context: Context
    ) : RecyclerView.ViewHolder(binding.root) {

        private var todo: TodoEntity? = null

        init {
            itemView.setOnClickListener {
                todo?.id?.also { id ->
                    selectItem.invoke(id)
                }
            }
        }

        fun bind(item: TodoEntity) {
            todo = item
            binding.tvName.text = if (item.name.isNullOrEmpty()) {
                context.getString(R.string.no_title)
            } else item.name
            binding.tvTime.text =
                "${dateConverter.formatTime(item.dateStart.toLocalTime())} - ${
                    dateConverter.formatTime(
                        item.dateFinish.toLocalTime()
                    )
                }"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        return TodoViewHolder(
            ItemTodoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            selectItem,
            parent.context
        )
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }
}