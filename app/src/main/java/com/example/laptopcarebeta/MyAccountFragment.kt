package com.example.laptopcarebeta

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView

class MyAccountFragment : Fragment() {

    private lateinit var btnbackdarimyaccounttoprofile: ImageView
    private lateinit var myaccountnamausertextview: TextView
    private lateinit var myaccountusernameltextview: TextView
    private lateinit var myaccountnamaedittext: EditText
    private lateinit var myaccountemailEdittext: EditText

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

        val sharedPreferences = requireActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val namaAkun = sharedPreferences.getString("nama_akun", "")
        val userName = sharedPreferences.getString("username", "")

        myaccountnamausertextview.text = "$namaAkun"
        myaccountusernameltextview.text = "$userName"

        btnbackdarimyaccounttoprofile.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }
}