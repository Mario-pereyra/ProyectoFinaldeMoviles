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
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.proyectofinaldemviles.databinding.FragmentCategoriasBinding
import com.example.proyectofinaldemviles.ui.adapters.CategoriaAdapter
import com.example.proyectofinaldemviles.viewmodels.CategoriaViewModel

class CategoriasFragment : Fragment() {
    private var _binding: FragmentCategoriasBinding? = null
    private val binding get() = _binding!!
    private val categoriaViewModel: CategoriaViewModel by viewModels()
    private lateinit var categoriaAdapter: CategoriaAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCategoriasBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupObservers()
        setupSearch()
        categoriaViewModel.cargarCategorias()
    }

    private fun setupRecyclerView() {
        categoriaAdapter = CategoriaAdapter(emptyList()) { categoria ->
            val action = CategoriasFragmentDirections.actionCategoriasFragmentToTrabajadorListFragment(categoria.id)
            requireActivity().runOnUiThread {
                binding.root.findNavController().navigate(action)
            }
        }
        binding.recyclerCategorias.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = categoriaAdapter
        }
    }

    private fun setupObservers() {
        categoriaViewModel.filteredCategorias.observe(viewLifecycleOwner) { categorias ->
            categoriaAdapter.updateList(categorias)
        }
        categoriaViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBarCategorias.visibility = if (isLoading == true) View.VISIBLE else View.GONE
        }
        categoriaViewModel.error.observe(viewLifecycleOwner) { errorMsg ->
            if (!errorMsg.isNullOrBlank()) {
                Toast.makeText(requireContext(), errorMsg, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun setupSearch() {
        binding.txtBuscarCategoria.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                categoriaViewModel.filtrarCategorias(s?.toString() ?: "")
            }
            override fun afterTextChanged(s: Editable?) {}
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
