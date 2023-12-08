package com.example.laptopcarebeta

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment


class ProfileFragment : Fragment() {

    private lateinit var profilenamaakuntextview: TextView
    private lateinit var profileusernameakunvextview: TextView
    private lateinit var profilebtnmyaccountlinearlayout: LinearLayout
    private lateinit var profilebtnmylaptoplinearlayout: LinearLayout
    private lateinit var profilebtnlogoutlinearlayout: LinearLayout
    private lateinit var profilebtnaboutapplinearlayout: LinearLayout

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
        profilebtnmyaccountlinearlayout = view.findViewById(R.id.ProfileBtnMyAccountLinearLayout)
        profilebtnmylaptoplinearlayout = view.findViewById(R.id.ProfileBtnMyLaptopLinearLayout)
        profilebtnlogoutlinearlayout = view.findViewById(R.id.ProfileBtnLogOutLinearLayout)
        profilebtnaboutapplinearlayout = view.findViewById(R.id.ProfileBtnAboutAppLinearLayout)

        val sharedPreferences = requireActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val namaAkun = sharedPreferences.getString("nama_akun", "")
        val userName = sharedPreferences.getString("username", "")

        profilenamaakuntextview.text = "$namaAkun"
        profileusernameakunvextview.text = "$userName"

        profilebtnlogoutlinearlayout.setOnClickListener {
            val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
            builder.setTitle("Konfirmasi Logout")
            builder.setMessage("Anda yakin ingin logout?")

            builder.setPositiveButton("Ya",
                DialogInterface.OnClickListener { dialog, which -> // Lakukan logout atau action yang sesuai di sini
                    Toast.makeText(context, "Kamu sudah logout", Toast.LENGTH_SHORT).show()
                    val sharedPreferences: SharedPreferences = requireActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
                    val editor = sharedPreferences.edit()
                    editor.clear() // Menghapus semua data dari SharedPreferences
                    editor.apply()
                    startActivity(Intent(requireActivity(), MainActivity::class.java))
                    requireActivity().finish()
                })

            builder.setNegativeButton("Batal",
                DialogInterface.OnClickListener { dialog, which -> // Batalkan atau tutup dialog jika tombol "Batal" ditekan
                    dialog.dismiss()
                })

            val dialog: AlertDialog = builder.create()
            dialog.show()
        }

        profilebtnmyaccountlinearlayout.setOnClickListener {
            val MyAccountFragment = MyAccountFragment() // Ganti NewFragment dengan fragment yang ingin ditampilkan
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragmentContainerView1, MyAccountFragment) // Ganti R.id.fragment_container dengan ID container fragment Anda
            transaction.addToBackStack(null)
            transaction.commit()
        }

        profilebtnmylaptoplinearlayout.setOnClickListener {
            val mylaptopFragment = MyLaptopFragment() // Ganti NewFragment dengan fragment yang ingin ditampilkan
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragmentContainerView1, mylaptopFragment) // Ganti R.id.fragment_container dengan ID container fragment Anda
            transaction.addToBackStack(null)
            transaction.commit()
        }

        profilebtnaboutapplinearlayout.setOnClickListener {
            val aboutappFragment = aboutappFragment() // Ganti NewFragment dengan fragment yang ingin ditampilkan
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragmentContainerView1, aboutappFragment) // Ganti R.id.fragment_container dengan ID container fragment Anda
            transaction.addToBackStack(null)
            transaction.commit()
        }
    }
}