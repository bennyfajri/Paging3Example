package com.drsync.paging3example.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.drsync.paging3example.api.ApiConfig
import com.drsync.paging3example.ui.barang.BarangPagingSource
import javax.inject.Inject


class BarangRepository @Inject constructor(
    private val apiConfig: ApiConfig
) {
    fun getDataBarang() = Pager(
        config = PagingConfig(
            pageSize = 5,
            enablePlaceholders = false
        ),
        pagingSourceFactory = { BarangPagingSource(apiConfig) }
    ).liveData

}