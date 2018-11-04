package org.cfarrell.hillfort.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class HillfortModel(var id: Long = 0,
                          var title: String = "",
                          var description: String = "",
                          var image: ArrayList<String> = ArrayList(),
                         var lat : Double = 0.0,
                          var lng: Double = 0.0,
                          var zoom: Float = 0f,
                         var visitedFlag: Boolean = true,
                         var visitedDate: Date) : Parcelable

@Parcelize
data class Location(var lat: Double = 0.0,
                    var lng: Double = 0.0,
                    var zoom: Float = 0f) : Parcelable

