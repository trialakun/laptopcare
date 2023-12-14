package com.example.laptopcarebeta

import android.os.Parcel
import android.os.Parcelable

data class Modelinfolaptop(val idinfolaptop: Int, val namainfolaptop: String, val desinfolaptop: String) :
    Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readString() ?: ""
    )

    fun isEmpty(): Boolean {
        return idinfolaptop == 0 && namainfolaptop.isEmpty() && desinfolaptop.isEmpty()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(idinfolaptop)
        parcel.writeString(namainfolaptop)
        parcel.writeString(desinfolaptop)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Modelinfolaptop> {
        override fun createFromParcel(parcel: Parcel): Modelinfolaptop {
            return Modelinfolaptop(parcel)
        }

        override fun newArray(size: Int): Array<Modelinfolaptop?> {
            return arrayOfNulls(size)
        }
    }
}
