package dev.williamscg.promcoserapp.ui.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import dev.williamscg.promcoserapp.R
import dev.williamscg.promcoserapp.apiService.ApiClient
import dev.williamscg.promcoserapp.apiService.ApiClientChangePassword
import dev.williamscg.promcoserapp.model.CambioContrasenaModel


import kotlinx.coroutines.launch
import retrofit2.Response


class CambioContrasenaFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_cambio_contrasena, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val etActualPassword = view.findViewById<EditText>(R.id.etActualPassword)
        val etNewPassword = view.findViewById<EditText>(R.id.etNewPassword)
        val etRepeatNewPassword = view.findViewById<EditText>(R.id.etRepeatNewPassword)
        val btnCambiarContrasena = view.findViewById<Button>(R.id.btnCambiarContrasena)
        val sharedPreferences = requireActivity().getSharedPreferences("userPrefs", Context.MODE_PRIVATE)
        val username = sharedPreferences.getString("userUsuario", "") ?: ""

        btnCambiarContrasena.setOnClickListener {
            val currentPassword = etActualPassword.text.toString()
            val newPassword = etNewPassword.text.toString()
            val repeatNewPassword = etRepeatNewPassword.text.toString()

            if (validateInputs(currentPassword, newPassword, repeatNewPassword)) {
                val requestBody = CambioContrasenaModel(
                    usuario = username,
                    contrasena = currentPassword,
                    newContrasena = newPassword
                )
                changePassword(requestBody)
            }
            limpiarCampos(etActualPassword, etNewPassword, etRepeatNewPassword)
        }
    }

    private fun validateInputs(current: String, new: String, repeat: String): Boolean {
        if (current.isEmpty() || new.isEmpty() || repeat.isEmpty()) {
            Toast.makeText(context, "Todos los campos son requeridos", Toast.LENGTH_SHORT).show()
            return false
        }
        if (new != repeat) {
            Toast.makeText(context, "Las contraseñas nuevas no coinciden", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun changePassword(userChange: CambioContrasenaModel){
        val apiService = ApiClient.getChangePassword(requireContext())
        lifecycleScope.launch {
            try {
                val response = apiService.changePassword(userChange)
                if (response.isSuccessful && response.body() != null) {
                    Toast.makeText(requireContext(), "Contraseña cambiada exitosamente", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(requireContext(), "Error al cambiar la contraseña: ${response.errorBody()?.string()}", Toast.LENGTH_LONG).show()
                }
            } catch (e: Exception) {

            }
        }
    }
    private fun limpiarCampos(vararg editTexts: EditText) {
        for (editText in editTexts) {
            editText.text.clear()
        }
    }

}

