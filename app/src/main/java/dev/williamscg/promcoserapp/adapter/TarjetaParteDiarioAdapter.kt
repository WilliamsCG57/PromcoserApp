package dev.williamscg.promcoserapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import dev.williamscg.promcoserapp.R
import dev.williamscg.promcoserapp.model.TarjetaParteDiarioModel

class TarjetaParteDiarioAdapter(
    private val listParteDiario: List<TarjetaParteDiarioModel>,
    private val onFinishClick: (TarjetaParteDiarioModel) -> Unit,
    private val onDetailsClick: (TarjetaParteDiarioModel) -> Unit
) : RecyclerView.Adapter<TarjetaParteDiarioAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvPlate: TextView = itemView.findViewById(R.id.txtPlate)
        private val tvDestiny: TextView = itemView.findViewById(R.id.txtDestiny)
        private val tvReason: TextView = itemView.findViewById(R.id.txtReason)
        private val tvSerie: TextView = itemView.findViewById(R.id.txtSerie)
        private val tvDate: TextView = itemView.findViewById(R.id.txtDate)
        private val tvStartTime: TextView = itemView.findViewById(R.id.txtStartTime)
        private val tvEndTime: TextView = itemView.findViewById(R.id.txtEndTime)
        private val tvPetCount: TextView = itemView.findViewById(R.id.txtPetCount)
        private val tvOilAmount: TextView = itemView.findViewById(R.id.txtOilAmount)

        fun bind(
            item: TarjetaParteDiarioModel,
            onFinishClick: (TarjetaParteDiarioModel) -> Unit,
            onDetailsClick: (TarjetaParteDiarioModel) -> Unit
        ) {
            tvPlate.text = item.placa
            tvDestiny.text = item.descripcion
            tvReason.text = item.razonSocial
            tvSerie.text = item.serie
            tvDate.text = item.fecha
            tvStartTime.text = item.horometroInicio.toString()
            tvEndTime.text = item.horometroFinal.toString()
            tvPetCount.text = item.cantidadPetroleo.toString()
            tvOilAmount.text = item.cantidadAceite.toString()

            itemView.findViewById<Button>(R.id.btnFin).setOnClickListener {
                onFinishClick(item)
            }

            itemView.findViewById<Button>(R.id.btnDetalles).setOnClickListener {
                onDetailsClick(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_tarjeta_parte_diario, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = listParteDiario.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listParteDiario[position], onFinishClick, onDetailsClick)
    }
}