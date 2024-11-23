package dev.williamscg.promcoserapp.ui.fragments.ParteDiario

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dev.williamscg.promcoserapp.R
import dev.williamscg.promcoserapp.adapter.CreacionDetalleAdapter
import dev.williamscg.promcoserapp.apiService.ApiClient
import dev.williamscg.promcoserapp.model.CreacionDetalleModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CreacionDetalleFragment : Fragment() {

    private lateinit var idParte: String
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CreacionDetalleAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_creacion_detalle, container, false)
        recyclerView = view.findViewById<RecyclerView>(R.id.rvCreacionDetalle)
        recyclerView.layoutManager = LinearLayoutManager(context)

        idParte = requireArguments().getString("idParte")
            ?: throw IllegalArgumentException("idParte es obligatorio")

        adapter = CreacionDetalleAdapter(emptyList()) { detalle ->
            deleteDetail(detalle.idDetalleParteDiario)
        }

        recyclerView.adapter = adapter
        return view
    }

    override fun onResume() {
        super.onResume()
        fetchDetailsFromApi()
    }

    fun deleteDetail(identificador: Int) {
        Toast.makeText(context, "Eliminar: ${identificador}", Toast.LENGTH_SHORT).show()
    }

    private fun fetchDetailsFromApi() {
        val apiService = ApiClient.instance
        apiService.getAllActiveDetails(idParte.toInt()).enqueue(object : Callback<List<CreacionDetalleModel>> {
            override fun onResponse(
                call: Call<List<CreacionDetalleModel>>,
                response: Response<List<CreacionDetalleModel>>
            ) {
                if (response.isSuccessful) {
                    val lstCreacionDetalle = response.body() ?: emptyList()
                    adapter = CreacionDetalleAdapter(lstCreacionDetalle) { detalle ->
                        deleteDetail(detalle.idDetalleParteDiario)
                    }
                    recyclerView.adapter = adapter
                } else {
                    Log.e("API_ERROR", "Error al obtener detalles: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<List<CreacionDetalleModel>>, t: Throwable) {
                Log.e("API_FAILURE", "Fallo en la llamada: ${t.message}")
            }
        })
    }

}