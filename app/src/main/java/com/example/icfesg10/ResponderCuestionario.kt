package com.example.icfesg10

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Toast
import com.example.icfesg10.databinding.ActivityResponderCuestionarioBinding
import com.example.icfesg10.model.test
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.initialize
import android.widget.RadioButton
import com.example.icfesg10.model.Pregunta
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener


class ResponderCuestionario : AppCompatActivity() {
    private lateinit var binding: ActivityResponderCuestionarioBinding

    val database = Firebase.database
    val dbReferencePreguntas = database.getReference("test")
    private lateinit var listaPreguntas: ArrayList<Pregunta>
    private lateinit var PreguntasAdapter: ArrayAdapter<Pregunta>

    var bundle: Bundle? = null
    var dbReferenciaPreguntas = database.getReference("preguntas")
    lateinit var cuestionario: test

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResponderCuestionarioBinding.inflate(layoutInflater)
        setContentView(binding.root)
        bundle = intent.extras
        cuestionario = bundle?.get("cuestionario") as test
        setSupportActionBar(findViewById(R.id.my_toolbar))
        supportActionBar?.title = cuestionario.id

        listaPreguntas = ArrayList<Pregunta>()
        Firebase.initialize(this)
        mostrarCuestionario()

        getPreguntas()

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
        if(binding.Opcion1.isChecked())
            cuestionario.respuesta = binding.Opcion1.text.toString()
        else
            if(binding.Opcion2.isChecked())
                cuestionario.respuesta = binding.Opcion2.text.toString()
            else
                cuestionario.respuesta = binding.Opcion3.text.toString()


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
        //binding.resResPregunta.setText(cuestionario.respuesta).toString()
    }

    private fun getPreguntas() {
        val preguntaItemListener = object : ValueEventListener {
            override fun onDataChange(datasnapshot: DataSnapshot) {
                for (pre in datasnapshot.children) {
                    println("Ingresó al for")
                    val mapPregunta: Map<String, Any> = pre.value as HashMap<String, Any>
                    if (mapPregunta.get("id").toString()==cuestionario.idpregunta){
                        println(mapPregunta.get("id").toString())
                    var pregunta: Pregunta = Pregunta(
                        mapPregunta.get("id").toString(),
                        mapPregunta.get("preTexto").toString(),
                        mapPregunta.get("opcion1").toString(),
                        mapPregunta.get("opcion2").toString(),
                        mapPregunta.get("opcion3").toString(),
                        mapPregunta.get("respuesta").toString(),
                        mapPregunta.get("area").toString(),
                        mapPregunta.get("descripcion").toString()
                    )
                    listaPreguntas.add(pregunta)
                    }
                }
                val r1=findViewById<RadioButton>(R.id.Opcion1)
                val r2=findViewById<RadioButton>(R.id.Opcion2)
                val r3=findViewById<RadioButton>(R.id.Opcion3)

                r1.setText(listaPreguntas[0].Opcion1)
                r2.setText(listaPreguntas[0].Opcion2)
                r3.setText(listaPreguntas[0].Opcion3)

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        }
        dbReferenciaPreguntas.addValueEventListener(preguntaItemListener)
    }

}