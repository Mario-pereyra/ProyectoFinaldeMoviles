package com.example.proyectofinaldemviles.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.proyectofinaldemviles.R
import com.example.proyectofinaldemviles.models.Trabajador

class TrabajadorAdapter(
    private var trabajadores: List<Trabajador>
) : RecyclerView.Adapter<TrabajadorAdapter.TrabajadorViewHolder>() {

    fun updateList(newList: List<Trabajador>) {
        trabajadores = newList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrabajadorViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.trabajador_item, parent, false)
        return TrabajadorViewHolder(view)
    }

    override fun onBindViewHolder(holder: TrabajadorViewHolder, position: Int) {
        holder.bind(trabajadores[position])
    }

    override fun getItemCount(): Int = trabajadores.size

    inner class TrabajadorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imgFoto: ImageView = itemView.findViewById(R.id.imgTrabajadorFoto)
        private val txtNombre: TextView = itemView.findViewById(R.id.txtTrabajadorNombre)
        private val txtRating: TextView = itemView.findViewById(R.id.txtTrabajadorRating)
        private val txtTrabajos: TextView = itemView.findViewById(R.id.txtTrabajadorTrabajos)

        fun bind(trabajador: Trabajador) {
            // Evitar crash si user es null
            val usuario = trabajador.user
            val nombreCompleto = if (usuario != null) "${usuario.name} ${usuario.last_name}" else "Sin nombre"
            txtNombre.text = nombreCompleto
            txtRating.text = trabajador.average_rating.toString()
            txtTrabajos.text = "${trabajador.reviews_count} trabajos"

            val fotoUrl = trabajador.picture_url
            if (fotoUrl != null && fotoUrl != "null") {
                Glide.with(itemView)
                    .load(fotoUrl)
                    .placeholder(R.drawable.ic_placeholder_user)
                    .circleCrop()
                    .into(imgFoto)
            } else {
                Glide.with(itemView)
                    .load(R.drawable.ic_placeholder_user)
                    .circleCrop()
                    .into(imgFoto)
            }
        }
    }
}
