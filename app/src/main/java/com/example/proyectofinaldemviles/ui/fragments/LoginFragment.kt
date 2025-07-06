package com.example.proyectofinaldemviles.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.proyectofinaldemviles.R
import com.example.proyectofinaldemviles.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

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
    }

    private fun setupEventListeners() {
        binding.btnIniciarSesion.setOnClickListener {
            val email = binding.txtEmailLogin.text.toString()
            if (email.isNotEmpty()) {
                Toast.makeText(requireContext(), "Iniciando sesión...", Toast.LENGTH_SHORT).show()
                // Lógica de inicio de sesión...
            } else {
                Toast.makeText(requireContext(), "Por favor, ingresa tu email", Toast.LENGTH_SHORT).show()
            }
        }

        binding.lblIrARegistro.setOnClickListener {
            // Navegamos hacia el fragmento de registro
            findNavController().navigate(R.id.action_loginFragment_to_registroClienteFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}