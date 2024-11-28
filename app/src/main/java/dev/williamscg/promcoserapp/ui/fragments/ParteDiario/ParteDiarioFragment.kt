package dev.williamscg.promcoserapp.ui.fragments.ParteDiario

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dev.williamscg.promcoserapp.R
import dev.williamscg.promcoserapp.adapter.TarjetaParteDiarioAdapter
import dev.williamscg.promcoserapp.apiService.RetrofitInstance
import dev.williamscg.promcoserapp.model.TarjetaParteDiarioModel
import kotlinx.coroutines.launch
import retrofit2.HttpException

class ParteDiarioFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: TarjetaParteDiarioAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_parte_diario, container, false)
        recyclerView = view.findViewById(R.id.rvParteDiarioTarjeta)
        val boton = view.findViewById<Button>(R.id.btnCrearDetalle)
        // Obtener el userId desde SharedPreferences
        val userId = obtenerUserIdDesdePreferencias()

        boton.setOnClickListener {
            createParteDiario()
        }
        // Llamar a la API con el userId
        lifecycleScope.launch {
            if (userId == -1) {
                Toast.makeText(requireContext(), "Usuario no autenticado", Toast.LENGTH_SHORT).show()
                return@launch
            }

            try {
                val response = RetrofitInstance.api.getParteDiario(userId)
                val listParteDiario = response
                adapter = TarjetaParteDiarioAdapter(
                    listParteDiario,
                    onFinishClick = { parteDiario ->
                        showFinishModal(parteDiario)
                    },
                    onDetailsClick = { parteDiario ->
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

    private fun showFinishModal(parteDiario: TarjetaParteDiarioModel) {
        // Implementar lógica de modal de finalización
    }

    private fun navigateToDetails(parteDiario: TarjetaParteDiarioModel) {
        val bundle = Bundle().apply {
            putInt("idParteDiario", parteDiario.idParteDiario)
        }
        //findNavController().navigate(R.id.creacionDetalleFragment, bundle)
    }
    private fun createParteDiario(){
        findNavController().navigate(R.id.crearParteDiarioFragment, )
    }
}
