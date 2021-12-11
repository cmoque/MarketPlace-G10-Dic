package com.example.icfesg10

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.icfesg10.databinding.ActivityDetalleCuestionarioBinding
import com.example.icfesg10.model.Cuestionario
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class DetalleCuestionario() : AppCompatActivity() {
    private lateinit var binding: ActivityDetalleCuestionarioBinding
    private lateinit var auth: FirebaseAuth

    private lateinit var listaCuestionarios: ArrayList<Cuestionario>
    private lateinit var DetalleCuestionarioAdapter: ArrayAdapter<Cuestionario>

    var database = Firebase.database
    var dbReferenciaCuestionarios = database.getReference("test")
    var bundle: Bundle? = null
    lateinit var cuestionario: Cuestionario

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetalleCuestionarioBinding.inflate(layoutInflater)
        setContentView(binding.root)
        bundle = intent.extras
        cuestionario = bundle?.get("cuestionario") as Cuestionario
        setSupportActionBar(findViewById(R.id.my_toolbar))

        supportActionBar?.title =
            resources.getString(R.string.txt_test_item_name) + cuestionario.idTest.toString()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        auth = Firebase.auth


        listaCuestionarios = ArrayList<Cuestionario>()

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

                    if (mapCuestionario.get("usuario")
                            .toString() == cuestionario.usuario && mapCuestionario.get("idtest")
                            .toString() == cuestionario.idTest.toString()
                    ) {

                        var cuestionario = Cuestionario(
                            mapCuestionario.get("id").toString(),
                            mapCuestionario.get("idpregunta").toString(),
                            mapCuestionario.get("idtest").toString().toInt(),
                            mapCuestionario.get("pregunta").toString(),
                            mapCuestionario.get("resCorrecta").toString(),
                            mapCuestionario.get("respuesta").toString(),
                            mapCuestionario.get("usuario").toString(),
                        )
                        listaCuestionarios.add(cuestionario)
                        DetalleCuestionarioAdapter =
                            DetalleCuestionarioAdapter(this@DetalleCuestionario, listaCuestionarios)
                        binding.lvCuestionarios.adapter = DetalleCuestionarioAdapter
                    }
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
        val intent = Intent(this, MainCuestionarios::class.java)
        this.startActivity(intent)
    }
}




