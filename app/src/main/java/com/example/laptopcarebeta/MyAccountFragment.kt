package com.example.laptopcarebeta

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

class MyAccountFragment : Fragment() {

    private lateinit var btnbackdarimyaccounttoprofile: ImageView
    private lateinit var myaccountnamausertextview: TextView
    private lateinit var myaccountusernameltextview: TextView
    private lateinit var myaccountnamaedittext: EditText
    private lateinit var myaccountemailEdittext: EditText
    private lateinit var MyAccountBtnUpdateButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_myaccount, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnbackdarimyaccounttoprofile = view.findViewById(R.id.btnbackdarimyaccounttoprofile)
        myaccountnamausertextview = view.findViewById(R.id.MyAccountNamaUserTextView)
        myaccountusernameltextview = view.findViewById(R.id.MyAccountUsernameTextView)
        myaccountnamaedittext = view.findViewById(R.id.MyAccountNamaEditText)
        myaccountemailEdittext = view.findViewById(R.id.MyAccountEmailEditText)
        MyAccountBtnUpdateButton = view.findViewById(R.id.MyAccountBtnUpdateButton)

        val sharedPreferences = requireActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val idakun = sharedPreferences.getString("id_akun", "")
        val namaAkun = sharedPreferences.getString("nama_akun", "")
        val userName = sharedPreferences.getString("username", "")
        val email = sharedPreferences.getString("email", "")

        myaccountnamausertextview.text = "$namaAkun"
        myaccountusernameltextview.text = "$userName"
        myaccountnamaedittext.setText("$namaAkun")
        myaccountemailEdittext.setText("$email")

        btnbackdarimyaccounttoprofile.setOnClickListener {
            requireActivity().onBackPressed()
        }

        MyAccountBtnUpdateButton.setOnClickListener {
            val id = idakun.toString()
            val nama = myaccountnamaedittext.text.toString()
            val em = myaccountemailEdittext.text.toString()
            val url = "https://mieruch.000webhostapp.com/update_akun.php"

            if (nama.isNotEmpty() && em.isNotEmpty()){
                val stringRequest = object : StringRequest(
                    Request.Method.POST,
                    url,
                    Response.Listener<String> { response ->
                        val jsonResponse = JSONObject(response)
                        val status = jsonResponse.getString("status")

                        if (status == "success") {
                            val idAkun = jsonResponse.getString("id_akun")
                            val namaAkun = jsonResponse.getString("nama_akun")
                            val usr = jsonResponse.getString("username")
                            val eml = jsonResponse.getString("email")

                            val sharedPreferences = requireActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
                            val editor = sharedPreferences.edit()
                            editor.putString("id_akun", idAkun)
                            editor.putString("nama_akun", namaAkun)
                            editor.putString("username", usr)
                            editor.putString("email", eml)
                            editor.apply()
                            Toast.makeText(requireContext(), jsonResponse.getString("message"), Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(requireContext(), jsonResponse.getString("message"), Toast.LENGTH_SHORT).show()
                        }
                        requireActivity().onBackPressed()
                    },
                    Response.ErrorListener { error ->
                        // Handle error
                        Toast.makeText(requireContext(), error.toString(), Toast.LENGTH_SHORT).show()
                    }
                ) {
                    override fun getParams(): HashMap<String, String> {
                        val params = HashMap<String, String>()
                        params["nama_akun"] = nama
                        params["email"] = em
                        params["id_akun"] = id
                        return params
                    }
                }
                val socketTimeout = 30000 // 30 detik
                val policy: RetryPolicy = DefaultRetryPolicy(socketTimeout,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
                stringRequest.retryPolicy = policy
                val requestQueue = Volley.newRequestQueue(requireContext())
                requestQueue.add(stringRequest)
            } else {
                Toast.makeText(requireContext(), "Data tidak boleh kosong", Toast.LENGTH_SHORT).show()
            }
        }
    }
}