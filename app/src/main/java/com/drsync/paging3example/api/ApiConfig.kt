package com.drsync.paging3example.api

import com.drsync.paging3example.model.BarangResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiConfig {

    @GET("readv2.php")
    suspend fun getDataBarang(
        @Query("page_number") pageNumber: Int
    ): BarangResponse
}