package com.example.deber01_2b_aaza

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CarreraAdapter(
    private var carreras: List<Carrera>,  // Cambiar a mutable para poder actualizarla
    private val onItemClick: (Carrera) -> Unit
) : RecyclerView.Adapter<CarreraAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_carrera, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val carrera = carreras[position]
        holder.bind(carrera)
    }

    override fun getItemCount(): Int = carreras.size

    // Método para actualizar la lista de carreras
    fun actualizarCarreras(nuevasCarreras: List<Carrera>) {
        carreras = nuevasCarreras  // Actualizar la lista
        notifyDataSetChanged()  // Notificar que los datos han cambiado
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nombre: TextView = itemView.findViewById(R.id.txtNombre)

        fun bind(carrera: Carrera) {
            nombre.text = carrera.nombre
            itemView.setOnClickListener { onItemClick(carrera) }
        }
    }
}
