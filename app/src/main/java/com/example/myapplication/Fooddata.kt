package com.example.myapplication

import android.os.Parcel
import android.os.Parcelable

data class Fooddata(val textView: String,
                    val textView2: String,
                    val imageView: Int,
                    val details : String) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readString()!!,
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(textView)
        parcel.writeString(textView2)
        parcel.writeInt(imageView)
        parcel.writeString(details)

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Fooddata> {
        override fun createFromParcel(parcel: Parcel): Fooddata {
            return Fooddata(parcel)
        }

        override fun newArray(size: Int): Array<Fooddata?> {
            return arrayOfNulls(size)
        }
    }
}
