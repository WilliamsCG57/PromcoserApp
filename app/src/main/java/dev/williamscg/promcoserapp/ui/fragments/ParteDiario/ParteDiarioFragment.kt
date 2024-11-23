package dev.williamscg.promcoserapp.ui.fragments.ParteDiario

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import dev.williamscg.promcoserapp.R

class ParteDiarioFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_parte_diario, container, false)
        // Inflate the layout for this fragment
        val boton = view.findViewById<Button>(R.id.btnCrearDetalle)
        val boton2 = view.findViewById<Button>(R.id.btnMasDetalle)

        boton2.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("idParte", "1")
            findNavController().navigate(R.id.creacionDetalleFragment, bundle)
        }


        boton.setOnClickListener {
            findNavController().navigate(R.id.crearParteDiarioFragment)
        }

        return view
    }
}

