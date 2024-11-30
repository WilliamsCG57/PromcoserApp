package dev.williamscg.promcoserapp.ui.fragments.ParteDiario

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dev.williamscg.promcoserapp.R
import dev.williamscg.promcoserapp.adapter.CreacionDetalleAdapter
import dev.williamscg.promcoserapp.adapter.VerDetalleAdapter
import dev.williamscg.promcoserapp.apiService.ApiClient
import dev.williamscg.promcoserapp.model.CreacionDetalleModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class VerDetalleParteFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: VerDetalleAdapter
    private var idParte: Int = -1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_ver_detalle_parte, container, false)
        recyclerView = view.findViewById<RecyclerView>(R.id.rvVerDetalle)
        recyclerView.layoutManager = LinearLayoutManager(context)

        val sharedPreferences = requireContext().getSharedPreferences("MiAppPreferences", Context.MODE_PRIVATE)
        idParte = sharedPreferences.getInt("SELECTED_PARTE_DIARIO_ID", -1)

        adapter = VerDetalleAdapter(
            lstCreacionDetalle = emptyList(),
            onDetailClick = { detalle ->
                showPopPp(detalle)
            }
        )
        recyclerView.adapter = adapter


        return view
    }
    override fun onResume() {
        super.onResume()
        fetchDetailsFromApi()
    }

    private fun fetchDetailsFromApi() {
        val apiService = ApiClient.getDetalleParteApiService(requireContext())
        apiService.getAllActiveDetails(idParte).enqueue(object :
            Callback<List<CreacionDetalleModel>> {
            override fun onResponse(
                call: Call<List<CreacionDetalleModel>>,
                response: Response<List<CreacionDetalleModel>>
            ) {
                if (response.isSuccessful) {
                    val lstCreacionDetalle = response.body() ?: emptyList()
                    adapter = VerDetalleAdapter(
                        lstCreacionDetalle = lstCreacionDetalle,
                        onDetailClick = { detalle ->
                            showPopPp(detalle)
                        }
                    )
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

    private fun showPopPp(detalle: CreacionDetalleModel) {
        val builder = AlertDialog.Builder(requireContext())
        val dialogView = layoutInflater.inflate(R.layout.popup_informativo_trabajo, null)

        val ivImage = dialogView.findViewById<ImageView>(R.id.ivImage)
        val tvTitle = dialogView.findViewById<TextView>(R.id.tvTitle)
        val tvDescription = dialogView.findViewById<TextView>(R.id.tvDescription)

        // Establecer los valores del popup
        tvTitle.text = "Trabajo Efectuado"
        tvDescription.text = detalle.trabajoEfectuado

        // Cargar una imagen fija desde los recursos drawable
        ivImage.setImageResource(R.drawable.promcos)

        // Configurar el diálogo
        builder.setView(dialogView)
        builder.setPositiveButton("Cerrar") { dialog, _ -> dialog.dismiss() }
        val dialog = builder.create()
        dialog.show()
    }
}