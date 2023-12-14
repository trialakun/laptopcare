package com.example.laptopcarebeta

import android.content.Context
import android.graphics.PorterDuff
import android.os.AsyncTask
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.RetryPolicy
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class AnalyticsFragment : Fragment() {

    private lateinit var analyticsiplaptopedittext: EditText
    private lateinit var analyticsbtnconnect: ImageButton
    private lateinit var analyticsconnectioninfotextview: TextView
    private lateinit var analyticscpuusagetextview: TextView
    private lateinit var analyticsmemoryusagetextview: TextView
    private lateinit var analyticsdiskusagetextview: TextView
    private lateinit var analyticsbateraiinfotextview: TextView
    private lateinit var analyticsbateraihealttextview: TextView
    private lateinit var analyticsnetworkinfotextview: TextView
    private lateinit var analyticssysteminfotextview: TextView
    private lateinit var analyticsbtnsave: Button

    private val handler = Handler(Looper.getMainLooper())
    private val interval: Long = 100

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_analytics, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        analyticsiplaptopedittext = view.findViewById(R.id.AnalyticsIPEditText)
        analyticsbtnconnect = view.findViewById(R.id.AnalyticsBtnConnect)
        analyticsconnectioninfotextview = view.findViewById(R.id.AnalyticsConnectionInfoTextView)
        analyticscpuusagetextview = view.findViewById(R.id.AnalyticsResultCpuUsageTextView)
        analyticsmemoryusagetextview = view.findViewById(R.id.AnalyticsResultMemoryUsageTextView)
        analyticsdiskusagetextview = view.findViewById(R.id.AnalyticsResultDiskUsageTextView)
        analyticsbateraiinfotextview = view.findViewById(R.id.AnalyticsResultBateraiInfoTextView)
        analyticsbateraihealttextview = view.findViewById(R.id.AnalyticsResultBateraiHealtTextView)
        analyticsnetworkinfotextview = view.findViewById(R.id.AnalyticsResultNetworkInfoTextView)
        analyticssysteminfotextview = view.findViewById(R.id.AnalyticsResultSystemInfoTextView)
        analyticsbtnsave = view.findViewById(R.id.AnalyticsBtnSave)

        analyticsbtnsave.isEnabled = false

        val disabledColor = ContextCompat.getColor(requireContext(), R.color.abu)
        analyticsbtnsave.background.setColorFilter(disabledColor, PorterDuff.Mode.SRC_ATOP)

        analyticsbtnconnect.setOnClickListener {
            handler.post(object : Runnable {
                override fun run() {
                    fetchDataFromServer()
                    handler.postDelayed(this, interval)
                }
            })
        }
    }

    private fun fetchDataFromServer() {
        val sharedPreferences = requireActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val idlap = sharedPreferences.getString("id_laptop", "")
        val serverIp = analyticsiplaptopedittext.text.toString()
        val serverUrl = "http://$serverIp:8000/get_performance"

        AsyncTask.execute {
            try {
                val url = URL(serverUrl)
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "GET"

                val responseCode = connection.responseCode
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    val reader = BufferedReader(InputStreamReader(connection.inputStream))
                    val response = StringBuilder()

                    var line: String?
                    while (reader.readLine().also { line = it } != null) {
                        response.append(line).append('\n')
                    }

                    val jsonObject = JSONObject(response.toString())
                    val cpuUsage = jsonObject.getString("cpu_usage")
                    val memoryUsage = jsonObject.getString("memory_usage")
                    val diskUsage = jsonObject.getString("disk_usage")
                    val batteryInfo = jsonObject.getString("battery_info")
                    val batteryHealth = jsonObject.getString("battery_health")
                    val networkInfo = jsonObject.getJSONObject("network_info") // Mendapatkan informasi jaringan dalam format JSON
                    val systemInfo = jsonObject.getJSONObject("system_info") // Mendapatkan informasi sistem dalam format JSON

                    val formattedSystemInfo = StringBuilder()
                    formattedSystemInfo.append("OS: ${systemInfo.getString("system")} ${systemInfo.getString("release")}\n")
                    formattedSystemInfo.append("Node: ${systemInfo.getString("node")}\n")
                    formattedSystemInfo.append("Processor: ${systemInfo.getString("processor")}\n")
                    formattedSystemInfo.append("Architecture: ${systemInfo.getString("architecture")}\n")
                    formattedSystemInfo.append("CPUs: ${systemInfo.getInt("cpus")}\n")
                    formattedSystemInfo.append("Total Memory: ${systemInfo.getLong("total_memory") / (1024 * 1024)} MB\n")

                    val formattedNetworkInfo = StringBuilder()
                    for (key in networkInfo.keys()) {
                        val ips = networkInfo.get(key)
                        formattedNetworkInfo.append("$key:\n")
                        if (ips is String) {
                            // Jika alamat IP adalah string tunggal, langsung tambahkan ke formattedNetworkInfo
                            formattedNetworkInfo.append("- $ips\n")
                        } else if (ips is JSONArray) {
                            // Jika alamat IP adalah JSON array, tambahkan setiap alamat IP ke dalam formattedNetworkInfo
                            for (i in 0 until ips.length()) {
                                formattedNetworkInfo.append("- ${ips.getString(i)}\n")
                            }
                        }
                    }

                    requireActivity().runOnUiThread {
                        analyticsconnectioninfotextview.text = "Connected"
                        analyticscpuusagetextview.text = "$cpuUsage"
                        analyticsmemoryusagetextview.text = "$memoryUsage"
                        analyticsdiskusagetextview.text = "$diskUsage"
                        analyticsbateraiinfotextview.text = "$batteryInfo"
                        analyticsbateraihealttextview.text = "$batteryHealth"
                        analyticsnetworkinfotextview.text = formattedNetworkInfo.toString()
                        analyticssysteminfotextview.text = formattedSystemInfo.toString()

                        analyticsbtnsave.isEnabled = true
                        analyticsbtnsave.background.clearColorFilter()

                        val idlaptop = idlap.toString()
                        val currentDateTime = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())
                        val desHistori = "CPU Usage: $cpuUsage\nMemory Usage: $memoryUsage\nDisk Usage: $diskUsage\nBattery Info: $batteryInfo\nBattery Health: $batteryHealth\nNetwork Info: $networkInfo\nSystem Info: $systemInfo"

                        analyticsbtnsave.setOnClickListener {
                            val url = "https://mieruch.000webhostapp.com/analytics_save.php" // ganti dengan URL server Anda
                            val stringRequest = object : StringRequest(
                                Request.Method.POST,
                                url,
                                Response.Listener<String> { response ->
                                    // Menggunakan respons di sini
                                    Toast.makeText(requireContext(), "Berhasil Disave", Toast.LENGTH_SHORT).show()
                                },
                                Response.ErrorListener { error ->
                                    // Menangani kesalahan di sini
                                    if (error.networkResponse != null && error.networkResponse.statusCode == 500) {
                                        Toast.makeText(requireContext(), "Server error: 500", Toast.LENGTH_SHORT).show()
                                    } else {
                                        Toast.makeText(requireContext(), error.toString(), Toast.LENGTH_SHORT).show()
                                    }
                                }
                            ) {
                                override fun getParams(): HashMap<String, String> {
                                    val params = HashMap<String, String>()
                                    params["judul_histori"] = currentDateTime
                                    params["des_histori"] = desHistori
                                    params["id_laptop"] = idlaptop
                                    return params
                                }
                            }
                            val socketTimeout = 30000 // 30 detik
                            val policy: RetryPolicy = DefaultRetryPolicy(socketTimeout,
                                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
                            stringRequest.retryPolicy = policy
                            val requestQueue = Volley.newRequestQueue(requireContext())
                            requestQueue.add(stringRequest)
                        }

                    }
                } else {
                    requireActivity().runOnUiThread {
                        analyticsconnectioninfotextview.text = "Gagal mendapatkan data."
                        analyticscpuusagetextview.text = "Null"
                        analyticsmemoryusagetextview.text = "Null"
                        analyticsdiskusagetextview.text = "Null"
                        analyticsbateraiinfotextview.text = "Null"
                        analyticsbateraihealttextview.text = "Null"
                        analyticsnetworkinfotextview.text = "Null"
                        analyticssysteminfotextview.text = "Null"

                        analyticsbtnsave.isEnabled = false

                        val disabledColor = ContextCompat.getColor(requireContext(), R.color.abu)
                        analyticsbtnsave.background.setColorFilter(disabledColor, PorterDuff.Mode.SRC_ATOP)
                    }
                }
            } catch (e: Exception) {
                requireActivity().runOnUiThread {
                    analyticsconnectioninfotextview.text = "Terjadi kesalahan: ${e.message}"
                    analyticscpuusagetextview.text = "Null"
                    analyticsmemoryusagetextview.text = "Null"
                    analyticsdiskusagetextview.text = "Null"
                    analyticsbateraiinfotextview.text = "Null"
                    analyticsbateraihealttextview.text = "Null"
                    analyticsnetworkinfotextview.text = "Null"
                    analyticssysteminfotextview.text = "Null"

                    analyticsbtnsave.isEnabled = false

                    val disabledColor = ContextCompat.getColor(requireContext(), R.color.abu)
                    analyticsbtnsave.background.setColorFilter(disabledColor, PorterDuff.Mode.SRC_ATOP)
                }
                e.printStackTrace()
            }
        }
    }
}