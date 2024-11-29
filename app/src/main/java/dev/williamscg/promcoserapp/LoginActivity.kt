package dev.williamscg.promcoserapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import dev.williamscg.promcoserapp.apiService.ApiClient
import dev.williamscg.promcoserapp.apiService.ApiClientLogIn
import dev.williamscg.promcoserapp.model.PromcoserUser
import dev.williamscg.promcoserapp.model.UserModel
import dev.williamscg.promcoserapp.model.UserRequestModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val etEmailLogin = findViewById<EditText>(R.id.etEmailLogin)
        val etPasswordLogin = findViewById<EditText>(R.id.etPasswordLogin)
        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val btnRegister = findViewById<Button>(R.id.btnRegister)

        btnLogin.setOnClickListener{
            val user =  etEmailLogin.text.toString()
            val password = etPasswordLogin.text.toString()

            if (user == "" || password == "") {
                Toast.makeText(this, "Email o contraseña incompletos", Toast.LENGTH_SHORT).show()
            } else {
                IniciarSesion(UserRequestModel(usuario = user, contrasena = password))
            }
        }


    }
    private fun IniciarSesion(user: UserRequestModel) {

        val logInService = ApiClientLogIn.instance
        logInService.signIn(user).enqueue(object : Callback<PromcoserUser> {
            override fun onResponse(call: Call<PromcoserUser>, response: Response<PromcoserUser>) {
                if (response.isSuccessful) {
                    val idPersonal = response.body()?.idPersonal ?: 0
                    val usuario = response.body()?.usuario ?: ""
                    val token = response.body()?.token ?: ""
                    val nombre = response.body()?.nombre ?: ""
                    val apellido = response.body()?.apellido ?: ""
                    val correo = response.body()?.correoElectronico ?: ""

                    val userInfo = PromcoserUser(
                        idPersonal = idPersonal,
                        nombre = nombre,
                        apellido = apellido,
                        correoElectronico = correo,
                        token = token,
                        usuario = usuario
                    )
                    guardarDatosUsuarioEnPreferencias(userInfo)

                    val intent = Intent(this@LoginActivity, PrincipalActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this@LoginActivity, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<PromcoserUser>, t: Throwable) {

            }
        })
    }

    private fun guardarDatosUsuarioEnPreferencias(user: PromcoserUser) {
        val sharedPreferences = getSharedPreferences("userPrefs", MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        editor.putInt("userId", user.idPersonal)
        editor.putString("userToken", user.token)
        editor.putString("userName", user.nombre)
        editor.putString("userApellido", user.apellido)
        editor.putString("userCorreo", user.correoElectronico)
        editor.putString("userUsuario", user.usuario)

        editor.apply()
    }

}




