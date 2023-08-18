package com.bignerdranch.android.taskmaster

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.fragment.findNavController
import com.bignerdranch.android.taskmaster.Constants.TASK_BUNDLE_KEY
import com.bignerdranch.android.taskmaster.Constants.TASK_DATE_BUNDLE_KEY
import com.bignerdranch.android.taskmaster.Constants.TASK_DESCR_BUNDLE_KEY
import com.bignerdranch.android.taskmaster.Constants.TASK_EDIT_BUNDLE_KEY
import com.bignerdranch.android.taskmaster.Constants.TASK_EDIT_DATE_BUNDLE_KEY
import com.bignerdranch.android.taskmaster.Constants.TASK_EDIT_DESCR_BUNDLE_KEY
import com.bignerdranch.android.taskmaster.TodoAdapter.Companion.TASK_LIST_URI
import com.bignerdranch.android.taskmaster.databinding.FragmentTaskDetailBinding


class TaskDetailFragment : Fragment() {
    private var _binding: FragmentTaskDetailBinding? = null
    private val binding: FragmentTaskDetailBinding
        get() = checkNotNull(_binding)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTaskDetailBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val title = arguments?.getString(TASK_BUNDLE_KEY)
        val date = arguments?.getString(TASK_DATE_BUNDLE_KEY)
        val descr = arguments?.getString(TASK_DESCR_BUNDLE_KEY)
        Log.d("MyApp", descr.toString())
        binding.taskTitle.text = title
        binding.selectedDate.text = date
        binding.selectedDescription.text = descr

        binding.btnBack.setOnClickListener {
            val request = NavDeepLinkRequest.Builder
                .fromUri(TASK_LIST_URI.toUri())
                .build()
            findNavController().navigate(request)
        }
        binding.btnEdit.setOnClickListener {
            val request = NavDeepLinkRequest.Builder
                .fromUri("${TodoAdapter.TASK_EDIT_URI}?${TASK_BUNDLE_KEY}=${title}&${TASK_DESCR_BUNDLE_KEY}=${descr}&${TASK_DATE_BUNDLE_KEY}=${date}".toUri())
                .build()
            findNavController().navigate(request)
        }

    }
}