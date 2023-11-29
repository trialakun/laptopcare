package com.example.laptopcarebeta

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class ProfileFragment : Fragment() {

    private lateinit var profilenamaakuntextview: TextView
    private lateinit var profileusernameakunvextview: TextView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        profilenamaakuntextview = view.findViewById(R.id.ProfileNamaAkunTextView)
        profileusernameakunvextview = view.findViewById(R.id.ProfileUsernameAkunTextView)

        val sharedPreferences = requireActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val namaAkun = sharedPreferences.getString("nama_akun", "")
        val userName = sharedPreferences.getString("username", "")

        profilenamaakuntextview.text = "$namaAkun"
        profileusernameakunvextview.text = "$userName"
    }
}