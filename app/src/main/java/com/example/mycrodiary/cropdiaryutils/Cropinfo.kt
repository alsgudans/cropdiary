package com.example.mycrodiary.cropdiaryutils

import android.os.Parcel
import android.os.Parcelable

data class Cropinfo(val nickname: String, val crop: String, val date: String) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!
    )

    override fun toString(): String {
        return "닉네임: $nickname, 작물: $crop, 날짜: $date"
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(nickname)
        parcel.writeString(crop)
        parcel.writeString(date)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Cropinfo> {
        override fun createFromParcel(parcel: Parcel): Cropinfo {
            return Cropinfo(parcel)
        }

        override fun newArray(size: Int): Array<Cropinfo?> {
            return arrayOfNulls(size)
        }
    }
}