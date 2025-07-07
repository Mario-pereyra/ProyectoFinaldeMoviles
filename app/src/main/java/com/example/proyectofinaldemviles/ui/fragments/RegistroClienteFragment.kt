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

            var isValid = true
            val emailPattern = android.util.Patterns.EMAIL_ADDRESS

            // Validación de nombre
            if (nombre.isEmpty()) {
                binding.tilNombreCliente.error = "El nombre es obligatorio"
                isValid = false
            } else {
                binding.tilNombreCliente.error = null
            }

            // Validación de apellido
            if (apellido.isEmpty()) {
                binding.tilApellidoCliente.error = "El apellido es obligatorio"
                isValid = false
            } else {
                binding.tilApellidoCliente.error = null
            }

            // Validación de email
            if (email.isEmpty()) {
                binding.tilEmailCliente.error = "El email es obligatorio"
                isValid = false
            } else if (!emailPattern.matcher(email).matches()) {
                binding.tilEmailCliente.error = "Formato de email inválido"
                isValid = false
            } else {
                binding.tilEmailCliente.error = null
            }

            // Validación de contraseña
            if (password.isEmpty()) {
                binding.tilPasswordCliente.error = "La contraseña es obligatoria"
                isValid = false
            } else if (password.length < 6) {
                binding.tilPasswordCliente.error = "Mínimo 6 caracteres"
                isValid = false
            } else {
                binding.tilPasswordCliente.error = null
            }

            if (isValid) {
                registroViewModel.registrarCliente(nombre, apellido, email, password)
            }
        }

        binding.lblIniciarSesion.setOnClickListener {
            findNavController().navigate(R.id.action_registroClienteFragment_to_loginFragment)
        }
    }

    private fun setupViewModelObservers() {
        registroViewModel.registroStatus.observe(viewLifecycleOwner) { response ->
            response?.let {
                Toast.makeText(requireContext(), "Registro exitoso para ${it.name}", Toast.LENGTH_LONG).show()
                findNavController().navigate(R.id.action_registroClienteFragment_to_loginFragment)
            }
        }
        registroViewModel.error.observe(viewLifecycleOwner) { errorMsg ->
            if (!errorMsg.isNullOrBlank()) {
                Toast.makeText(requireContext(), errorMsg, Toast.LENGTH_LONG).show()
            }
        }
        registroViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBarRegistro.visibility = if (isLoading == true) View.VISIBLE else View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
