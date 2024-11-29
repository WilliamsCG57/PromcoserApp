package dev.williamscg.promcoserapp.ui.fragments.ParteDiario

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dev.williamscg.promcoserapp.R


class ConfirmarParteFragment : Fragment() {

    private var idParte: Int = -1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_confirmar_parte, container, false)
        val sharedPreferences = requireContext().getSharedPreferences("MiAppPreferences", Context.MODE_PRIVATE)
        idParte = sharedPreferences.getInt("SELECTED_PARTE_DIARIO_ID", -1)

        return view
    }

}