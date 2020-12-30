
package com.jaa.library.domain.entity

import kotlinx.serialization.Serializable

import kotlinx.serialization.SerialName

/**
 * 
 * @param response A boolean indicating success or error
 * @param error A string describing the error
 */
@Serializable
data class FilmImageBadResponse (
    /* A boolean indicating success or error */
    @SerialName("Response")
    val response: String? = null,
    /* A string describing the error */
    @SerialName("Error")
    val error: String? = null
)

