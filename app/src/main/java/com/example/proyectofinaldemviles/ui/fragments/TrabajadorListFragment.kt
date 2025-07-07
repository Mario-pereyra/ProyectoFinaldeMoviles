package com.example.proyectofinaldemviles.ui.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.proyectofinaldemviles.databinding.FragmentTrabajadorListBinding
import com.example.proyectofinaldemviles.ui.adapters.TrabajadorAdapter
import com.example.proyectofinaldemviles.viewmodels.TrabajadorViewModel

class TrabajadorListFragment : Fragment() {
    private var _binding: FragmentTrabajadorListBinding? = null
    private val binding get() = _binding!!
    private val trabajadorViewModel: TrabajadorViewModel by viewModels()
    private lateinit var trabajadorAdapter: TrabajadorAdapter
    private var trabajadoresOriginal: List<com.example.proyectofinaldemviles.models.Trabajador> = emptyList()

    private val args: TrabajadorListFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTrabajadorListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupObservers()
        setupSearch()
        trabajadorViewModel.cargarTrabajadoresPorCategoria(args.categoriaId)
    }

    private fun setupRecyclerView() {
        trabajadorAdapter = TrabajadorAdapter(emptyList())
        binding.recyclerTrabajadores.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = trabajadorAdapter
        }
    }

    private fun setupObservers() {
        trabajadorViewModel.trabajadores.observe(viewLifecycleOwner) { trabajadores ->
            trabajadoresOriginal = trabajadores
            trabajadorAdapter.updateList(trabajadores)
        }
        trabajadorViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBarTrabajadores.visibility = if (isLoading == true) View.VISIBLE else View.GONE
        }
    }

    private fun setupSearch() {
        binding.edtBuscarTrabajador.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                filtrarTrabajadores(s?.toString() ?: "")
            }
            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun filtrarTrabajadores(query: String) {
        val filtrados = if (query.isBlank()) {
            trabajadoresOriginal
        } else {
            trabajadoresOriginal.filter {
                val usuario = it.user
                val nombreCompleto = if (usuario != null) "${usuario.name} ${usuario.last_name}" else ""
                nombreCompleto.lowercase().contains(query.lowercase())
            }
        }
        trabajadorAdapter.updateList(filtrados)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
