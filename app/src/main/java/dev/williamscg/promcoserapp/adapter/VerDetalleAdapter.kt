package dev.williamscg.promcoserapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import dev.williamscg.promcoserapp.R
import dev.williamscg.promcoserapp.model.CreacionDetalleModel

class VerDetalleAdapter (
    private val lstCreacionDetalle: List<CreacionDetalleModel>,
    private val onDetailClick: (CreacionDetalleModel) -> Unit
) :
    RecyclerView.Adapter<VerDetalleAdapter.ViewHolder>() {

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
    {
        val tvHoraInicio = itemView.findViewById<TextView>(R.id.tvHoraInicio)
        val tvHoraFin = itemView.findViewById<TextView>(R.id.tvHoraFin)
        val icTrabajo = itemView.findViewById<ImageView>(R.id.icTrabajo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.item_ver_detalle, parent, false))
    }

    override fun getItemCount(): Int {
        return lstCreacionDetalle.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemCreacionDetalle = lstCreacionDetalle[position]
        holder.tvHoraInicio.text = itemCreacionDetalle.horaInicio
        holder.tvHoraFin.text = itemCreacionDetalle.horaFin
        holder.icTrabajo.setOnClickListener {
            onDetailClick(itemCreacionDetalle)
        }
    }
}