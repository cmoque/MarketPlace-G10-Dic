package com.example.icfesg10

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.example.icfesg10.database.SaberProDB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MenuProfesorActivity : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


/*
    private fun verpregunta(fragmento: View, idPregunta: Int) {
        var pregunta: Pregunta = Pregunta(0, "", "", "", "", "", "", "")

        CoroutineScope(Dispatchers.IO).launch {
            //obtener la instancia de la BDs
            val database = context?.let { SaberProDB.getDatabase(it) }

            //consultamos la pelicula x ID en la BDs
            pregunta = database?.SaberProDAO()?.getPreguntaPorId(idPregunta)!!

            val edtArea = fragmento.findViewById<EditText>(R.id.edtarea)
            val edtDescripcion = fragmento.findViewById<EditText>(R.id.edtDescripcion)
            edtArea.setText(pregunta.Area)
            edtDescripcion.setText(pregunta.Descripcion.toString())

        }
        salir()
    }*/

    private fun salir() {
        activity?.supportFragmentManager?.beginTransaction()
            ?.remove(this)
            ?.commit()
    }
}

