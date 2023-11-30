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
import org.json.JSONObject

class LoginActivity : AppCompatActivity() {

    private lateinit var btnbackdarilogintomenu: ImageView
    private lateinit var btntodaftar: TextView
    private lateinit var btnmasuk: Button
    private lateinit var loginusernameedittext: EditText
    private lateinit var loginpasswordedittext: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btnbackdarilogintomenu = findViewById(R.id.btnbackdarilogintomenu)
        btntodaftar = findViewById(R.id.btntodaftar)
        btnmasuk = findViewById(R.id.btnmasuk)
        loginusernameedittext = findViewById(R.id.LoginUsernameEditText)
        loginpasswordedittext = findViewById(R.id.LoginPasswordEditText)

        btnbackdarilogintomenupage()
        btntodaftarpage()

        btnmasuk.setOnClickListener {
            val user = loginusernameedittext.text.toString()
            val pass = loginpasswordedittext.text.toString()
            val url = "https://mieruch.000webhostapp.com/login.php"

            if (user.isNotEmpty() && pass.isNotEmpty()) {
                val stringRequest = object : StringRequest(
                    Request.Method.POST,
                    url,Response.Listener<String> { response ->
                        // Menggunakan respons di sini
                        val jsonResponse = JSONObject(response)
                        val status = jsonResponse.getString("status")

                        if (status == "success") {
                            val idAkun = jsonResponse.getString("id_akun")
                            val namaAkun = jsonResponse.getString("nama_akun")
                            val usr = jsonResponse.getString("username")

                            // Simpan id_akun dan nama_akun di sini, misalnya menggunakan SharedPreferences
                            val sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)
                            val editor = sharedPreferences.edit()
                            editor.putString("id_akun", idAkun)
                            editor.putString("nama_akun", namaAkun)
                            editor.putString("username", usr)
                            editor.apply()

                            Toast.makeText(applicationContext, jsonResponse.getString("message"), Toast.LENGTH_SHORT).show()
                            val intent = Intent(this, MenuActivity::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            Toast.makeText(applicationContext, jsonResponse.getString("message"), Toast.LENGTH_SHORT).show()
                        }
                    },
                    Response.ErrorListener { error ->
                        // Menangani kesalahan di sini
                        Toast.makeText(applicationContext, error.toString(), Toast.LENGTH_SHORT).show()
                    }
                ) {
                    override fun getParams(): HashMap<String, String> {
                        val params = HashMap<String, String>()
                        params["username"] = user
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
            } else {
                Toast.makeText(applicationContext, "Data Tidak Boleh Kosong!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun btntodaftarpage() {
        btntodaftar.setOnClickListener{
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    private fun btnbackdarilogintomenupage() {
        btnbackdarilogintomenu.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}