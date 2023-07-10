package com.example.dairy.presentation.ui.rv

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dairy.databinding.HourCellBinding
import com.example.dairy.domain.entity.CalendarCell
import com.example.dairy.presentation.utils.SpaceItemDecorator

class CellAdapter(
    private val list: List<CalendarCell>,
    private val context: Context,
    private val selectCell: (Int) -> Unit,
    private val selectTodo: (Int) -> Unit
) : RecyclerView.Adapter<CellAdapter.CellViewHolder>() {

    inner class CellViewHolder(
        private val binding: HourCellBinding,
        private val selectCell: (Int) -> Unit,
        private val selectTodo: (Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        private val spaceItemDecorator by lazy {
            SpaceItemDecorator(context)
        }

        fun bind(item: CalendarCell, position: Int) {
            binding.tvTime.text = item.hour
            with(binding.cell.rvTodoList) {
                adapter = TodoAdapter(item.todoListPerHour) {
                    selectTodo.invoke(it)
                }
                layoutManager =
                    LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                addItemDecoration(spaceItemDecorator)
            }
            binding.cell.root.setOnClickListener {
                selectCell.invoke(position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CellViewHolder {
        return CellViewHolder(
            HourCellBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            selectCell,
            selectTodo
        )
    }

    override fun onBindViewHolder(holder: CellViewHolder, position: Int) {
        holder.bind(list[position], position)
    }

    override fun getItemCount(): Int {
        return list.size
    }
}