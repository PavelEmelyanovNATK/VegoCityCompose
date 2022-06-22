package com.emelyanov.vegocity.shared.domain.models.response

import androidx.annotation.Keep
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class PhotoResponse(
    @SerialName("photoId")
    val id: String,
    @SerialName("lowResPath")
    val lowResPath: String,
    @SerialName("highResPath")
    val highResPath: String,
)