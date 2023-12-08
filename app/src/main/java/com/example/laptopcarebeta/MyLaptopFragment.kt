package com.example.laptopcarebeta

import android.content.Context
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

class MyLaptopFragment : Fragment() {

    private lateinit var btnbackdarimylaptoptoprofile: ImageView
    private lateinit var mylaptopmerktextview: TextView
    private lateinit var mylaptoptypetextview: TextView
    private lateinit var mylaptopnameedittext: EditText
    private lateinit var mylaptopsistemoperasiedittext: EditText
    private lateinit var mylaptopprocessoredittext: EditText
    private lateinit var mylaptopmerklaptopedittext: EditText
    private lateinit var mylaptoptypelaptopedittext: EditText
    private lateinit var mylaptopbtnupdatebutton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mylaptop, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnbackdarimylaptoptoprofile = view.findViewById(R.id.btnbackdarimylaptoptoprofile)
        mylaptopmerktextview = view.findViewById(R.id.MyLaptopMerkTextView)
        mylaptoptypetextview = view.findViewById(R.id.MyLaptopTypeTextView)
        mylaptopnameedittext = view.findViewById(R.id.MyLaptopNameEditText)
        mylaptopsistemoperasiedittext = view.findViewById(R.id.MyLaptopSistemOperasiEditText)
        mylaptopprocessoredittext = view.findViewById(R.id.MyLaptopProcessorEditText)
        mylaptopmerklaptopedittext = view.findViewById(R.id.MyLaptopMerkLaptopEditText)
        mylaptoptypelaptopedittext = view.findViewById(R.id.MyLaptopTypeLaptopEditText)
        mylaptopbtnupdatebutton = view.findViewById(R.id.MyLaptopBtnUpdateButton)

        val sharedPreferences = requireActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val idakun = sharedPreferences.getString("id_akun", "")
        val namalaptop = sharedPreferences.getString("nama_laptop", "")
        val sistemoperasi = sharedPreferences.getString("sistem_operasi", "")
        val processorl = sharedPreferences.getString("processor", "")
        val merklaptop = sharedPreferences.getString("merk_laptop", "")
        val typelaptop = sharedPreferences.getString("type_laptop", "")

        mylaptopmerktextview.text = "$merklaptop"
        mylaptoptypetextview.text = "$typelaptop"
        mylaptopnameedittext.setText("$namalaptop")
        mylaptopsistemoperasiedittext.setText("$sistemoperasi")
        mylaptopprocessoredittext.setText("$processorl")
        mylaptopmerklaptopedittext.setText("$merklaptop")
        mylaptoptypelaptopedittext.setText("$typelaptop")

        btnbackdarimylaptoptoprofile.setOnClickListener {
            requireActivity().onBackPressed()
        }

        mylaptopbtnupdatebutton.setOnClickListener {
            val url = "https://mieruch.000webhostapp.com/laptop.php"
            val id = idakun.toString()
            val namalap = mylaptopnameedittext.text.toString()
            val sisop = mylaptopsistemoperasiedittext.text.toString()
            val proces = mylaptopprocessoredittext.text.toString()
            val merk = mylaptopmerklaptopedittext.text.toString()
            val type = mylaptoptypelaptopedittext.text.toString()

            if (namalap.isNotEmpty() && sisop.isNotEmpty() && proces.isNotEmpty() && merk.isNotEmpty() && type.isNotEmpty()){
                val stringRequest = object : StringRequest(
                    Request.Method.POST,
                    url,
                    Response.Listener<String> { response ->
                        val jsonResponse = JSONObject(response)
                        val status = jsonResponse.getString("status")

                        if (status == "success") {
                            val idLaptop = jsonResponse.getString("id_laptop")
                            val namalaptop = jsonResponse.getString("nama_laptop")
                            val sistemoperasi = jsonResponse.getString("sistem_operasi")
                            val processorl = jsonResponse.getString("processor")
                            val merklaptop = jsonResponse.getString("merk_laptop")
                            val typelaptop = jsonResponse.getString("type_laptop")
                            val idAkun = jsonResponse.getString("id_akun")

                            val sharedPreferences = requireActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
                            val editor = sharedPreferences.edit()
                            editor.putString("id_laptop", idLaptop)
                            editor.putString("nama_laptop", namalaptop)
                            editor.putString("sistem_operasi", sistemoperasi)
                            editor.putString("processor", processorl)
                            editor.putString("merk_laptop", merklaptop)
                            editor.putString("type_laptop", typelaptop)
                            editor.putString("id_akun", idAkun)
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
                        params["nama_laptop"] = namalap
                        params["sistem_operasi"] = sisop
                        params["processor"] = proces
                        params["merk_laptop"] = merk
                        params["type_laptop"] = type
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
            }else{
                Toast.makeText(requireContext(), "Data tidak boleh kosong", Toast.LENGTH_SHORT).show()
            }
        }
    }
}