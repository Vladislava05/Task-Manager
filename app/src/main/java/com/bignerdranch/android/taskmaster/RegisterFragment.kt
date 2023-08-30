package com.bignerdranch.android.taskmaster

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.fragment.findNavController
import com.bignerdranch.android.taskmaster.TodoAdapter.Companion.LOGIN_FRAGMENT_URI
import com.bignerdranch.android.taskmaster.databinding.FragmentRegisterBinding


class RegisterFragment : Fragment(R.layout.fragment_register) {
    private var _binding: FragmentRegisterBinding? = null
    private val binding: FragmentRegisterBinding
        get() = checkNotNull(_binding)
    private lateinit var userDao: UserDAO
    private lateinit var userViewModel: UserViewModel
    private var users = mutableListOf<User>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val appDatabase = AppDatabase.getInstance(this.requireActivity())
        userDao = appDatabase.userDao()
        var userRepository = UserRepository(userDao)
        userViewModel = ViewModelProvider(this, UserViewModelFactory(userRepository)).get(UserViewModel::class.java)
        userViewModel.getAllUsers().observe(this, Observer { userList ->
            users.clear()
            users.addAll(userList)
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnRegister.setOnClickListener {
            var name = binding.name.text.toString()
            var surname = binding.surname.text.toString()
            var email = binding.emailAddress.text.toString()
            var password = binding.passwordRegister.text.toString()
            var newUser = User(id, email, password, name, surname)
           userViewModel.insert(newUser)
            users.add(newUser)
            Log.d("myApp", newUser.toString())
            val request = NavDeepLinkRequest.Builder
                .fromUri(LOGIN_FRAGMENT_URI.toUri())
                .build()
            findNavController().navigate(request)
        }
    }
}