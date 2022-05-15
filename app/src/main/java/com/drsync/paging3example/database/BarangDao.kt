package com.drsync.paging3example.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.drsync.paging3example.model.Barang

@Dao
interface BarangDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBarang(barang: List<Barang>)

    @Query("SELECT * FROM barang")
    fun getAllBarang(): PagingSource<Int, Barang>

    @Query("DELETE FROM barang")
    suspend fun deleteAll()
}