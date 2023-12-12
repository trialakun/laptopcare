package com.example.laptopcarebeta

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

class DetailCareFragment : Fragment() {

    private var infocare: ModelLaptopCare? = null

    private lateinit var btnbackdaridetailcaretocare: ImageView
    private lateinit var detailcaretitletextview: TextView
    private lateinit var detaildeskripsicaretextview: TextView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail_care, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        infocare = arguments?.getParcelable("care")

        btnbackdaridetailcaretocare = view.findViewById(R.id.btnbackdaridetailcaretocare)
        detailcaretitletextview = view.findViewById(R.id.DetailCareTitleTextView)
        detaildeskripsicaretextview = view.findViewById(R.id.DetailDeskripsiCareTextView)

        infocare?.let {
            detailcaretitletextview.text = it.judulcare
            detaildeskripsicaretextview.text = it.descare
        }

        btnbackdaridetailcaretocare.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }
}