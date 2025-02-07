package com.example.examen_2b_aaza

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class UniversidadAdapter(
    private var universidades: List<Universidad>,
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

    // Función para actualizar la lista de datos
    fun actualizarDatos(nuevasUniversidades: List<Universidad>) {
        universidades = nuevasUniversidades
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nombre: TextView = itemView.findViewById(R.id.txtNombre)
        private val presupuesto: TextView = itemView.findViewById(R.id.txtPresupuesto)
        private val privada: TextView = itemView.findViewById(R.id.txtEsPrivada)

        fun bind(universidad: Universidad) {
            nombre.text = universidad.nombre
            presupuesto.text = "        Presupuesto Universidad: $" + universidad.presupuestoAnual.toString()
            privada.text = "        Es privada: " + universidad.esPrivada.toString()
            itemView.setOnClickListener { onItemClick(universidad) }
        }
    }
}
