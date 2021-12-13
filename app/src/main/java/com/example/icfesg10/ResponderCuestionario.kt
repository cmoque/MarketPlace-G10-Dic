package com.example.icfesg10

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.icfesg10.databinding.ActivityResponderCuestionarioBinding
import com.example.icfesg10.model.test
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.initialize

class ResponderCuestionario : AppCompatActivity() {
    private lateinit var binding: ActivityResponderCuestionarioBinding

    val database = Firebase.database
    val dbReferencePreguntas = database.getReference("test")
    var bundle: Bundle? = null
    lateinit var cuestionario: test

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResponderCuestionarioBinding.inflate(layoutInflater)
        setContentView(binding.root)
        bundle = intent.extras
        cuestionario = bundle?.get("cuestionario") as test
        setSupportActionBar(findViewById(R.id.my_toolbar))
        supportActionBar?.title = cuestionario.id

        Firebase.initialize(this)
        mostrarCuestionario()

        binding.btnEnviar.setOnClickListener {
            guardarRespuesta()
        }

        binding.btnCancelar.setOnClickListener {
            val intent = Intent(this, DetalleCuestionario::class.java)
            intent.putExtra("cuestionario", cuestionario)
            this.startActivity(intent)
        }
    }

    private fun guardarRespuesta() {
        cuestionario.respuesta = binding.resResPregunta.text.toString()

        dbReferencePreguntas.child(cuestionario.id).setValue(cuestionario)

        Toast.makeText(
            this,
            resources.getString(R.string.msg_resolve_test_success),
            Toast.LENGTH_LONG
        ).show()

        val intent = Intent(this, DetalleCuestionario::class.java)
        intent.putExtra("cuestionario", cuestionario)
        this.startActivity(intent)
    }

    private fun mostrarCuestionario() {

        binding.resPregunta.setText(cuestionario.pregunta).toString()
        binding.resResPregunta.setText(cuestionario.respuesta).toString()
    }

}