package com.example.laptopcarebeta

import android.os.Parcel
import android.os.Parcelable

class ModelHistory (val idhistory: Int, val judulhistori: String, val deshistori: String, val idlaptop: Int) :
    Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readInt()
    )

    fun isEmpty(): Boolean {
        return idhistory == 0 && judulhistori.isEmpty() && deshistori.isEmpty()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(idhistory)
        parcel.writeString(judulhistori)
        parcel.writeString(deshistori)
        parcel.writeInt(idlaptop)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ModelHistory> {
        override fun createFromParcel(parcel: Parcel): ModelHistory {
            return ModelHistory(parcel)
        }

        override fun newArray(size: Int): Array<ModelHistory?> {
            return arrayOfNulls(size)
        }
    }
}
