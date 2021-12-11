package com.example.icfesg10

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.icfesg10.databinding.ActivityMainPreguntasBinding
import com.example.icfesg10.model.Pregunta
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class MainPreguntas() : AppCompatActivity() {
    private lateinit var binding: ActivityMainPreguntasBinding
    private lateinit var auth: FirebaseAuth

    private lateinit var listaPreguntas: ArrayList<Pregunta>
    private lateinit var PreguntasAdapter: ArrayAdapter<Pregunta>

    var database = Firebase.database
    var dbReferenciaPreguntas = database.getReference("preguntas")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainPreguntasBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(findViewById(R.id.my_toolbar))
        supportActionBar?.title = resources.getString(R.string.txt_main_questions_title)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        auth = Firebase.auth

        listaPreguntas = ArrayList<Pregunta>()

        binding.btnAdicionaPregunta.setOnClickListener {
            val intent = Intent(this, AdicionarPreguntas::class.java)
            this.startActivity(intent)
        }

        verListadoPreguntas()

        binding.lvPreguntas.setOnItemClickListener { parent, view, position, id ->
            var pregunta = listaPreguntas[position]

            val intent = Intent(this, EditarPreguntas::class.java)
            intent.putExtra("pregunta", pregunta)
            this.startActivity(intent)
        }
    }

    private fun verListadoPreguntas() {
        val preguntaItemListener = object : ValueEventListener {
            override fun onDataChange(datasnapshot: DataSnapshot) {
                for (pel in datasnapshot.children) {

                    // Objeto MAP
                    val mapPregunta: Map<String, Any> = pel.value as HashMap<String, Any>

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
                    PreguntasAdapter = PreguntasAdapter(this@MainPreguntas, listaPreguntas)
                    binding.lvPreguntas.adapter = PreguntasAdapter
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        }
        dbReferenciaPreguntas.addValueEventListener(preguntaItemListener)
    }

    private fun cerrarSesion() {
        auth.signOut()
        val intent = Intent(this, MainActivity::class.java)
        this.startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main_activity, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_logout -> {
                cerrarSesion()
            }
            android.R.id.home -> {
                irPanel()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun irPanel() {
        val intent = Intent(this, MainDashboard::class.java)
        this.startActivity(intent)
    }
}




