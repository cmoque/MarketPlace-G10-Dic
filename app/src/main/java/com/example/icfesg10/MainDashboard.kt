package com.example.icfesg10

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Toast
import com.example.icfesg10.databinding.ActivityDashboardBinding
import com.example.icfesg10.databinding.ActivityMainBinding
import com.example.icfesg10.databinding.ActivityMainPreguntasBinding
import com.example.icfesg10.model.Pregunta
import com.example.icfesg10.model.User
import com.example.icfesg10.model.test
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*
import kotlin.collections.ArrayList
import kotlin.random.Random
import kotlin.random.nextInt

class MainDashboard : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityDashboardBinding

    private lateinit var listaPreguntas: ArrayList<Pregunta>
    private lateinit var listaTest: ArrayList<test>
    private lateinit var listaUser: ArrayList<User>

    private val db=FirebaseFirestore.getInstance()

    var database = Firebase.database
    var idTest:Int=0
    var dbReferenciaPreguntas = database.getReference("preguntas")
    var dbReferenceTest = database.getReference("test")
    var dbReferenceUser = database.getReference("users")
    var username: String =""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(findViewById(R.id.my_toolbar))
        supportActionBar?.title = resources.getString(R.string.dashboard_page_title)

        auth = Firebase.auth
        val currentUser = auth.currentUser
        listaPreguntas = ArrayList<Pregunta>()
        listaTest = ArrayList<test>()
        listaUser = ArrayList<User>()

        binding.btnMostrarCuestionarios.setOnClickListener {
            val intent = Intent(this, MainCuestionarios::class.java)
            this.startActivity(intent)
        }

        binding.btnMostrarPreguntas.setOnClickListener {
            val intent = Intent(this, MainPreguntas::class.java)
            this.startActivity(intent)
        }


        binding.btnCrearTest.setOnClickListener{
                Generartest()
        }
        getTest()
        getUser(currentUser?.email.toString())
        verListadoPreguntas()
    }

    private fun Generartest(){
        var testfinal: ArrayList<Pregunta> =ArrayList<Pregunta>()
        var listaSelecciona: ArrayList<Int> = ArrayList()
        var posicion: Int
        var termino:Boolean =false
        while (!termino){
            posicion= Random.nextInt(listaPreguntas.indices)
            if (!listaSelecciona.contains(posicion)) {
                listaSelecciona.add(posicion)
                testfinal.add(listaPreguntas[posicion])
            }
            if(listaSelecciona.size==5)
                termino=true
        }
        for (item in testfinal){
            var test = test(
                UUID.randomUUID().toString(),
                idTest,
                item.id,
                item.PreTexto,
                "",
                item.Respuesta,
                username

            )
            println(item)
            dbReferenceTest.child(test.id.toString()).setValue(test)
        }

        Toast.makeText(this, "Se creó el test Aleatoriamente $idTest", Toast.LENGTH_LONG).show()
    }
    private fun verListadoPreguntas() {
        val preguntaItemListener = object : ValueEventListener {
            override fun onDataChange(datasnapshot: DataSnapshot) {
                for (pre in datasnapshot.children) {

                    val mapPregunta: Map<String, Any> = pre.value as HashMap<String, Any>

                    var pregunta: Pregunta = Pregunta(
                        mapPregunta.get("id").toString(),
                        mapPregunta.get("preTexto").toString(),
                        mapPregunta.get("opcion1").toString(),
                        mapPregunta.get("opcion2").toString(),
                        mapPregunta.get("opcion3").toString(),
                        mapPregunta.get("respuesta").toString(),
                        mapPregunta.get("area").toString(),
                        mapPregunta.get("descripcion").toString()
                    )
                    listaPreguntas.add(pregunta)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        }
        dbReferenciaPreguntas.addValueEventListener(preguntaItemListener)
    }

    private fun getTest(){

        val TestItemListener = object : ValueEventListener {
            override fun onDataChange(datasnapshot: DataSnapshot) {
                for (pre in datasnapshot.children) {
                    val mapTest: Map<String, Any> = pre.value as HashMap<String, Any>

                    var tests: test = test(
                        mapTest.get("id").toString(),
                        mapTest.get("idtest").toString()?.toInt(),
                        mapTest.get("idpregunta").toString(),
                        mapTest.get("pregunta").toString(),
                        mapTest.get("respuesta").toString(),
                        mapTest.get("rescorrecta").toString(),
                        mapTest.get("usuario").toString()
                    )
                    listaTest.add(tests)
                    if (tests.idtest>idTest)
                        idTest=tests.idtest
                }
                println("El máximo test es: $idTest")
                idTest=idTest+1
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        }
        dbReferenceTest.addValueEventListener(TestItemListener)
    }

    private fun getUser(email:String){
         val userItemListener = object: ValueEventListener{
             override fun onDataChange(datasnapshot: DataSnapshot) {
                for (user in datasnapshot.children){
                    val mapUser: Map<String, Any> = user.value as HashMap<String, Any>
                    if ( email==mapUser.get("email").toString()) {
                        var users: User = User(
                            mapUser.get("uid").toString(),
                            mapUser.get("name").toString(),
                            mapUser.get("lastname").toString(),
                            mapUser.get("username").toString(),
                            mapUser.get("email").toString(),
                            mapUser.get("role").toString().toInt()
                        )
                        listaUser.add(users)
                        username =mapUser.get("username").toString()
                    }
                }
                 println("el user name es $username")
             }
             override fun onCancelled(error: DatabaseError) {
                 TODO("Not yet implemented")
             }
         }
        dbReferenceUser.addValueEventListener(userItemListener)
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