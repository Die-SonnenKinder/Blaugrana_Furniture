package com.example.blaugranafurniture.fragments.loginRegister

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.blaugranafurniture.R
import com.example.blaugranafurniture.data.User
import com.example.blaugranafurniture.databinding.FragmentRegisterBinding
import com.example.blaugranafurniture.util.Resource
import com.example.blaugranafurniture.viewmodel.RegisterViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectIndexed
import kotlinx.coroutines.launch

private val TAG = "Register Fragment"
@AndroidEntryPoint
class RegisterFragment: Fragment() {
    private lateinit var binding: FragmentRegisterBinding
    private val viewModel by viewModels<RegisterViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(inflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            buttonRegisterRegister.setOnClickListener{
                val user = User(
                    edFirstNameRegister.text.toString().trim(),
                    edLastNameRegister.text.toString().trim(),
                    edEmailLoginRegister.text.toString().trim(),
                )
                val password = edPasswordLoginRegister.text.toString()
                viewModel.createAccountWithEmailAndPassword(user, password)
            }
        }

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.register.collect {
                    when (it) {
                        is Resource.Loading -> {
                            binding.buttonRegisterRegister.startAnimation()
                        }
                        is Resource.Success -> {
                            Log.d("Test", it.message.toString())
                            binding.buttonRegisterRegister.revertAnimation()
                        }
                        is Resource.Error -> {
                            Log.e(TAG, it.message.toString())
                            binding.buttonRegisterRegister.revertAnimation()
                        }
                        else -> Unit
                    }
                }
            }
        }

    }
}