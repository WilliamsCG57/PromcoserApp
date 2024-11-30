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
import androidx.navigation.fragment.findNavController
import dev.williamscg.promcoserapp.R
import dev.williamscg.promcoserapp.apiService.ApiClient
import dev.williamscg.promcoserapp.model.ConfirmarNewParteDiario
import dev.williamscg.promcoserapp.model.RequestNewParteDiario
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.format.DateTimeFormatter


class ConfirmarParteFragment : Fragment() {

    private var idParte: Int = -1
    private lateinit var etHoraFin: EditText
    private lateinit var etCantAceite: EditText
    private lateinit var etCantPetroleo: EditText
    private lateinit var btnConfirmarPD: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_confirmar_parte, container, false)
        val sharedPreferences = requireContext().getSharedPreferences("MiAppPreferences", Context.MODE_PRIVATE)
        idParte = sharedPreferences.getInt("SELECTED_PARTE_DIARIO_ID", -1)

        etHoraFin = view.findViewById(R.id.etHoraFin)
        etCantPetroleo = view.findViewById(R.id.etCantPetroleo)
        etCantAceite = view.findViewById(R.id.etCantAceite)
        btnConfirmarPD = view.findViewById(R.id.btnConfirmarPD)

        btnConfirmarPD.setOnClickListener {
            if (validate()) {
                val confirmarNewParteDiario = ConfirmarNewParteDiario(
                    idParteDiario = idParte,
                    horometroFinal = etHoraFin.text.toString().toDouble(),
                    cantidadPetroleo = etCantPetroleo.text.toString().toDouble(),
                    cantidadAceite = etCantAceite.text.toString().toDouble(),
                )
                confirmarParteDiario(confirmarNewParteDiario)
            }
        }

        return view
    }

    private fun confirmarParteDiario(confirmarNewParteDiario: ConfirmarNewParteDiario) {

        val apiService = ApiClient.getParteDiarioApiService(requireContext())
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = apiService.confirmarParteDiario(confirmarNewParteDiario)
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        findNavController().navigateUp()
                    } else {
                        Toast.makeText(requireContext(), "Error: ${response.code()}", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(requireContext(), "Error: ${e.localizedMessage}", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

    private fun validate(): Boolean {
        val input1 = etHoraFin.text.toString()
        val input2 = etCantAceite.text.toString()
        val input3 = etCantPetroleo.text.toString()
        if (input1.isBlank() || input2.isBlank() || input3.isBlank()) {
            showToast("Ingrese todos los datos requeridos.")
            return false
        }
        return try {
            val value1 = input1.toDouble()
            val value2 = input2.toDouble()
            val value3 = input3.toDouble()
            if (value1 >= 0 || value2 >= 0 || value3 >= 0) true else {
                showToast("Los valores deben ser mayor a 0")
                false
            }
        } catch (e: NumberFormatException) {
            showToast("Ingrese un número válido")
            false
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

}