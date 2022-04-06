package com.example.firedron.dto

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

//Output
@Parcelize
data class Token(
    var auth_token : String?,
) : Parcelable