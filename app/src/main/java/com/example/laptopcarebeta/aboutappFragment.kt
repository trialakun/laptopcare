package com.example.laptopcarebeta

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView

class aboutappFragment : Fragment() {

    private lateinit var btnbackdariaboutapptoprofile: ImageView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_aboutapp, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnbackdariaboutapptoprofile = view.findViewById(R.id.btnbackdariaboutapptoprofile)

        btnbackdariaboutapptoprofile.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }
}