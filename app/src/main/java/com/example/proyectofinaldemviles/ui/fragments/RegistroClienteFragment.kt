package com.example.proyectofinaldemviles.ui.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.proyectofinaldemviles.databinding.FragmentRegistroClienteBinding

class RegistroClienteFragment : Fragment() {

    private var _binding: FragmentRegistroClienteBinding? = null
    private val binding get() = _binding!!

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
    }

    private fun setupEventListeners() {
        binding.btnRegistro.setOnClickListener {
            val nombre = binding.txtNombreCliente.text.toString()
            val apellido = binding.txtApellidoCliente.text.toString()
            val email = binding.txtEmailCliente.text.toString()

            if (nombre.isNotEmpty() && apellido.isNotEmpty() && email.isNotEmpty()) {
                val mensaje = "Registro exitoso: $nombre $apellido"
                Toast.makeText(requireContext(), mensaje, Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
            }
        }

        binding.lblIniciarSesion.setOnClickListener {
            Toast.makeText(requireContext(), "Navegando a Iniciar Sesión...", Toast.LENGTH_SHORT).show()
            // Aquí iría la navegación al fragment de Login, por ejemplo:
            // findNavController().navigate(R.id.action_registroClienteFragment_to_loginFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}