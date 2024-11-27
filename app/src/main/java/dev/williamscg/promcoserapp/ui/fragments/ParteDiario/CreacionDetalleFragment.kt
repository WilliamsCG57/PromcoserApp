package dev.williamscg.promcoserapp.ui.fragments.ParteDiario

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
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
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity


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

        adapter = CreacionDetalleAdapter(
            lstCreacionDetalle = emptyList(),
            onDeleteClick = { detalle ->
                showPopPp(detalle)
            },
            onDetailClick = { detalle ->
                // Acción al hacer clic en detalle
                deactivateDetail(detalle.idDetalleParteDiario)
            }
        )

        recyclerView.adapter = adapter
        return view
    }

    override fun onResume() {
        super.onResume()
        fetchDetailsFromApi()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Establecer el título fijo de la Toolbar o ActionBar
        (activity as? AppCompatActivity)?.supportActionBar?.title = "Detalle Parte Diario"
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



    private fun deactivateDetail(identificador: Int) {
        Toast.makeText(context, "Eliminando: $identificador", Toast.LENGTH_SHORT).show()

        val apiService = ApiClient.getDetalleParteApiService(requireContext())
        apiService.deactivateDetail(identificador).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Toast.makeText(context, "Eliminado correctamente", Toast.LENGTH_SHORT).show()
                    fetchDetailsFromApi()
                } else {
                    Toast.makeText(context, "Error al eliminar: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(context, "Fallo en la conexión: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }


    private fun fetchDetailsFromApi() {
        val apiService = ApiClient.getDetalleParteApiService(requireContext())
        apiService.getAllActiveDetails(idParte.toInt()).enqueue(object : Callback<List<CreacionDetalleModel>> {
            override fun onResponse(
                call: Call<List<CreacionDetalleModel>>,
                response: Response<List<CreacionDetalleModel>>
            ) {
                if (response.isSuccessful) {
                    val lstCreacionDetalle = response.body() ?: emptyList()
                    adapter = CreacionDetalleAdapter(
                        lstCreacionDetalle = lstCreacionDetalle,
                        onDetailClick = { detalle ->
                            showPopPp(detalle)
                        },
                        onDeleteClick = { detalle ->
                            deactivateDetail(detalle.idDetalleParteDiario)
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

}