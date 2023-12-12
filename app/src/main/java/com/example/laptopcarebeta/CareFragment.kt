package com.example.laptopcarebeta

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class CareFragment : Fragment() {

    private lateinit var recyclerViewcare: RecyclerView
    private lateinit var adaptercare: CareAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_care, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerViewcare = view.findViewById(R.id.CareLaptopCareRecyclerView)
        recyclerViewcare.layoutManager = LinearLayoutManager(activity)
        adaptercare = CareAdapter(ArrayList())
        recyclerViewcare.adapter = adaptercare

        val url = "https://mieruch.000webhostapp.com/care.php"
        val requestQueue = Volley.newRequestQueue(activity)
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                val jsonObject = JSONObject(response)
                val jsonArray = jsonObject.getJSONArray("data")
                val careList = ArrayList<ModelLaptopCare>()
                for (i in 0 until jsonArray.length()) {
                    val jsonObject = jsonArray.getJSONObject(i)
                    val idcare = jsonObject.getInt("id_care")
                    val judulcare = jsonObject.getString("judul_care")
                    val descare = jsonObject.getString("des_care")
                    careList.add(ModelLaptopCare(idcare, judulcare, descare))
                }
                adaptercare.careList = careList
                adaptercare.notifyDataSetChanged()
            },
            { error ->
                //
            })
        requestQueue.add(stringRequest)
    }
}