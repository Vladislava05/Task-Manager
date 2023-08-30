package com.bignerdranch.android.taskmaster

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.net.toUri
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.fragment.findNavController
import com.bignerdranch.android.taskmaster.databinding.FragmentLoginBinding


class LoginFragment : Fragment(R.layout.fragment_login) {
    private var _binding: FragmentLoginBinding? = null
    private val binding: FragmentLoginBinding
        get() = checkNotNull(_binding)
    private lateinit var userViewModel: UserViewModel
    private lateinit var userDao: UserDAO
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val appDatabase = AppDatabase.getInstance(this.requireActivity())
        userDao = appDatabase.userDao()
        var userRepository = UserRepository(userDao)
        userViewModel = ViewModelProvider(
            this,
            UserViewModelFactory(userRepository)
        ).get(UserViewModel::class.java)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.registerLink.setOnClickListener {
            val request = NavDeepLinkRequest.Builder
                .fromUri(TodoAdapter.REGISTER_FRAGMENT_URI.toUri())
                .build()
            findNavController().navigate(request)
        }
        binding.btnEnter.setOnClickListener {
            var email = binding.email.text.toString()
            var password = binding.password.text.toString()
            userViewModel.loadSingle(email, password) { user ->
                if (user != null) {
                    val request = NavDeepLinkRequest.Builder
                        .fromUri(TodoAdapter.TASK_LIST_URI.toUri())
                        .build()
                    findNavController().navigate(request)
                } else {
                    Toast.makeText(requireContext(), "Не удалось выполнить вход. Проверьте email и password.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}

