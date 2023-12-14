package com.example.laptopcarebeta

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class HistoryFragment : Fragment() {

    private lateinit var recyclerViewhistory: RecyclerView
    private lateinit var adapterhistory: HistoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerViewhistory = view.findViewById(R.id.HistoryRecyclerView)
        recyclerViewhistory.layoutManager = LinearLayoutManager(activity)
        adapterhistory = HistoryAdapter(ArrayList())
        recyclerViewhistory.adapter = adapterhistory

        val sharedPreferences = requireActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val idlaptop = sharedPreferences.getString("id_laptop", "")

        val id_laptop = idlaptop.toString() // Ganti dengan id_laptop yang diinginkan
        val url = "https://mieruch.000webhostapp.com/histori.php?id_laptop=$id_laptop"
        val requestQueue = Volley.newRequestQueue(activity)
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                val jsonObject = JSONObject(response)
                if (jsonObject.has("data")) {
                    val jsonArray = jsonObject.getJSONArray("data")
                    val historyList = ArrayList<ModelHistory>()
                    for (i in 0 until jsonArray.length()) {
                        Log.d("Server Response", response)
                        val jsonObject = jsonArray.getJSONObject(i)
                        val id_histori = jsonObject.getInt("id_histori")
                        val judul_histori = jsonObject.getString("judul_histori")
                        val des_history = jsonObject.getString("des_histori")
                        historyList.add(ModelHistory(id_histori, judul_histori, des_history, id_laptop.toInt()))
                    }
                    Log.d("History List", historyList.toString()) // Print the historyList
                    adapterhistory.historylist = historyList
                    adapterhistory.notifyDataSetChanged()
                } else {
                    val historyList = ArrayList<ModelHistory>()
                    historyList.add(ModelHistory(0, "No history available", "No history available", id_laptop.toInt()))
                    adapterhistory.historylist = historyList
                    adapterhistory.notifyDataSetChanged()
                }
            },
            { error ->
                Toast.makeText(requireContext(), error.toString(), Toast.LENGTH_SHORT).show()
            })
        requestQueue.add(stringRequest)
    }
}
