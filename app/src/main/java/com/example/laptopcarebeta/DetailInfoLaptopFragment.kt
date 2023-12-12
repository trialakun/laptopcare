package com.example.laptopcarebeta

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

class DetailInfoLaptopFragment : Fragment() {

    private var infoLaptop: Modelinfolaptop? = null

    private lateinit var btnbackdaridetailinfolaptoptohome: ImageView
    private lateinit var detailinfolaptoptitletextview: TextView
    private lateinit var detaildeskripsiinfolaptoptextview: TextView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail_info_laptop, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        infoLaptop = arguments?.getParcelable("info_laptop")

        btnbackdaridetailinfolaptoptohome = view.findViewById(R.id.btnbackdaridetailinfolaptoptohome)
        detailinfolaptoptitletextview = view.findViewById(R.id.DetailInfoLaptopTitleTextView)
        detaildeskripsiinfolaptoptextview = view.findViewById(R.id.DetailDeskripsiInfoLaptopTextView)

        infoLaptop?.let {
            detailinfolaptoptitletextview.text = it.namainfolaptop
            detaildeskripsiinfolaptoptextview.text = it.desinfolaptop
        }

        btnbackdaridetailinfolaptoptohome.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }
}