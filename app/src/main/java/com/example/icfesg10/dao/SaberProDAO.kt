package com.example.icfesg10.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.icfesg10.model.Usuario

@Dao
interface SaberProDAO {
    @Query("Select * from usuario")
    fun getAllUsuarios(): LiveData<List<Usuario>>


    @Query("SELECT * FROM usuario WHERE id=:id")
    fun getUsuarioPorId(id: Int): Usuario


    @Insert
    fun insertUsuario(usuario: Usuario)



    @Update
    fun updateUsuario(usuario: Usuario)


    @Delete
    fun deleteUsuario(usuario: Usuario)


}