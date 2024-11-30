package dev.williamscg.promcoserapp.ui.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import dev.williamscg.promcoserapp.R
import dev.williamscg.promcoserapp.adapter.TarjetaParteDiarioAdapter
import dev.williamscg.promcoserapp.adapter.TarjetaParteDiarioCompletoAdapter
import dev.williamscg.promcoserapp.apiService.RetrofitInstance
import dev.williamscg.promcoserapp.model.MaquinariaModel
import dev.williamscg.promcoserapp.model.TarjetaParteDiarioModel
import kotlinx.coroutines.launch
import retrofit2.HttpException

class HistorialParteFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: TarjetaParteDiarioCompletoAdapter
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_historial, container, false)
        recyclerView = view.findViewById(R.id.rvHistorial)

        sharedPreferences = requireContext().getSharedPreferences("MiAppPreferences", Context.MODE_PRIVATE)
        val userId = obtenerUserIdDesdePreferencias()

        lifecycleScope.launch {
            if (userId == -1) {
                Toast.makeText(requireContext(), "Usuario no autenticado", Toast.LENGTH_SHORT).show()
                return@launch
            }
            try {
                    val response = RetrofitInstance.api.getParteDiarioConfirmed(userId)
                val listParteDiario = response.takeLast(3).asReversed() // Mostrar solo los últimos 3
                adapter = TarjetaParteDiarioCompletoAdapter(
                    listParteDiario,
                    onDetailsClick = { parteDiario ->
                        saveSelectedParteDiarioId(parteDiario.idParteDiario)
                        navigateToDetails(parteDiario)
                    }
                )
                recyclerView.layoutManager = LinearLayoutManager(context)
                recyclerView.adapter = adapter
            } catch (e: HttpException) {
                e.printStackTrace()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        return view
    }
    private fun obtenerUserIdDesdePreferencias(): Int {
        val sharedPreferences = requireContext().getSharedPreferences("userPrefs", Context.MODE_PRIVATE)
        return sharedPreferences.getInt("userId", -1) // Devuelve -1 si no encuentra el valor
    }

    private fun navigateToDetails(parteDiario: TarjetaParteDiarioModel) {
        findNavController().navigate(R.id.verDetalleParteFragment)
    }

    private fun saveSelectedParteDiarioId(idParteDiario: Int) {
        sharedPreferences.edit().putInt("SELECTED_PARTE_DIARIO_ID", idParteDiario).apply()
    }
}
