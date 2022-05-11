package com.drsync.paging3example.ui.barang

import androidx.lifecycle.ViewModel
import com.drsync.paging3example.repository.BarangRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BarangViewModel @Inject constructor(
    private val barangRepository: BarangRepository
): ViewModel(){

    val barang = barangRepository.getDataBarang()
}