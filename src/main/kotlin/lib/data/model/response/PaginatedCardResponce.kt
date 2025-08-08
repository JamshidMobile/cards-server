package jtoir.uz.lib.data.model.response

import jtoir.uz.lib.data.model.CardModel
import kotlinx.serialization.Serializable

@Serializable
data class PaginatedCardResponce(
    val data: List<CardModel>,
    val page: Int,
    val limit: Int,
    val total: Long
)
