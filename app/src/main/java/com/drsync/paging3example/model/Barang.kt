package com.drsync.paging3example.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "barang")
data class Barang(
    @PrimaryKey
    val id: Int,
    val nama: String,
    val stock: Int,
    val harga: Int,
    val terjual: Int
)
