package com.example.laptopcarebeta

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
import androidx.core.content.ContextCompat
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

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