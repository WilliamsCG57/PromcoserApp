package dev.williamscg.promcoserapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import dev.williamscg.promcoserapp.R
import dev.williamscg.promcoserapp.model.CreacionDetalleModel

class CreacionDetalleAdapter (private val lstCreacionDetalle: List<CreacionDetalleModel>) :
    RecyclerView.Adapter<CreacionDetalleAdapter.ViewHolder>() {

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
    {
        val tvHoraInicio = itemView.findViewById<TextView>(R.id.tvHoraInicio)
        val tvHoraFin = itemView.findViewById<TextView>(R.id.tvHoraFin)
        val tvTrabajo = itemView.findViewById<TextView>(R.id.tvTrabajo)
        val tvDescripcion = itemView.findViewById<TextView>(R.id.tvDescripcion)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.item_creacion_detalle, parent, false))
    }

    override fun getItemCount(): Int {
        return lstCreacionDetalle.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemCreacionDetalle = lstCreacionDetalle[position]
        holder.tvHoraInicio.text = itemCreacionDetalle.HoraInicio
        holder.tvHoraFin.text = itemCreacionDetalle.HoraFin
        holder.tvTrabajo.text = itemCreacionDetalle.Trabajo
        holder.tvDescripcion.text = itemCreacionDetalle.Descripcion


    }
}