
package com.jaa.library.domain.entity

import kotlinx.serialization.Serializable

import kotlinx.serialization.SerialName

@Serializable
data class FilmImageData (
    /* The title of the film */
    @SerialName("Title")
    val title: String? = null,
    /* An image of the film */
    @SerialName("Poster")
    val poster: String? = null,
    /* A boolean indicating success or error */
    @SerialName("Response")
    val response: String? = null,
    /* A string specifying the error */
    @SerialName("Error")
    val error: String? = null
)
