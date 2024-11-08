package dev.williamscg.promcoserapp

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val db = FirebaseFirestore.getInstance()
        val tvCurso = findViewById<TextView>(R.id.tvCurso)
        val tvNota = findViewById<TextView>(R.id.tvNota)

        db.collection("courses").addSnapshotListener { snapshots, error ->
            if(error != null){
                Log.e("Firestore", "Error al obtener los datos", error)
                return@addSnapshotListener
            }

            for (document in snapshots!!.documents){
                val curso = document.getString("description")
                val nota = document.getString("score")
                tvCurso.text = curso
                tvNota.text = nota
            }
        }
    }
}

