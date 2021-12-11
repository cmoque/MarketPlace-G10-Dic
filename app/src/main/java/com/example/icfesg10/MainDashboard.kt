package com.example.icfesg10

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.Toast
import com.example.icfesg10.databinding.ActivityDashboardBinding
import com.example.icfesg10.databinding.ActivityMainBinding
import com.example.icfesg10.databinding.ActivityMainPreguntasBinding
import com.example.icfesg10.model.Pregunta
import com.google.firebase.FirebaseApp
import com.google.firebase.appcheck.FirebaseAppCheck
import com.google.firebase.appcheck.debug.DebugAppCheckProviderFactory
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainDashboard : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(findViewById(R.id.my_toolbar))
        supportActionBar?.title = resources.getString(R.string.dashboard_page_title)

        auth = Firebase.auth

        binding.btnMostrarCuestionarios.setOnClickListener {
            val intent = Intent(this, MainCuestionarios::class.java)
            this.startActivity(intent)
        }

        binding.btnMostrarPreguntas.setOnClickListener {
            val intent = Intent(this, MainPreguntas::class.java)
            this.startActivity(intent)
        }
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
            R.id.action_logout -> cerrarSesion()
        }
        return super.onOptionsItemSelected(item)
    }
}