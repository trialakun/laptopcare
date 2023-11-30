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
import androidx.activity.addCallback

class HomeFragment : Fragment() {

    private lateinit var homenamausertextview: TextView
    private lateinit var sharedPreferences: SharedPreferences

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
        homenamausertextview = view.findViewById(R.id.HomeNamaUserTextView)
        homenamausertextview.text = "$namaAkun"
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
                // Lakukan logout atau keluar dari aplikasi di sini
                // Misalnya:
                // sharedPreferences.edit().clear().apply()
                requireActivity().finish()
            }
            .setNegativeButton("Tidak", null)
            .setCancelable(false)
            .show()
    }
}
