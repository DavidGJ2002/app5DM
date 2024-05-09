package com.appsbig.app5dm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val lvusuarios = findViewById<ListView>(R.id.lv_usuarios)
        val queue : RequestQueue = Volley.newRequestQueue(this)
        val url = "https://jsonplaceholder.typicode.com/users"

        val listUsuarios: ArrayList<String> = ArrayList()
        val adaptador = ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, listUsuarios)
        lvusuarios.adapter = adaptador

        val request = JsonArrayRequest(Request.Method.GET, url, null,
            { response ->
                try {
                    for (i in 0 until response.length()) {
                        val jsonObject = response.getJSONObject(i)
                        val nombreUsuario = jsonObject.getString("name")
                        val username = jsonObject.getString("username")
                        val email = jsonObject.getString("email")
                        listUsuarios.add(nombreUsuario + "-" + username + "-"+email)
                    }
                    adaptador.notifyDataSetChanged()
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            { error ->
                Toast.makeText(
                    this,
                    "Error al obtener los usuarios: ${error.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        )
        queue.add(request)
    }
}