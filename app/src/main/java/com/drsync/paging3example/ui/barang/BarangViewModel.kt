package com.drsync.paging3example.ui.barang

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.drsync.paging3example.model.Barang
import com.drsync.paging3example.repository.BarangRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class BarangViewModel @Inject constructor(
    private val barangRepository: BarangRepository
) : ViewModel() {

    fun getBarang(): Flow<PagingData<Barang>> = barangRepository.getDataBarang()
}