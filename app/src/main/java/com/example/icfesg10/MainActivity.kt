package com.example.icfesg10

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import com.example.icfesg10.databinding.ActivityMainBinding
import com.google.firebase.FirebaseApp
import com.google.firebase.appcheck.FirebaseAppCheck
import com.google.firebase.appcheck.debug.DebugAppCheckProviderFactory
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(findViewById(R.id.my_toolbar))

        FirebaseApp.initializeApp(/*context=*/this)
        val firebaseAppCheck = FirebaseAppCheck.getInstance()
        firebaseAppCheck.installAppCheckProviderFactory(
            DebugAppCheckProviderFactory.getInstance()
        )
        auth = Firebase.auth

        binding.btningresar.setOnClickListener {
            val edtEmail = findViewById<EditText>(R.id.edtEmail)
            val edtPassword = findViewById<EditText>(R.id.edtpassword)
            var validate = true

            if (edtEmail.text.toString().trim() == ""
                || !android.util.Patterns.EMAIL_ADDRESS.matcher(edtEmail.text).matches()
            ) {
                edtEmail.error = resources.getString(R.string.txt_main_activity_error_email)
                validate = false
            }

            if (edtPassword.text.toString().trim() == "") {
                edtPassword.error = resources.getString(R.string.txt_main_activity_error_password)
                validate = false
            }

            if (validate) {
                onLogin(edtEmail.text.toString(), edtPassword.text.toString())
            }
        }

        binding.btnregistrese.setOnClickListener {
            registrarse()
        }
    }


    fun registrarse() {
        val intento = Intent(this, Signup::class.java)
        startActivity(intento)
    }

    fun onLogin(email: String, pass: String) {
        auth.signInWithEmailAndPassword(email, pass)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    startActivity(Intent(this, MainDashboard::class.java))
                } else {
                    clearFormLogin()
                    Toast.makeText(
                        this,
                        resources.getString(R.string.txt_main_activity_on_login),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
    }

    private fun clearFormLogin() {
        val edtEmail = findViewById<EditText>(R.id.edtEmail)
        val edtPassword = findViewById<EditText>(R.id.edtpassword)

        edtEmail.setText("")
        edtEmail.requestFocus()
        edtPassword.setText("")
    }

    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            startActivity(Intent(this, MainDashboard::class.java))
        }
    }
}