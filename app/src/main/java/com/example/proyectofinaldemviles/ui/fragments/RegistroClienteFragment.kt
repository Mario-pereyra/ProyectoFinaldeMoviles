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

    // Inicializamos el ViewModel
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
                // Llamamos a la función del ViewModel
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
        // Observamos el LiveData del ViewModel
        registroViewModel.registroStatus.observe(viewLifecycleOwner) { response ->
            response?.let {
                if (it.success) {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                    // Si el registro es exitoso, navegamos a la pantalla de Login
                    findNavController().navigate(R.id.action_registroClienteFragment_to_loginFragment)
                } else {
                    // Si no, mostramos el mensaje de error de la API
                    Toast.makeText(requireContext(), "Error: ${it.message}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}