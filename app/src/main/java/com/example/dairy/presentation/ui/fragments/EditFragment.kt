package com.example.dairy.presentation.ui.fragments

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.dairy.R
import com.example.dairy.databinding.FragmentEditBinding
import com.example.dairy.domain.entity.TodoEntity
import com.example.dairy.presentation.utils.DateConverter
import com.example.dairy.presentation.utils.createDatePicker
import com.example.dairy.presentation.utils.createTimePicker
import com.example.dairy.presentation.utils.toCalendar
import com.example.dairy.presentation.viewmodel.EditViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.*

class EditFragment : Fragment(R.layout.fragment_edit) {

    private val binding by viewBinding(FragmentEditBinding::bind)

    private val viewModel: EditViewModel by viewModel()

    private val cellId: Int? by lazy {
        arguments?.getInt(getString(R.string.arg_cell_id))
    }

    private val editTodoId: Int? by lazy {
        arguments?.getInt(getString(R.string.arg_todo_id))
    }

    private val selectedDateString: String? by lazy {
        arguments?.getString(getString(R.string.arg_selected_date))
    }

    private val dateConverter by lazy {
        DateConverter()
    }

    private lateinit var dateTimeStart: LocalDateTime
    private lateinit var dateTimeFinish: LocalDateTime

    private var selectedDate = LocalDate.now()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeViewModel()
        editTodoId?.let {
            if (it != 0) {
                viewModel.onGetTodo(it)
            }
        }

        binding.tvInfo.text = if (editTodoId == 0) {
            getString(R.string.new_todo)
        } else getString(R.string.edit_todo)

        selectedDateString?.let {
            selectedDate = dateConverter.toLocalDate(it)
        }

        cellId?.let {
            if (cellId == -1) {
                dateTimeStart = LocalDateTime.of(
                    selectedDate,
                    LocalTime.of(LocalTime.now().hour, LocalTime.now().minute)
                )
                dateTimeFinish =
                    LocalDateTime.of(selectedDate, dateTimeStart.toLocalTime().plusHours(1))
            } else {
                dateTimeStart = LocalDateTime.of(selectedDate, LocalTime.of(it, 0))
                dateTimeFinish =
                    LocalDateTime.of(selectedDate, dateTimeStart.toLocalTime().plusHours(1))
            }
        }

        setStartField()
        setFinishField()
        chooseTime()

        binding.btnSubmit.setOnClickListener {
            submitTodo()
        }
        binding.btnCancel.setOnClickListener {
            openCalendar()
        }
    }

    private fun submitTodo() {
        with(binding) {
            val name = etName.text?.toString()
            val desc = etDesc.text?.toString()
            viewModel.onCreateTodo(
                TodoEntity(
                    editTodoId ?: 0,
                    dateTimeStart,
                    dateTimeFinish,
                    name,
                    desc
                )
            )
            openCalendar()
        }
    }

    private fun observeViewModel() {
        viewModel.todo.flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach { todo ->
                setData(todo)
            }
            .launchIn(lifecycleScope)
    }

    private fun setData(todo: TodoEntity?) {
        todo?.let {
            dateTimeStart = todo.dateStart
            dateTimeFinish = todo.dateFinish
            setStartField()
            setFinishField()
            binding.etName.setText(todo.name)
            binding.etDesc.setText(todo.description)
        }
    }

    private fun setStartField() {
        setTimeField(binding.tvFrom, dateTimeStart)
    }

    private fun setFinishField() {
        setTimeField(binding.tvTo, dateTimeFinish)
    }

    private fun chooseTime() {
        with(binding) {
            tvFrom.setOnClickListener {
                datePicker(
                    toCalendar(dateTimeStart.toLocalDate()),
                    toCalendar(dateTimeStart.toLocalTime())
                ) {
                    dateTimeStart = it
                    setStartField()
                    if (dateTimeStart > dateTimeFinish) {
                        dateTimeFinish = dateTimeStart
                        setFinishField()
                    }
                }
            }
            tvTo.setOnClickListener {
                datePicker(
                    toCalendar(dateTimeFinish.toLocalDate()),
                    toCalendar(dateTimeFinish.toLocalTime())
                ) {
                    dateTimeFinish = if (it < dateTimeStart) dateTimeStart else it
                    setFinishField()
                }
            }
        }
    }

    private fun datePicker(
        selectedDate: Calendar,
        selectedTime: Calendar,
        dateTimePicked: (LocalDateTime) -> Unit
    ) {
        val dateSetListener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            timePicker(selectedTime) {
                LocalDateTime.of(LocalDate.of(year, month + 1, dayOfMonth), it)
                    .also { result ->
                        dateTimePicked.invoke(result)
                    }
            }
        }
        createDatePicker(requireContext(), selectedDate, dateSetListener).apply {
            datePicker.firstDayOfWeek = Calendar.MONDAY
            show()
        }
    }

    private fun timePicker(
        selectedTime: Calendar,
        timePicked: (LocalTime) -> Unit
    ) {
        val timeSetListener = TimePickerDialog.OnTimeSetListener { _, hour, minute ->
            LocalTime.of(hour, minute).also { timePicked.invoke(it) }
        }
        createTimePicker(requireContext(), selectedTime, timeSetListener).show()
    }

    private fun openCalendar() {
        findNavController().navigate(R.id.action_editFragment_to_calendarFragment)
    }

    @SuppressLint("SetTextI18n")
    private fun setTimeField(tv: TextView, dateTime: LocalDateTime) {
        tv.text =
            "${
                dateConverter.dateAsString(
                    dateTime.toLocalDate(),
                    setYear = true,
                    fullDayOfWeek = false
                )
            } ${dateConverter.formatTime(dateTime.toLocalTime())}"
    }
}