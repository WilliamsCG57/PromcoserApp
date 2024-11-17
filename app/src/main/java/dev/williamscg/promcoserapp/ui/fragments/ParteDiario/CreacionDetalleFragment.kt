package dev.williamscg.promcoserapp.ui.fragments.ParteDiario

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import dev.williamscg.promcoserapp.R
import dev.williamscg.promcoserapp.adapter.CreacionDetalleAdapter
import dev.williamscg.promcoserapp.adapter.MaquinariaAdapter
import dev.williamscg.promcoserapp.model.CreacionDetalleModel
import dev.williamscg.promcoserapp.model.MaquinariaModel

class CreacionDetalleFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_creacion_detalle, container, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.rvCreacionDetalle)
        recyclerView.layoutManager = LinearLayoutManager(context)
        val lstCreacionDetalle = listOf(
            CreacionDetalleModel("10:30", "10:45", "Operario", "Manual"),
            CreacionDetalleModel("10:30", "10:45", "tyu", "iop"),
            CreacionDetalleModel("10:30", "10:45", "qwe", "asd"),
        )

        recyclerView.adapter = CreacionDetalleAdapter(lstCreacionDetalle)

        return view
    }

}