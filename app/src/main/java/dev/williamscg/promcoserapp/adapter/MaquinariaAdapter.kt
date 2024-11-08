package dev.williamscg.promcoserapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import dev.williamscg.promcoserapp.R
import dev.williamscg.promcoserapp.model.MaquinariaModel

class MaquinariaAdapter (private val lstMaquinaria: List<MaquinariaModel>) :
    RecyclerView.Adapter<MaquinariaAdapter.ViewHolder>() {

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
    {
        val ivMaquinaria = itemView.findViewById<ImageView>(R.id.ivMaquinaria)
        val tvPlaca = itemView.findViewById<TextView>(R.id.tvPlaca)
        val tvModelo = itemView.findViewById<TextView>(R.id.tvModelo)
        val tvTipoMaquinaria = itemView.findViewById<TextView>(R.id.tvTipoMaquinaria)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.item_maquinaria, parent, false))
    }

    override fun getItemCount(): Int {
        return lstMaquinaria.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemMaquinaria = lstMaquinaria[position]
        holder.tvPlaca.text = itemMaquinaria.placa
        holder.tvModelo.text = itemMaquinaria.modelo
        holder.tvTipoMaquinaria.text = itemMaquinaria.tipo
        Picasso.get().load(itemMaquinaria.imageUrl).into(holder.ivMaquinaria)

    }

}
