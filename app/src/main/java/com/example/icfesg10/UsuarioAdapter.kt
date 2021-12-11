package com.example.icfesg10

import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.icfesg10.model.Usuario

class UsuarioAdapter(private val mContext: Context, val listaUsuarios: List<Usuario>) :
    ArrayAdapter<Usuario>(mContext, 0, listaUsuarios), Parcelable {
    constructor(parcel: Parcel) : this(
        TODO("mContext"),
        TODO("listaUsuarios")
    ) {
    }

    override fun getView(posicion: Int, view: View?, viewGroup: ViewGroup): View {
        val layout =
            LayoutInflater.from(mContext).inflate(R.layout.preguntas_item, viewGroup, false)
        /*val usuario = listaUsuarios[posicion]
        layout.tvTexto.text = usuario    //usuario.id.toCtring().Int()  o "${usuario.id}"
        layout.tvOpcion1.text = usuario.Opcion1
        layout.tvOpcion2.text = usuario.Opcion2
        layout.tvOpcion3.text = usuario.Opcion3
        layout.tvRespuesta.text = usuario.Respuesta
        layout.tvArea.text = usuario.Area*/
        return layout
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) = Unit

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<UsuarioAdapter> {
        override fun createFromParcel(parcel: Parcel): UsuarioAdapter {
            return UsuarioAdapter(parcel)
        }

        override fun newArray(size: Int): Array<UsuarioAdapter?> {
            return arrayOfNulls(size)
        }
    }
}