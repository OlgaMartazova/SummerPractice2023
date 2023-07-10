package com.example.dairy.presentation.ui.fragments

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.dairy.R
import com.example.dairy.databinding.FragmentCalendarBinding
import com.example.dairy.domain.entity.CalendarCell
import com.example.dairy.domain.entity.TodoEntity
import com.example.dairy.presentation.ui.rv.CellAdapter
import com.example.dairy.presentation.utils.DateConverter
import com.example.dairy.presentation.utils.createDatePicker
import com.example.dairy.presentation.utils.toCalendar
import com.example.dairy.presentation.viewmodel.CalendarViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.time.LocalDate
import java.time.LocalTime
import java.util.*

class CalendarFragment : Fragment(R.layout.fragment_calendar) {

    private val binding by viewBinding(FragmentCalendarBinding::bind)

    private val viewModel: CalendarViewModel by viewModel()

    private lateinit var cellAdapter: CellAdapter

    private val dateConverter by lazy {
        DateConverter()
    }

    private var selectedDate: LocalDate = LocalDate.now()
    private var calendarCells = mutableListOf<CalendarCell>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeViewModel()
        with(binding) {
            rvCells.run {
                cellAdapter = CellAdapter(
                    calendarCells,
                    requireContext(),
                    { createTodo(it) },
                    { openTodoInfo(it) },
                )
                adapter = cellAdapter
                addItemDecoration(DividerItemDecoration(requireContext(), RecyclerView.VERTICAL))
            }
            refresh()
            tvDate.setOnClickListener { dayPicker(toCalendar(selectedDate)) }
            btnPrevious.setOnClickListener {
                selectedDate = selectedDate.minusDays(1)
                refresh()
            }
            btnNext.setOnClickListener {
                selectedDate = selectedDate.plusDays(1)
                refresh()
            }
            fabAdd.setOnClickListener {
                createTodo(-1)
            }
        }
    }

    private fun dayPicker(selected: Calendar) {
        val dateSetListener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            selectedDate = LocalDate.of(year, month + 1, dayOfMonth)
            refresh()
        }
        val datePickerDialog = createDatePicker(requireContext(), selected, dateSetListener)
        datePickerDialog.datePicker.firstDayOfWeek = Calendar.MONDAY
        datePickerDialog.show()
    }


    private fun refresh() {
        binding.tvDate.text = dateConverter.dateAsString(selectedDate)
        viewModel.onGetTodoListByDay(selectedDate)
    }

    private fun observeViewModel() {

        viewModel.todoList.flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach { list ->
                updateTable(toCalendarCells(list))
            }
            .launchIn(lifecycleScope)
    }

    private fun updateTable(cells: List<CalendarCell>) {
        calendarCells.clear()
        calendarCells.addAll(cells)
        cellAdapter.notifyDataSetChanged()
    }

    private fun createTodo(cellId: Int) {
        val bundle = Bundle().apply {
            putInt(getString(R.string.arg_cell_id), cellId)
            putString(getString(R.string.arg_selected_date), selectedDate.toString())
        }
        findNavController().navigate(R.id.action_calendarFragment_to_editFragment, bundle)
    }

    private fun openTodoInfo(todoId: Int) {
        val bundle = Bundle().apply {
            putInt(getString(R.string.arg_todo_id), todoId)
        }
        findNavController().navigate(R.id.action_calendarFragment_to_infoFragment, bundle)
    }

    private fun toCalendarCells(todoList: List<TodoEntity>): List<CalendarCell> {
        val cells = mutableListOf<CalendarCell>()
        for (hour in 0..23) {
            val time = LocalTime.of(hour, 0)
            val todoListPerHour = mutableListOf<TodoEntity>()
            todoList.forEach {
                if (it.dateStart.hour == hour) todoListPerHour.add(it)
            }
            cells.add(CalendarCell(dateConverter.formatTime(time), todoListPerHour))
        }
        return cells
    }
}