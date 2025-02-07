package com.example.deber01_2b_aaza

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class UniversidadAdapter(
    private val universidades: List<Universidad>,
    private val onItemClick: (Universidad) -> Unit
) : RecyclerView.Adapter<UniversidadAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_universidad, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val universidad = universidades[position]
        holder.bind(universidad)
    }

    override fun getItemCount(): Int = universidades.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nombre: TextView = itemView.findViewById(R.id.txtNombre)

        fun bind(universidad: Universidad) {
            nombre.text = universidad.nombre
            itemView.setOnClickListener { onItemClick(universidad) }
        }
    }
}
