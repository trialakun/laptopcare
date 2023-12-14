package com.example.laptopcarebeta

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView

class HistoryAdapter (var historylist: ArrayList<ModelHistory>) : RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val judulhistory: TextView = itemView.findViewById(R.id.HistoryJudulHistoriTextView)
        val deshistory: TextView = itemView.findViewById(R.id.HistoriDeskripsiHistoriTextView)
        val btnshowdetail: LinearLayout = itemView.findViewById(R.id.btnlisthistorilinearlayout)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_histori, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return historylist.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val infohistory = historylist[position]

        holder.judulhistory.text = infohistory.judulhistori
        holder.deshistory.text = infohistory.deshistori
        holder.btnshowdetail.setOnClickListener {
            val context = holder.itemView.context
            val fragmentManager = (context as AppCompatActivity).supportFragmentManager // Jika fragment di-host oleh AppCompatActivity

            val detailHistoryFragment = detailHistoryFragment()
            val bundle = Bundle()
            bundle.putParcelable("history", infohistory)
            detailHistoryFragment.arguments = bundle

            fragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView1, detailHistoryFragment) // Ganti R.id.fragment_container dengan ID dari container fragment Anda
                .addToBackStack(null) // Jika Anda ingin menambahkan transaksi ke back stack
                .commit()
        }
    }
}
