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

    // Inicializamos el ViewModel
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
                // Llamamos a la función del ViewModel en lugar de mostrar un Toast
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
        // Observamos el LiveData del ViewModel
        loginViewModel.loginStatus.observe(viewLifecycleOwner) { response ->
            response?.let {
                // Si la API devuelve un token, el login fue exitoso
                if (!it.accessToken.isNullOrEmpty() ) {
                    Toast.makeText(requireContext(), "Inicio de sesión exitoso!", Toast.LENGTH_SHORT).show()
                    // Navegamos a la siguiente pantalla (usamos DetailFragment como ejemplo)
                    findNavController().navigate(R.id.action_loginFragment_to_detailFragment)
                } else {
                    // Si no, mostramos un mensaje de error
                    Toast.makeText(requireContext(), "Error: Email o contraseña incorrectos", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}