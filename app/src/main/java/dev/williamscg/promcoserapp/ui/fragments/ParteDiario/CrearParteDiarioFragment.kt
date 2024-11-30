package dev.williamscg.promcoserapp.ui.fragments.ParteDiario

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import dev.williamscg.promcoserapp.R
import dev.williamscg.promcoserapp.apiService.ApiClient
import dev.williamscg.promcoserapp.model.ClienteModel
import dev.williamscg.promcoserapp.model.LugarTrabajoModel
import dev.williamscg.promcoserapp.model.MaquinariaModel
import dev.williamscg.promcoserapp.model.RequestNewParteDiario
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import androidx.navigation.fragment.findNavController

class CrearParteDiarioFragment : Fragment() {

    private lateinit var spinnerClientes: Spinner
    private lateinit var spinnerLugares: Spinner
    private lateinit var spinnerMaquinarias: Spinner
    private lateinit var plainTextHorometro: EditText
    private lateinit var btnConfirmar: Button
    private var maquinariasList: List<MaquinariaModel> = emptyList()
    private var clientesList: List<ClienteModel> = emptyList()
    private var lugaresList: List<LugarTrabajoModel> = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflar la vista
        val view = inflater.inflate(R.layout.fragment_crear_parte_diario, container, false)

        // Declarar y configurar los Spinners
        spinnerClientes = view.findViewById(R.id.spCliente)
        spinnerLugares = view.findViewById(R.id.spLugarTrabajo)
        spinnerMaquinarias = view.findViewById(R.id.spMaquinaria)
        plainTextHorometro = view.findViewById(R.id.tvHorometroInicio)
        btnConfirmar = view.findViewById(R.id.btnConfDetalle)

        cargarMaquinarias()
        cargarLugares()
        cargarClientes()

        val sharedPreferences = requireActivity().getSharedPreferences("userPrefs", Context.MODE_PRIVATE)
        val userId = sharedPreferences.getInt("userId", -1)

        btnConfirmar.setOnClickListener {
            if (validateHorometro()) {
                val maquinariaSelec = maquinariasList.getOrNull(spinnerMaquinarias.selectedItemPosition)
                val clienteSelec = clientesList.getOrNull(spinnerClientes.selectedItemPosition)
                val lugarSelec = lugaresList.getOrNull(spinnerLugares.selectedItemPosition)

                val idMaquinaria = maquinariaSelec?.idMaquinaria ?: return@setOnClickListener
                val idCliente = clienteSelec?.idCliente ?: return@setOnClickListener
                val idLugarTrabajo = lugarSelec?.idLugarTrabajo ?: return@setOnClickListener

                val newParteDiario = RequestNewParteDiario(
                    idCliente = idCliente,
                    idPersonal = userId,
                    idLugarTrabajo = idLugarTrabajo,
                    idMaquinaria = idMaquinaria,
                    horometroInicio = plainTextHorometro.text.toString().toDouble(),
                    fecha = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                )
                crearParteDiario(newParteDiario)
            }

        }
        return view
    }

    private fun crearParteDiario(newParteDiario: RequestNewParteDiario) {

        val apiService = ApiClient.getParteDiarioApiService(requireContext())
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = apiService.crearParteDiario(newParteDiario)
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


    private fun validateHorometro(): Boolean {
        val input = plainTextHorometro.text.toString()
        if (input.isBlank()) {
            showToast("Ingrese un valor para el horómetro")
            return false
        }
        return try {
            val value = input.toDouble()
            if (value >= 0) true else {
                showToast("El horómetro debe ser mayor a 0")
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

    private fun cargarMaquinarias() {
        val apiService = ApiClient.getMaquinariaApiService(requireContext())
        apiService.getAllMaquinarias().enqueue(object : Callback<List<MaquinariaModel>> {
            override fun onResponse(
                call: Call<List<MaquinariaModel>>,
                response: Response<List<MaquinariaModel>>
            ) {
                if (response.isSuccessful) {
                    maquinariasList = response.body() ?: emptyList()
                    val adapter = ArrayAdapter(
                        requireContext(),
                        android.R.layout.simple_spinner_item,
                        maquinariasList.map { it.placa }
                    )
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    spinnerMaquinarias.adapter = adapter
                } else {
                    Toast.makeText(requireContext(), "Error al cargar maquinarias", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<MaquinariaModel>>, t: Throwable) {
                Toast.makeText(requireContext(), "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun cargarClientes() {
        val apiService = ApiClient.getClienteApiService(requireContext())
        apiService.getAllClientes().enqueue(object : Callback<List<ClienteModel>> {
            override fun onResponse(
                call: Call<List<ClienteModel>>,
                response: Response<List<ClienteModel>>
            ) {
                if (response.isSuccessful) {
                    clientesList = response.body() ?: emptyList()
                    val adapter = ArrayAdapter(
                        requireContext(),
                        android.R.layout.simple_spinner_item,
                        clientesList.map { it.razonSocial }
                    )
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    spinnerClientes.adapter = adapter
                } else {
                    Toast.makeText(requireContext(), "Error al cargar maquinarias", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<ClienteModel>>, t: Throwable) {
                Toast.makeText(requireContext(), "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun cargarLugares() {
        val apiService = ApiClient.getLugarApiService(requireContext())
        apiService.getAllLugares().enqueue(object : Callback<List<LugarTrabajoModel>> {
            override fun onResponse(
                call: Call<List<LugarTrabajoModel>>,
                response: Response<List<LugarTrabajoModel>>
            ) {
                if (response.isSuccessful) {
                    lugaresList = response.body() ?: emptyList()
                    val adapter = ArrayAdapter(
                        requireContext(),
                        android.R.layout.simple_spinner_item,
                        lugaresList.map { it.descripcion }
                    )
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    spinnerLugares.adapter = adapter
                } else {
                    Toast.makeText(requireContext(), "Error al cargar maquinarias", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<LugarTrabajoModel>>, t: Throwable) {
                Toast.makeText(requireContext(), "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}