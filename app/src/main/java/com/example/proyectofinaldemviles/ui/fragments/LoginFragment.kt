package com.example.proyectofinaldemviles.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.proyectofinaldemviles.R
import com.example.proyectofinaldemviles.databinding.FragmentLoginBinding
import com.example.proyectofinaldemviles.viewmodels.LoginViewModel

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupEventListeners()
        setupViewModelObservers()
    }

    private fun setupEventListeners() {
        binding.btnIniciarSesion.setOnClickListener {
            val email = binding.txtEmailLogin.text.toString().trim()
            val password = binding.txtPasswordLogin.text.toString().trim()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                loginViewModel.iniciarSesion(email, password)
            } else {
                Toast.makeText(requireContext(), "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
            }
        }

        binding.lblIrARegistro.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registroClienteFragment)
        }
    }

    private fun setupViewModelObservers() {
        loginViewModel.loginStatus.observe(viewLifecycleOwner) { response ->
            response?.let {
                if (!it.accessToken.isNullOrEmpty()) {
                    Toast.makeText(requireContext(), "¡Inicio de sesión exitoso!", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_loginFragment_to_detailFragment)
                } else {
                    // Este caso puede no ser necesario si el error se maneja por separado
                }
            }
        }

        loginViewModel.error.observe(viewLifecycleOwner) { errorMessage ->
            if (!errorMessage.isNullOrBlank()) {
                Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
