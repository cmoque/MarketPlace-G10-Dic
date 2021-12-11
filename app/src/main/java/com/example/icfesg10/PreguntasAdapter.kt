package com.example.icfesg10

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.icfesg10.model.Pregunta

class PreguntasAdapter(private val mContext: Context, val listaPreguntas: List<Pregunta>) :
    ArrayAdapter<Pregunta>(mContext, 0, listaPreguntas) {
    override fun getView(posicion: Int, view: View?, viewGroup: ViewGroup): View {
        val layout =
            LayoutInflater.from(mContext).inflate(R.layout.preguntas_item, viewGroup, false)
        val pregunta = listaPreguntas[posicion]

        /*layout.findViewById<TextView>(R.id.tvOpcion1).text=pregunta.Opcion1
        layout.tvOpcion2.text=pregunta.Opcion2
        layout.tvOpcion3.text=pregunta.Opcion3
        layout.tvRespuesta.text=pregunta.Respuesta*/
        layout.findViewById<TextView>(R.id.tvArea).text = pregunta.Area
        layout.findViewById<TextView>(R.id.tvDescripcion).text = pregunta.Descripcion
        return layout
    }
}
