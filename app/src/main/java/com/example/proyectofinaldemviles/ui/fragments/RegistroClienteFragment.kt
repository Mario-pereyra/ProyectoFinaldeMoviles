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
import com.example.proyectofinaldemviles.databinding.FragmentRegistroClienteBinding
import com.example.proyectofinaldemviles.viewmodels.RegistroClienteViewModel

class RegistroClienteFragment : Fragment() {

    private var _binding: FragmentRegistroClienteBinding? = null
    private val binding get() = _binding!!

    private val registroViewModel: RegistroClienteViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegistroClienteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupEventListeners()
        setupViewModelObservers()
    }

    private fun setupEventListeners() {
        binding.btnRegistro.setOnClickListener {
            val nombre = binding.txtNombreCliente.text.toString().trim()
            val apellido = binding.txtApellidoCliente.text.toString().trim()
            val email = binding.txtEmailCliente.text.toString().trim()
            val password = binding.txtPasswordCliente.text.toString().trim()

            if (nombre.isNotEmpty() && apellido.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
                registroViewModel.registrarCliente(nombre, apellido, email, password)
            } else {
                Toast.makeText(requireContext(), "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
            }
        }

        binding.lblIniciarSesion.setOnClickListener {
            findNavController().navigate(R.id.action_registroClienteFragment_to_loginFragment)
        }
    }

    private fun setupViewModelObservers() {
        // Observador para el registro exitoso
        registroViewModel.registroStatus.observe(viewLifecycleOwner) { response ->
            response?.let {
                // Si la respuesta no es nula, el registro fue exitoso
                if (!it.message.isNullOrBlank()) {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                }
                findNavController().navigate(R.id.action_registroClienteFragment_to_loginFragment)
            }
        }

        // Observador para los errores
        registroViewModel.error.observe(viewLifecycleOwner) { errorMessage ->
            // Solo muestra el Toast si el mensaje de error no es nulo ni está vacío
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