package com.bignerdranch.android.taskmaster

import android.app.DatePickerDialog
import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult

import com.bignerdranch.android.taskmaster.databinding.FragmentNewTaskSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.util.*



class NewTaskSheet() : BottomSheetDialogFragment(){

    private lateinit var binding: FragmentNewTaskSheetBinding
    private lateinit var todo: Todo




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewTaskSheetBinding.inflate(inflater,container,false)

        return binding.root
    }

    override fun onViewCreated(view:View,savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
            todo = Todo(title ="", description ="", isChecked = false, date ="")
            binding.date.setOnClickListener{
                val c = Calendar.getInstance()
                val year = c.get(Calendar.YEAR)
                val month = c.get(Calendar.MONTH)
                val day = c.get(Calendar.DAY_OF_MONTH)
                val datePicker = DatePickerDialog(this.requireContext(),DatePickerDialog.OnDateSetListener { view, year, month, day ->
                      binding.date.setText("" + day + "/" + (month +1)+ "/ " + year)

                }, year, month, day)
                datePicker.show()

            }



            binding.btnSave.setOnClickListener {
               todo.title=binding.title.text.toString()
               todo.date=binding.date.text.toString()
               binding.title.setText("")
               binding.description.setText("")
                binding.date.setText("")
               setFragmentResult("requestKey" , bundleOf("bundleKey" to todo))
                dismiss()

           }


    }




}