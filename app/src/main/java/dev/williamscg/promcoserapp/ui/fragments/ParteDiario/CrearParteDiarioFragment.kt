package dev.williamscg.promcoserapp.ui.fragments.ParteDiario

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import dev.williamscg.promcoserapp.R

class CrearParteDiarioFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflar la vista
        val view = inflater.inflate(R.layout.fragment_crear_parte_diario, container, false)

        // Declarar y configurar los Spinners
        val spTrabajo: Spinner = view.findViewById(R.id.spLugarTrabajo)
        val spLugar: Spinner = view.findViewById(R.id.spMaquinaria)
        val spMaquina: Spinner = view.findViewById(R.id.spCliente)

        // Configurar adaptadores para cada Spinner
        try {
            val trabajoAdapter = ArrayAdapter.createFromResource(
                requireContext(),
                R.array.trabajo_array,  // Asegúrate de que este array exista en strings.xml
                android.R.layout.simple_spinner_item
            )
            trabajoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spTrabajo.adapter = trabajoAdapter

            val lugarAdapter = ArrayAdapter.createFromResource(
                requireContext(),
                R.array.lugar_array,  // Asegúrate de que este array exista
                android.R.layout.simple_spinner_item
            )
            lugarAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spLugar.adapter = lugarAdapter

            val maquinaAdapter = ArrayAdapter.createFromResource(
                requireContext(),
                R.array.maquina_array,  // Asegúrate de que este array exista
                android.R.layout.simple_spinner_item
            )
            maquinaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spMaquina.adapter = maquinaAdapter

            // Configurar eventos para detectar selección en los Spinners
            spTrabajo.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                    val trabajoSeleccionado = parent.getItemAtPosition(position).toString()
                    Log.d("CrearParteDiarioFragment", "Trabajo seleccionado: $trabajoSeleccionado")
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    Log.d("CrearParteDiarioFragment", "Ningún trabajo seleccionado")
                }
            }

            spLugar.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                    val lugarSeleccionado = parent.getItemAtPosition(position).toString()
                    Log.d("CrearParteDiarioFragment", "Lugar seleccionado: $lugarSeleccionado")
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    Log.d("CrearParteDiarioFragment", "Ningún lugar seleccionado")
                }
            }

            spMaquina.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                    val maquinaSeleccionada = parent.getItemAtPosition(position).toString()
                    Log.d("CrearParteDiarioFragment", "Máquina seleccionada: $maquinaSeleccionada")
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    Log.d("CrearParteDiarioFragment", "Ninguna máquina seleccionada")
                }
            }

        } catch (e: Exception) {
            Log.e("CrearParteDiarioFragment", "Error al configurar los Spinners: ${e.message}")
        }

        return view
    }

}