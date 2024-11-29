package dev.williamscg.promcoserapp.ui.fragments

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
import dev.williamscg.promcoserapp.model.MaquinariaModel

class MaquinariaFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_maquinaria, container, false)
        val db = FirebaseFirestore.getInstance()
        val rvMaquinaria = view.findViewById<RecyclerView>(R.id.rvMaquinaria)
        var lstMaquinaria: List<MaquinariaModel>


        return view
    }
}
