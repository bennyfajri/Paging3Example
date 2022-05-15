package com.drsync.paging3example.ui.barang

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.drsync.paging3example.api.ApiConfig
import com.drsync.paging3example.database.BarangDatabase
import com.drsync.paging3example.database.RemoteKeys
import com.drsync.paging3example.model.Barang

@ExperimentalPagingApi
class BarangRemoteMediator(
    private val database: BarangDatabase,
    private val api: ApiConfig
) : RemoteMediator<Int, Barang>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Barang>
    ): MediatorResult {
        val page = when(loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: INITIAL_PAGE_INDEX
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                val prevKey = remoteKeys?.prevKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                prevKey
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                val nextKey = remoteKeys?.nextKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                nextKey
            }
        }

        try {
            val responseData = api.getDataBarang(page).data

            val endOfPaginationReached = responseData.isEmpty()

            database.withTransaction {
                if(loadType == LoadType.REFRESH) {
                    database.remoteKeysDao().deleteRemoteKeys()
                    database.barangDao().deleteAll()
                }
                val prevKey = if(page == 1) null else page - 1
                val nextKey = if(endOfPaginationReached) null else page + 1
                val keys = responseData.map {
                    RemoteKeys(id = it.id.toString(), prevKey = prevKey, nextKey = nextKey)
                }
                database.remoteKeysDao().insertAll(keys)
                database.barangDao().insertBarang(responseData)
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        }catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, Barang>): RemoteKeys? {
        return state.lastItemOrNull()?.let {
            return state.pages.lastOrNull() { it.data.isNotEmpty() }?.data?.lastOrNull()?.let { data ->
                database.remoteKeysDao().getRemoteKeysId(data.id.toString())
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, Barang>): RemoteKeys? {
        return state.firstItemOrNull()?.let {
            return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()?.let { data ->
                database.remoteKeysDao().getRemoteKeysId(data.id.toString())
            }
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, Barang>): RemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                database.remoteKeysDao().getRemoteKeysId(id.toString())
            }
        }
    }

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }
}