package com.example.icfesg10

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.icfesg10.model.Cuestionario

class CuestionariosAdapter(
    private val mContext: Context,
    val listaCuestionarios: List<Cuestionario>
) :
    ArrayAdapter<Cuestionario>(mContext, 0, listaCuestionarios) {
    override fun getView(posicion: Int, view: View?, viewGroup: ViewGroup): View {
        val layout =
            LayoutInflater.from(mContext).inflate(R.layout.cuestionarios_item, viewGroup, false)
        val cuestionario = listaCuestionarios[posicion]

        layout.findViewById<TextView>(R.id.tvName).text = cuestionario.pregunta

        return layout
    }
}
