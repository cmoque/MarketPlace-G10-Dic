package com.example.icfesg10

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.icfesg10.databinding.ActivityMainCuestionariosBinding
import com.example.icfesg10.model.Cuestionario
import com.example.icfesg10.model.Pregunta
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class MainCuestionarios() : AppCompatActivity() {
    private lateinit var binding: ActivityMainCuestionariosBinding
    private lateinit var auth: FirebaseAuth

    private lateinit var listaCuestionarios: ArrayList<Cuestionario>
    private lateinit var CuestionariosAdapter: ArrayAdapter<Cuestionario>

    var database = Firebase.database
    var dbReferenciaCuestionarios = database.getReference("test")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainCuestionariosBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(findViewById(R.id.my_toolbar))

        supportActionBar?.title = resources.getString(R.string.txt_main_test_title)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        auth = Firebase.auth

        listaCuestionarios = ArrayList<Cuestionario>()

        binding.btnAdicionarCuestionario.setOnClickListener {
            // Añadir la actividad correcta
//            val intent = Intent(this, AdicionarPreguntas::class.java)
//            this.startActivity(intent)
            Toast.makeText(
                this,
                "Remover este mensaje. Ver comentarios codigo",
                Toast.LENGTH_LONG
            ).show()
        }

        verListaCuestionarios()

        binding.lvCuestionarios.setOnItemClickListener { parent, view, position, id ->
            var cuestionario = listaCuestionarios[position]

            // Añadir la actividad correcta
//            val intent = Intent(this, EditarCuestionario::class.java)
//            intent.putExtra("cuestionario", cuestionario)
//            this.startActivity(intent)
            Toast.makeText(
                this,
                "Remover este mensaje. Ver comentarios codigo",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun verListaCuestionarios() {
        val cuestionarioItemListener = object : ValueEventListener {
            override fun onDataChange(datasnapshot: DataSnapshot) {
                for (pel in datasnapshot.children) {

                    // Objeto MAP
                    val mapCuestionario: Map<String, Any> = pel.value as HashMap<String, Any>

                    var cuestionario: Cuestionario = Cuestionario(
                        mapCuestionario.get("id").toString(),
                        mapCuestionario.get("idpregunta").toString(),
                        mapCuestionario.get("idtest").toString().toInt(),
                        mapCuestionario.get("pregunta").toString(),
                        mapCuestionario.get("resCorrecta").toString(),
                        mapCuestionario.get("respuesta").toString(),
                        mapCuestionario.get("usuario").toString(),
                    )
                    listaCuestionarios.add(cuestionario)
                    CuestionariosAdapter =
                        CuestionariosAdapter(this@MainCuestionarios, listaCuestionarios)
                    binding.lvCuestionarios.adapter = CuestionariosAdapter
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        }
        dbReferenciaCuestionarios.addValueEventListener(cuestionarioItemListener)
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




