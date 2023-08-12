package com.bignerdranch.android.taskmaster

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bignerdranch.android.taskmaster.Constants.TASK_BUNDLE_KEY
import com.bignerdranch.android.taskmaster.Constants.TASK_DATE_BUNDLE_KEY
import com.bignerdranch.android.taskmaster.Constants.TASK_DESCR_BUNDLE_KEY
import com.bignerdranch.android.taskmaster.databinding.FragmentTaskDetailBinding


class TaskDetailFragment : Fragment() {
    private var _binding: FragmentTaskDetailBinding? = null
    private val binding: FragmentTaskDetailBinding
        get() = checkNotNull(_binding)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val title = arguments?.getString(TASK_BUNDLE_KEY)
        val date = arguments?.getString(TASK_DATE_BUNDLE_KEY)
        val descr = arguments?.getString(TASK_DESCR_BUNDLE_KEY)
        binding.taskTitle.setText(title.toString())
        binding.selectedDate.setText(date.toString())
        binding.selectedDescription.setText(descr.toString())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTaskDetailBinding.inflate(inflater, container, false)
        return binding.root

    }
}