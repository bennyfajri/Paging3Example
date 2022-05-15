package com.drsync.paging3example.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.drsync.paging3example.model.Barang

@Database(
    entities = [Barang::class, RemoteKeys::class],
    version = 1,
    exportSchema = false

)
abstract class BarangDatabase : RoomDatabase() {
    abstract fun barangDao(): BarangDao
    abstract fun remoteKeysDao(): RemoteKeysDao
}