package dev.williamscg.promcoserapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import dev.williamscg.promcoserapp.apiService.ClienteApiService
import dev.williamscg.promcoserapp.apiService.LugarTrabajoApiService
import dev.williamscg.promcoserapp.apiService.MaquinariaApiService
import dev.williamscg.promcoserapp.apiService.RetrofitInstance
import dev.williamscg.promcoserapp.model.ClienteModel
import dev.williamscg.promcoserapp.model.LugarTrabajoModel
import dev.williamscg.promcoserapp.model.MaquinariaModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CrearParteDiarioFragment : Fragment() {

    private lateinit var spinnerClientes: Spinner
    private lateinit var spinnerLugares: Spinner
    private lateinit var spinnerMaquinarias: Spinner
    private lateinit var plainTextHorometro: EditText
    private lateinit var btnConfirmar: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_crear_parte_diario, container, false)

        // Inicializar vistas
        spinnerClientes = view.findViewById(R.id.spCliente)
        spinnerLugares = view.findViewById(R.id.spLugarTrabajo)
        spinnerMaquinarias = view.findViewById(R.id.spMaquinaria)
        plainTextHorometro = view.findViewById(R.id.tvHorometroInicio)
        btnConfirmar = view.findViewById(R.id.btnConfDetalle)

        // Cargar datos en los spinners
        loadClientes()
        loadLugaresTrabajo()
        loadMaquinarias()

        // Configurar botón confirmar
        btnConfirmar.setOnClickListener {
            if (validateHorometro()) {
                val clienteSeleccionado = spinnerClientes.selectedItem.toString()
                val lugarSeleccionado = spinnerLugares.selectedItem.toString()
                val maquinariaSeleccionada = spinnerMaquinarias.selectedItem.toString()
                val horometro = plainTextHorometro.text.toString()

                Toast.makeText(
                    requireContext(),
                    "Cliente: $clienteSeleccionado\nLugar: $lugarSeleccionado\nMaquinaria: $maquinariaSeleccionada\nHorómetro: $horometro",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        return view
    }

    private fun loadClientes() {
        val clienteService = RetrofitInstance.createService(ClienteApiService::class.java)
        clienteService.getAllClientes().enqueue(object : Callback<List<ClienteModel>> {
            override fun onResponse(call: Call<List<ClienteModel>>, response: Response<List<ClienteModel>>) {
                if (response.isSuccessful && response.body() != null) {
                    val clientes = response.body()!!
                    val nombresClientes = clientes.map { "${it.nombre} ${it.apellidos}" }

                    val adapter = ArrayAdapter(
                        requireContext(),
                        android.R.layout.simple_spinner_item,
                        nombresClientes
                    )
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    spinnerClientes.adapter = adapter
                } else {
                    showToast("Error al cargar clientes")
                }
            }

            override fun onFailure(call: Call<List<ClienteModel>>, t: Throwable) {
                showToast("Error al conectar con el servidor")
            }
        })
    }

    private fun loadLugaresTrabajo() {
        val lugarService = RetrofitInstance.createService(LugarTrabajoApiService::class.java)
        lugarService.getAllLugares().enqueue(object : Callback<List<LugarTrabajoModel>> {
            override fun onResponse(call: Call<List<LugarTrabajoModel>>, response: Response<List<LugarTrabajoModel>>) {
                if (response.isSuccessful && response.body() != null) {
                    val lugares = response.body()!!
                    val descripciones = lugares.map { it.descripcion }

                    val adapter = ArrayAdapter(
                        requireContext(),
                        android.R.layout.simple_spinner_item,
                        descripciones
                    )
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    spinnerLugares.adapter = adapter
                } else {
                    showToast("Error al cargar lugares de trabajo")
                }
            }

            override fun onFailure(call: Call<List<LugarTrabajoModel>>, t: Throwable) {
                showToast("Error al conectar con el servidor")
            }
        })
    }

    private fun loadMaquinarias() {
        val maquinariaService = RetrofitInstance.createService(MaquinariaApiService::class.java)
        maquinariaService.getAllMaquinarias().enqueue(object : Callback<List<MaquinariaModel>> {
            override fun onResponse(call: Call<List<MaquinariaModel>>, response: Response<List<MaquinariaModel>>) {
                if (response.isSuccessful && response.body() != null) {
                    val maquinarias = response.body()!!
                    val marcas = maquinarias.map { it.nombreMarca }

                    val adapter = ArrayAdapter(
                        requireContext(),
                        android.R.layout.simple_spinner_item,
                        marcas
                    )
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    spinnerMaquinarias.adapter = adapter
                } else {
                    showToast("Error al cargar maquinarias")
                }
            }

            override fun onFailure(call: Call<List<MaquinariaModel>>, t: Throwable) {
                showToast("Error al conectar con el servidor")
            }
        })
    }

    private fun validateHorometro(): Boolean {
        val input = plainTextHorometro.text.toString()
        if (input.isBlank()) {
            showToast("Ingrese un valor para el horómetro")
            return false
        }
        return try {
            val value = input.toDouble()
            if (value > 0) true else {
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
}
