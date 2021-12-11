package com.example.icfesg10

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.example.icfesg10.databinding.ActivityEditarPreguntasBinding
import android.os.Bundle
import android.widget.Toast
import com.example.icfesg10.model.Pregunta
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.initialize

class EditarPreguntas : AppCompatActivity() {
    lateinit var binding: ActivityEditarPreguntasBinding

    var database = Firebase.database
    var dbReferencePregunta = database.getReference("preguntas")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditarPreguntasBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(findViewById(R.id.my_toolbar))
        supportActionBar?.title =  resources.getString(R.string.txt_edit_questions_title)

        Firebase.initialize(this)
        mostrarDatosPregunta()
        binding.btnActualizar.setOnClickListener {
            actualizarPregunta()
        }
        binding.btnEliminar.setOnClickListener {
            eliminarPregunta()
        }
        binding.btnCancelar.setOnClickListener {
            salir()
        }

    }

    private fun eliminarPregunta() {
        dbReferencePregunta.child(binding.edtId.text.toString()).removeValue()
        Toast.makeText(
            this,
            resources.getString(R.string.txt_edit_questions_remove) + " ${binding.edtTexto.text.toString()}",
            Toast.LENGTH_LONG
        ).show()

        verListadoPreguntas()
    }

    private fun mostrarDatosPregunta() {
        var bundle = intent.extras
        val pregunta = bundle?.get("pregunta") as Pregunta

        binding.edtId.setText(pregunta.id).toString()
        binding.edtTexto.setText(pregunta.PreTexto).toString()
        binding.edtopcion1.setText(pregunta.Opcion1.toString())
        binding.edtopcion2.setText(pregunta.Opcion2.toString())
        binding.edtOpcion3.setText(pregunta.Opcion3.toString())
        binding.edtRespuesta.setText(pregunta.Respuesta.toString())
        binding.edtarea.setText(pregunta.Area.toString())
        binding.edtDescripcion.setText(pregunta.Descripcion.toString())
    }

    private fun actualizarPregunta() {
        var pregunta = Pregunta(
            binding.edtId.text.toString(),
            binding.edtTexto.text.toString(),
            binding.edtopcion1.text.toString(),
            binding.edtopcion2.text.toString(),
            binding.edtOpcion3.text.toString(),
            binding.edtRespuesta.text.toString(),
            binding.edtarea.text.toString(),
            binding.edtDescripcion.text.toString()
        )
        dbReferencePregunta.child(pregunta.id).setValue(pregunta)

        verListadoPreguntas()
    }

    private fun verListadoPreguntas() {
        val intent = Intent(this, MainPreguntas::class.java)
        this.startActivity(intent)
    }

    private fun salir() {
        startActivity(Intent(this, MainPreguntas::class.java))
    }
}