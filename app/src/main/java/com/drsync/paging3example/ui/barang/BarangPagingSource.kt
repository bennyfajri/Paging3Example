package com.drsync.paging3example.ui.barang

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.drsync.paging3example.api.ApiConfig
import com.drsync.paging3example.model.Barang
import okio.IOException
import retrofit2.HttpException

class BarangPagingSource(
    private val api: ApiConfig
) : PagingSource<Int, Barang>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Barang> {
        return try {
            val position = params.key ?: STARTING_PAGE_NUMBER
            val response = api.getDataBarang(position)
            LoadResult.Page(
                data = response.data,
                prevKey = if(position == STARTING_PAGE_NUMBER ) null else position - 1,
                nextKey = if(response.data.isNullOrEmpty()) null else position + 1
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Barang>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    companion object {
        const val STARTING_PAGE_NUMBER = 1
    }
}