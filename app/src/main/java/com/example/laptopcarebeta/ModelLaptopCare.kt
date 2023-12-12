package com.example.laptopcarebeta

import android.os.Parcel
import android.os.Parcelable

class ModelLaptopCare (val idcare: Int, val judulcare: String, val descare: String) :
    Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(idcare)
        parcel.writeString(judulcare)
        parcel.writeString(descare)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ModelLaptopCare> {
        override fun createFromParcel(parcel: Parcel): ModelLaptopCare {
            return ModelLaptopCare(parcel)
        }

        override fun newArray(size: Int): Array<ModelLaptopCare?> {
            return arrayOfNulls(size)
        }
    }
}
