package com.drsync.paging3example.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.drsync.paging3example.api.ApiConfig
import com.drsync.paging3example.model.Barang
import com.drsync.paging3example.ui.barang.BarangPagingSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class BarangRepository @Inject constructor(
    private val apiConfig: ApiConfig
) {
    fun getDataBarang(): Flow<PagingData<Barang>>  {
        return Pager(
            config = PagingConfig(
                pageSize = 5,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                BarangPagingSource(apiConfig)
            }
        ).flow
    }

}