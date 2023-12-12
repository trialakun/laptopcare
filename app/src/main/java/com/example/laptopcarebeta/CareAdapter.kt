package com.example.laptopcarebeta

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView

class CareAdapter (var careList: ArrayList<ModelLaptopCare>) : RecyclerView.Adapter<CareAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val judulcare: TextView = itemView.findViewById(R.id.CareTitlePerawatanLaptopTextView)
        val descare: TextView = itemView.findViewById(R.id.CareDeskripsiPerawatanLaptopTextView)
        val btnshowdetail: LinearLayout = itemView.findViewById(R.id.btnlistlaptopcarelinearlayout)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_laptop_care, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return careList.size
    }

    override fun onBindViewHolder(holder: CareAdapter.ViewHolder, position: Int) {
        val infocare = careList[position]

        holder.judulcare.text = infocare.judulcare
        holder.descare.text = infocare.descare
        holder.btnshowdetail.setOnClickListener {
            val context = holder.itemView.context
            val fragmentManager = (context as AppCompatActivity).supportFragmentManager // Jika fragment di-host oleh AppCompatActivity

            val detailCareFragment = DetailCareFragment()
            val bundle = Bundle()
            bundle.putParcelable("care", infocare)
            detailCareFragment.arguments = bundle

            fragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView1, detailCareFragment) // Ganti R.id.fragment_container dengan ID dari container fragment Anda
                .addToBackStack(null) // Jika Anda ingin menambahkan transaksi ke back stack
                .commit()
        }
    }
}