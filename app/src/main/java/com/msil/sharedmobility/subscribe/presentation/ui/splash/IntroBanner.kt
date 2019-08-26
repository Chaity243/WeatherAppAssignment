package com.msil.sharedmobility.subscribe.presentation.ui.splash

import android.os.Parcel
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


data class IntroBanner(
    var image :Int,
    var title:String,
    var text :String
):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!
    ) {
    }

    override fun writeToParcel(p0: Parcel?, p1: Int) {
        println("write here")
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<IntroBanner> {
        override fun createFromParcel(parcel: Parcel): IntroBanner {
            return IntroBanner(parcel)
        }

        override fun newArray(size: Int): Array<IntroBanner?> {
            return arrayOfNulls(size)
        }
    }
}