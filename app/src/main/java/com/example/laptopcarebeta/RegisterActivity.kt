package com.example.laptopcarebeta

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.RetryPolicy
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

class RegisterActivity : AppCompatActivity() {

    private lateinit var btnbackdariregistertomenu: ImageView
    private lateinit var btntologin: TextView
    private lateinit var btndaftar: Button
    private lateinit var registernamaedittext: EditText
    private lateinit var registerusernameedittext: EditText
    private lateinit var registeremailedittext: EditText
    private lateinit var registerpasswordedittext: EditText
    private lateinit var registerpasswordkembaliedittext: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        btnbackdariregistertomenu = findViewById(R.id.btnbackdariregistertomenu)
        btntologin = findViewById(R.id.btntologin)
        btndaftar = findViewById(R.id.btndaftar)
        registernamaedittext = findViewById(R.id.RegisterNamaEditText)
        registerusernameedittext = findViewById(R.id.RegisterUsernameEditText)
        registeremailedittext = findViewById(R.id.RegisterEmailEditText)
        registerpasswordedittext = findViewById(R.id.RegisterPasswordEditText)
        registerpasswordkembaliedittext = findViewById(R.id.RegisterPasswordKembaliEditText)

        btnbackdariregistertomenupage()
        btntologinpage()

        btndaftar.setOnClickListener {
            val nama = registernamaedittext.text.toString()
            val user = registerusernameedittext.text.toString()
            val em = registeremailedittext.text.toString()
            val pass = registerpasswordedittext.text.toString()
            val passkem = registerpasswordkembaliedittext.text.toString()
            val url = "https://mieruch.000webhostapp.com/register.php"

            if (nama.isNotEmpty() && user.isNotEmpty() && em.isNotEmpty() && pass.isNotEmpty() && passkem.isNotEmpty()){
                if(pass == passkem){
                    val stringRequest = object : StringRequest(
                        Request.Method.POST,
                        url,
                        Response.Listener<String> { response ->
                            // Menggunakan respons di sini
                            Toast.makeText(applicationContext, response.toString(), Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this, LoginActivity::class.java))
                        },
                        Response.ErrorListener { error ->
                            // Menangani kesalahan di sini
                            Toast.makeText(applicationContext, error.toString(), Toast.LENGTH_SHORT).show()
                        }
                    ) {
                        override fun getParams(): HashMap<String, String> {
                            val params = HashMap<String, String>()
                            params["nama_akun"] = nama
                            params["username"] = user
                            params["email"] = em
                            params["password"] = pass
                            return params
                        }
                    }
                    val socketTimeout = 30000 // 30 detik
                    val policy: RetryPolicy = DefaultRetryPolicy(socketTimeout,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
                    stringRequest.retryPolicy = policy
                    val requestQueue = Volley.newRequestQueue(this)
                    requestQueue.add(stringRequest)
                }else{
                    Toast.makeText(applicationContext, "Password Tidak Sama!", Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(applicationContext, "Data Tidak Boleh Kosong!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun btntologinpage() {
        btntologin.setOnClickListener{
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }

    private fun btnbackdariregistertomenupage() {
        btnbackdariregistertomenu.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}