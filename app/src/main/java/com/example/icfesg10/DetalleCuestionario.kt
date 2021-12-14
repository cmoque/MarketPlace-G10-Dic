package com.example.icfesg10

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.icfesg10.databinding.ActivityDetalleCuestionarioBinding
import com.example.icfesg10.model.test
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

    private lateinit var listaCuestionarios: ArrayList<test>
    private lateinit var listaCuestionariosEvaluado: ArrayList<test>
    private lateinit var DetalleCuestionarioAdapter: ArrayAdapter<test>

    var database = Firebase.database
    var dbReferenciaCuestionarios = database.getReference("test")
    var bundle: Bundle? = null
    var nota:Int=0
    lateinit var cuestionario: test

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetalleCuestionarioBinding.inflate(layoutInflater)
        setContentView(binding.root)
        bundle = intent.extras
        cuestionario = bundle?.get("cuestionario") as test
        setSupportActionBar(findViewById(R.id.my_toolbar))

        supportActionBar?.title =
            resources.getString(R.string.txt_test_item_name) + " " + cuestionario.idtest.toString()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        auth = Firebase.auth

        listaCuestionarios = ArrayList<test>()
        listaCuestionariosEvaluado = ArrayList<test>()

        verListaCuestionarios()

        binding.lvCuestionarios.setOnItemClickListener { parent, view, position, id ->
            var cuestionario = listaCuestionarios[position]

            val intent = Intent(this, ResponderCuestionario::class.java)
            intent.putExtra("cuestionario", cuestionario)
            this.startActivity(intent)
        }

        binding.btnevaluar.setOnClickListener {
            evaluartest()
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
                            .toString() == cuestionario.idtest.toString()
                    ) {

                        var cuestionario = test(
                            mapCuestionario.get("id").toString(),
                            mapCuestionario.get("idtest").toString().toInt(),
                            mapCuestionario.get("idpregunta").toString(),
                            mapCuestionario.get("pregunta").toString(),
                            mapCuestionario.get("respuesta").toString(),
                            mapCuestionario.get("resCorrecta").toString(),
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

    private fun evaluartest(){
        nota=0
        val cuestionarioItemListener = object : ValueEventListener {
            override fun onDataChange(datasnapshot: DataSnapshot) {
                println("fun evaluar antes del for")
                for (cus in datasnapshot.children) {
                    println("ingresó al for de evaluar")
                    val mapCuestionario: Map<String, Any> = cus.value as HashMap<String, Any>

                    if (mapCuestionario.get("usuario")
                            .toString() == cuestionario.usuario && mapCuestionario.get("idtest")
                            .toString() == cuestionario.idtest.toString())
                        {
                            var cuestionario = test(
                                mapCuestionario.get("id").toString(),
                                mapCuestionario.get("idtest").toString().toInt(),
                                mapCuestionario.get("idpregunta").toString(),
                                mapCuestionario.get("pregunta").toString(),
                                mapCuestionario.get("respuesta").toString(),
                                mapCuestionario.get("resCorrecta").toString(),
                                mapCuestionario.get("usuario").toString(),
                            )
                            listaCuestionariosEvaluado.add(cuestionario)
                            print("Respuesta:")
                            println(mapCuestionario.get("respuesta").toString())
                            print("Correct:")
                            println(mapCuestionario.get("resCorrecta").toString())
                        if (mapCuestionario.get("respuesta").toString() == mapCuestionario.get("resCorrecta").toString()) {
                            nota += 1
                        }
                    }
                    val etnota=findViewById<EditText>(R.id.etnota)
                    etnota.setText("su nota es de: $nota")
                }

            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        }
        dbReferenciaCuestionarios.addValueEventListener(cuestionarioItemListener)
    }
}




