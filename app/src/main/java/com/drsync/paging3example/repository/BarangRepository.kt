package com.drsync.paging3example.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.drsync.paging3example.api.ApiConfig
import com.drsync.paging3example.database.BarangDatabase
import com.drsync.paging3example.ui.barang.BarangPagingSource
import com.drsync.paging3example.ui.barang.BarangRemoteMediator
import javax.inject.Inject


class BarangRepository @Inject constructor(
    private val apiConfig: ApiConfig,
    private val database: BarangDatabase
) {
    @OptIn(ExperimentalPagingApi::class)
    fun getDataBarang() = Pager(
        config = PagingConfig(
            pageSize = 5,
            enablePlaceholders = false
        ),
        remoteMediator = BarangRemoteMediator(database, apiConfig),
        pagingSourceFactory = {
//            BarangPagingSource(apiConfig)
            database.barangDao().getAllBarang()
        }
    ).flow

}