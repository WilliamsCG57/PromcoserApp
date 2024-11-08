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
import dev.williamscg.promcoserapp.adapter.MaquinariaAdapter
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

        db.collection("maquinarias")
            .addSnapshotListener { value, error ->
                if (error != null) {
                    Log.e("Firestore Error", error.message.toString())
                    return@addSnapshotListener
                }
                lstMaquinaria = value!!.documents.map { document ->
                    MaquinariaModel(
                        document["placa"].toString(),
                        document["modelo"].toString(),
                        document["tipo"].toString(),
                        document["imageUrl"].toString()
                    )
                }
                rvMaquinaria.adapter = MaquinariaAdapter(lstMaquinaria)
                rvMaquinaria.layoutManager = LinearLayoutManager(requireContext())
            }
        return view
    }
}
