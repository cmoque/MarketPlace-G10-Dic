package com.example.icfesg10

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.icfesg10.databinding.ActivityAdicionarPreguntasBinding
import com.example.icfesg10.model.Pregunta
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.initialize
import java.util.*

class AdicionarPreguntas : AppCompatActivity() {
    private lateinit var binding: ActivityAdicionarPreguntasBinding

    val database = Firebase.database
    val dbReferencePreguntas = database.getReference("preguntas")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdicionarPreguntasBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(findViewById(R.id.my_toolbar))
        supportActionBar?.title =  resources.getString(R.string.txt_add_questions_title)

        Firebase.initialize(this)

        binding.btnGuardar.setOnClickListener {
            guardarPreguntas()
        }

        binding.btnCancelar.setOnClickListener {
            salir()
        }
    }

    private fun guardarPreguntas() {
        var pregunta = Pregunta(
            UUID.randomUUID().toString(),
            binding.edtTexto.text.toString(),
            binding.edtopcion1.text.toString(),
            binding.edtopcion2.text.toString(),
            binding.edtOpcion3.text.toString(),
            binding.edtRespuesta.text.toString(),
            binding.edtarea.text.toString(),
            binding.edtDescripcion.text.toString()
        )
        dbReferencePreguntas.child(pregunta.id.toString()).setValue(pregunta)
        Toast.makeText(this,  resources.getString(R.string.txt_add_questions_message), Toast.LENGTH_LONG).show()

        this.startActivity(Intent(this, MainPreguntas::class.java))
    }

    private fun salir() {
        startActivity(Intent(this, MainPreguntas::class.java))
    }
}