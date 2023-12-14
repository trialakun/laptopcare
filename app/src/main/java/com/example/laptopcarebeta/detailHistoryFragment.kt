package com.example.laptopcarebeta

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

class detailHistoryFragment : Fragment() {

    private var listhistori: ModelHistory? = null

    private lateinit var btnbackdaridetailhistoritohistori: ImageView
    private lateinit var detailjudulhistoritextview: TextView
    private lateinit var detaildeskripsihistoritextview: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listhistori = arguments?.getParcelable("history")

        btnbackdaridetailhistoritohistori = view.findViewById(R.id.btnbackdaridetailhistorytohistory)
        detailjudulhistoritextview = view.findViewById(R.id.DetailHistoryTitleTextView)
        detaildeskripsihistoritextview = view.findViewById(R.id.DetailDeskripsiHistoryTextView)

        listhistori?.let {
            detailjudulhistoritextview.text = it.judulhistori
            detaildeskripsihistoritextview.text = it.deshistori
        }
        btnbackdaridetailhistoritohistori.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }
}