package org.cfarrell.hillfort.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.Date;


@Parcelize
data class HillfortModel(


        var id: Long = 0,
        var title: String = "",
        var description: String = "",
        var image: ArrayList<String> = ArrayList(),
        var lat: Double = 0.0,
        var lng: Double = 0.0,
        var zoom: Float = 0f,
        var visitedFlag: Boolean = false,
        var favouriteFlag: Boolean = false,
        var visitedDate: Date = Date(),
        var rating: Byte = 0) : Parcelable

@Parcelize
data class Location(var lat: Double = 0.0,
                    var lng: Double = 0.0,
                    var zoom: Float = 0f) : Parcelable

