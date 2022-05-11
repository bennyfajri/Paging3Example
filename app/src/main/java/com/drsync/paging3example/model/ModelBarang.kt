package com.drsync.paging3example.model

import com.google.gson.annotations.SerializedName

data class ModelBarang(
    val status: Boolean,
    @SerializedName("total_pages")
    val totalPages: Int,
    val message: String,
    val data: List<Barang>
)