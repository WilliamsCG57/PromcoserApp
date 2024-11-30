package dev.williamscg.promcoserapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import dev.williamscg.promcoserapp.R
import dev.williamscg.promcoserapp.model.TarjetaParteDiarioModel


class TarjetaParteDiarioCompletoAdapter(
    private val listParteDiario: List<TarjetaParteDiarioModel>,
    private val onDetailsClick: (TarjetaParteDiarioModel) -> Unit
) : RecyclerView.Adapter<TarjetaParteDiarioCompletoAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvPlate: TextView = itemView.findViewById(R.id.txtPlate2)
        private val tvDestiny: TextView = itemView.findViewById(R.id.txtDestiny2)
        private val tvReason: TextView = itemView.findViewById(R.id.txtReason2)
        private val tvDate: TextView = itemView.findViewById(R.id.txtDate2)
        private val tvStartTime: TextView = itemView.findViewById(R.id.txtStartTime2)
        private val tvEndTime: TextView = itemView.findViewById(R.id.txtEndTime2)
        private val tvPetCount: TextView = itemView.findViewById(R.id.txtPetCount2)
        private val tvOilAmount: TextView = itemView.findViewById(R.id.txtOilAmount2)
        private val btnDetalles = itemView.findViewById<Button>(R.id.btnDetalles2)

        fun bind(
            item: TarjetaParteDiarioModel,
            onDetailsClick: (TarjetaParteDiarioModel) -> Unit
        ) {
            tvPlate.text = item.placa
            tvDestiny.text = item.descripcion
            tvReason.text = item.razonSocial
            tvDate.text = item.fecha
            tvStartTime.text = item.horometroInicio.toString()
            tvEndTime.text = item.horometroFinal.toString()
            tvPetCount.text = item.cantidadPetroleo.toString()
            tvOilAmount.text = item.cantidadAceite.toString()

            btnDetalles.setOnClickListener {
                onDetailsClick(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_tarjeta_parte_diario_completo, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = listParteDiario.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listParteDiario[position], onDetailsClick)
    }
}