package dev.williamscg.promcoserapp.ui.fragments.ParteDiario

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import dev.williamscg.promcoserapp.R
import dev.williamscg.promcoserapp.apiService.ApiClient
import dev.williamscg.promcoserapp.model.DetalleParteDiarioModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import androidx.navigation.fragment.findNavController


class AgregarDetalleFragment : Fragment() {

    private var idParte: Int = -1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val sharedPreferences = requireContext().getSharedPreferences("MiAppPreferences", Context.MODE_PRIVATE)
        idParte = sharedPreferences.getInt("SELECTED_PARTE_DIARIO_ID", -1)
        return inflater.inflate(R.layout.fragment_agregar_detalle, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val etHoraInicio = view.findViewById<EditText>(R.id.etHoraInicioDet)
        val etHoraFin = view.findViewById<EditText>(R.id.etHoraFinDet)
        val etDescripcionTrabajo = view.findViewById<EditText>(R.id.etDescrTrabDet)
        val btnGuardarDetalle = view.findViewById<Button>(R.id.btnAgregarDetalle)

        // Acción del botón guardar
        btnGuardarDetalle.setOnClickListener {
            val horaInicio = etHoraInicio.text.toString()
            val horaFin = etHoraFin.text.toString()
            val descripcionTrabajo = etDescripcionTrabajo.text.toString()

            // Validar campos
            if (horaInicio.isEmpty() || horaFin.isEmpty() || descripcionTrabajo.isEmpty()) {
                Toast.makeText(context, "Por favor completa todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Crear el modelo
            val detalleParteDiario = DetalleParteDiarioModel(
                idParteDiario = idParte,
                horaInicio = horaInicio,
                horaFin = horaFin,
                trabajoEfectuado = descripcionTrabajo,
                estado = true
            )

            // Realizar el POST
            realizarPostDetalleParteDiario(detalleParteDiario)
        }
    }

    private fun realizarPostDetalleParteDiario(detalle: DetalleParteDiarioModel) {
        // Crear una instancia del servicio
        val apiService = ApiClient.getDetalleParteApiService(requireContext())

        // Llamada a la API utilizando Coroutine
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = apiService.crearDetalleParteDiario(detalle)
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        findNavController().popBackStack()
                    } else {
                        Toast.makeText(context, "Error al guardar el detalle: ${response.code()}", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "Error de conexión: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}