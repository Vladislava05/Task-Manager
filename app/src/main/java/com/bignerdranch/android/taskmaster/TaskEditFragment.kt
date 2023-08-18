package com.bignerdranch.android.taskmaster

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.fragment.findNavController
import com.bignerdranch.android.taskmaster.databinding.FragmentTaskEditBinding

class TaskEditFragment : Fragment() {
    private var _binding: FragmentTaskEditBinding? = null
    private val binding: FragmentTaskEditBinding
        get() = checkNotNull(_binding)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTaskEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val title = arguments?.getString(Constants.TASK_BUNDLE_KEY)
        val date = arguments?.getString(Constants.TASK_DATE_BUNDLE_KEY)
        val descr = arguments?.getString(Constants.TASK_DESCR_BUNDLE_KEY)
        binding.title.setText(title)
        binding.date.setText(date)
        binding.description.setText(descr)
        binding.btnSave.setOnClickListener {
            val request = NavDeepLinkRequest.Builder
                .fromUri("${TodoAdapter.TASK_DETAIL_URI}?${Constants.TASK_EDIT_BUNDLE_KEY}=${binding.title.text}&${Constants.TASK_EDIT_DESCR_BUNDLE_KEY}=${binding.description.text}&${Constants.TASK_EDIT_DATE_BUNDLE_KEY}=${binding.date.text}".toUri())
                .build()
            findNavController().navigate(request)
        }
    }

}