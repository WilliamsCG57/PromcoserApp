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
        val auth = FirebaseAuth.getInstance()

        btnRegister.setOnClickListener{
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        btnLogin.setOnClickListener{
            val email = etEmailLogin.text.toString()
            val password = etPasswordLogin.text.toString()

            if (email == "" && password == "") {
                // Login correcto, iniciar la siguiente actividad
                val intent = Intent(this, PrincipalActivity::class.java)
                startActivity(intent)
                finish() // Finalizar la actividad de login
            } else {
                // Mostrar mensaje de error
                Toast.makeText(this, "Email o contraseña incorrectos", Toast.LENGTH_SHORT).show()
            }
        }


    }
}




