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

            var isValid = true
            val emailPattern = android.util.Patterns.EMAIL_ADDRESS

            // Validación de email
            if (email.isEmpty()) {
                binding.tilEmailLogin.error = "El email es obligatorio"
                isValid = false
            } else if (!emailPattern.matcher(email).matches()) {
                binding.tilEmailLogin.error = "Formato de email inválido"
                isValid = false
            } else {
                binding.tilEmailLogin.error = null
            }

            // Validación de contraseña
            if (password.isEmpty()) {
                binding.tilPasswordLogin.error = "La contraseña es obligatoria"
                isValid = false
            } else if (password.length < 6) {
                binding.tilPasswordLogin.error = "Mínimo 6 caracteres"
                isValid = false
            } else {
                binding.tilPasswordLogin.error = null
            }

            if (isValid) {
                loginViewModel.iniciarSesion(requireContext(), email, password)
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
                    findNavController().navigate(R.id.action_loginFragment_to_categoriasFragment)
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
        loginViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBarLogin.visibility = if (isLoading == true) View.VISIBLE else View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
