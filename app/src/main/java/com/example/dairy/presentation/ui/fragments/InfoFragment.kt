package com.example.dairy.presentation.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.dairy.R
import com.example.dairy.databinding.FragmentInfoBinding
import com.example.dairy.domain.entity.TodoEntity
import com.example.dairy.presentation.utils.DateConverter
import com.example.dairy.presentation.viewmodel.InfoViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel

class InfoFragment : Fragment(R.layout.fragment_info) {

    private val binding by viewBinding(FragmentInfoBinding::bind)

    private val viewModel: InfoViewModel by viewModel()

    private val id: Int? by lazy {
        arguments?.getInt(getString(R.string.arg_todo_id))
    }

    private val dateConverter by lazy {
        DateConverter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnListeners()
        observeViewModel()
        id?.let {
            viewModel.onGetTodo(it)
        }
    }

    private fun btnListeners() {
        with(binding) {
            btnBack.setOnClickListener {
                openCalendar()
            }
            imgDelete.setOnClickListener {
                dialog()
            }
            imgEdit.setOnClickListener {
                val bundle = Bundle().apply {
                    id?.let { id -> putInt(getString(R.string.arg_todo_id), id) }
                }
                findNavController().navigate(R.id.action_infoFragment_to_editFragment, bundle)
            }
        }
    }

    private fun dialog() {
        AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.delete_todo_title))
            .setPositiveButton(getString(R.string.positive)) { _, _ ->
                id?.let { viewModel.onDeleteTodo(it) }
                openCalendar()
            }
            .setNegativeButton(getString(R.string.negative)) { dialog, _ ->
                dialog.dismiss()
            }
            .create()
            .show()
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
            with(binding) {
                tvName.text = if (todo.name.isNullOrEmpty()) {
                    getString(R.string.no_title)
                } else todo.name
                tvWhen.text = dateConverter.formatDateInfo(todo.dateStart, todo.dateFinish)
                tvDesc.text = if (todo.description.isNullOrEmpty()) {
                    getString(R.string.no_desc)
                } else todo.description
            }
        }
    }

    private fun openCalendar() {
        findNavController().navigate(R.id.action_infoFragment_to_calendarFragment)
    }
}