package com.example.laptopcarebeta

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.addCallback
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class HomeFragment : Fragment() {

    private lateinit var homenamausertextview: TextView
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var homenamalaptoptextview: TextView
    private lateinit var homesistemoperasitextview: TextView
    private lateinit var homeprocessortextview: TextView
    private lateinit var homemerklaptoptextview: TextView
    private lateinit var hometypelaptoptextview: TextView

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: HomeInfoLaptopAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPreferences = requireActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val namaAkun = sharedPreferences.getString("nama_akun", "")
        val namaLaptop = sharedPreferences.getString("nama_laptop", "")
        val sistemoperasi = sharedPreferences.getString("sistem_operasi", "")
        val processorl = sharedPreferences.getString("processor", "")
        val merklaptop = sharedPreferences.getString("merk_laptop", "")
        val typelaptop = sharedPreferences.getString("type_laptop", "")

        recyclerView = view.findViewById(R.id.HomeInfoLaptopRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        adapter = HomeInfoLaptopAdapter(ArrayList())
        recyclerView.adapter = adapter

        homenamausertextview = view.findViewById(R.id.HomeNamaUserTextView)
        homenamalaptoptextview = view.findViewById(R.id.HomeNamaLaptopTextView)
        homesistemoperasitextview = view.findViewById(R.id.HomeSistemOperasiLaptopTextView)
        homeprocessortextview = view.findViewById(R.id.HomeProcessorLaptopTextView)
        homemerklaptoptextview = view.findViewById(R.id.HomeMerkLaptopTextView)
        hometypelaptoptextview = view.findViewById(R.id.HomeTypeLaptopTextView)

        homenamausertextview.text = "$namaAkun"
        homenamalaptoptextview.text = "$namaLaptop"
        homesistemoperasitextview.text = "$sistemoperasi"
        homeprocessortextview.text = "$processorl"
        homemerklaptoptextview.text = "$merklaptop"
        hometypelaptoptextview.text = "$typelaptop"

        val url = "https://mieruch.000webhostapp.com/info_laptop.php"
        val requestQueue = Volley.newRequestQueue(activity)
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                val jsonObject = JSONObject(response)
                if (jsonObject.has("data")) {
                    val jsonArray = jsonObject.getJSONArray("data")
                    val infolaptopList = ArrayList<Modelinfolaptop>()
                    for (i in 0 until jsonArray.length()) {
                        val jsonObject = jsonArray.getJSONObject(i)
                        val idinfolaptop = jsonObject.getInt("id_info_laptop")
                        val namainfolaptop = jsonObject.getString("nama_info_laptop")
                        val desinfolaptop = jsonObject.getString("des_info_laptop")
                        infolaptopList.add(Modelinfolaptop(idinfolaptop, namainfolaptop, desinfolaptop))
                    }
                    adapter.infolaptopList = infolaptopList
                    adapter.notifyDataSetChanged()
                } else {
                    val infolaptopList = ArrayList<Modelinfolaptop>()
                    infolaptopList.add(Modelinfolaptop(0, "No Info Laptop available", "No Info Laptop available"))
                    adapter.infolaptopList = infolaptopList
                    adapter.notifyDataSetChanged()
                }
            },
            { error ->
                Toast.makeText(requireContext(), error.toString(), Toast.LENGTH_SHORT).show()
            })
        requestQueue.add(stringRequest)

    }

    override fun onResume() {
        super.onResume()
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            showExitConfirmationDialog()
        }
    }

    private fun showExitConfirmationDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle("Keluar dari Aplikasi")
            .setMessage("Apakah Anda yakin ingin keluar dari aplikasi?")
            .setPositiveButton("Ya") { _, _ ->
                val sharedPreferences: SharedPreferences = requireActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
                val editor = sharedPreferences.edit()
                editor.clear() // Menghapus semua data dari SharedPreferences
                editor.apply()
                requireActivity().finish()
            }
            .setNegativeButton("Tidak", null)
            .setCancelable(false)
            .show()
    }
}
