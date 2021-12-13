package com.example.icfesg10

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.icfesg10.model.test

class DetalleCuestionarioAdapter(
    private val mContext: Context,
    val listaCuestionarios: List<test>
) :
    ArrayAdapter<test>(mContext, 0, listaCuestionarios) {
    override fun getView(posicion: Int, view: View?, viewGroup: ViewGroup): View {
        val layout =
            LayoutInflater.from(mContext)
                .inflate(R.layout.detalle_cuestionario_item, viewGroup, false)
        val cuestionario = listaCuestionarios[posicion]

        layout.findViewById<TextView>(R.id.tvName).text = cuestionario.pregunta

        if (cuestionario.respuesta == "") {
            layout.findViewById<TextView>(R.id.tvStatus).text =
                mContext.resources.getString(R.string.txt_show_test_status_label) +
                        mContext.resources.getString(R.string.txt_show_test_status_unresolved)
        } else {
            layout.findViewById<TextView>(R.id.tvStatus).text =
                mContext.resources.getString(R.string.txt_show_test_status_label) +
                        mContext.resources.getString(R.string.txt_show_test_status_resolved)
        }

        return layout
    }
}
