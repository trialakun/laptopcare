package com.example.laptopcarebeta

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView

class MyLaptopFragment : Fragment() {

    private lateinit var btnbackdarimylaptoptoprofile: ImageView

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

        btnbackdarimylaptoptoprofile.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }
}