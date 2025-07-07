package com.example.proyectofinaldemviles.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectofinaldemviles.R
import com.example.proyectofinaldemviles.models.Categoria

class CategoriaAdapter(
    private var categorias: List<Categoria>,
    private val onItemClick: (Categoria) -> Unit
) : RecyclerView.Adapter<CategoriaAdapter.CategoriaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.categoria_item, parent, false)
        return CategoriaViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoriaViewHolder, position: Int) {
        holder.bind(categorias[position], onItemClick)
    }

    override fun getItemCount(): Int = categorias.size

    fun updateList(newList: List<Categoria>) {
        categorias = newList
        notifyDataSetChanged()
    }

    class CategoriaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val txtNombreCategoria: TextView = itemView.findViewById(R.id.txtNombreCategoria)
        fun bind(categoria: Categoria, onItemClick: (Categoria) -> Unit) {
            txtNombreCategoria.text = categoria.name
            itemView.setOnClickListener { onItemClick(categoria) }
        }
    }
}
