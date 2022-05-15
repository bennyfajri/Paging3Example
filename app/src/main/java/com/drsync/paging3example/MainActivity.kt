package com.drsync.paging3example

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.drsync.paging3example.databinding.ActivityMainBinding
import com.drsync.paging3example.ui.barang.BarangAdapter
import com.drsync.paging3example.ui.barang.BarangLoadStateAdapter
import com.drsync.paging3example.ui.barang.BarangViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var barangAdapter: BarangAdapter
    private val viewModel by viewModels<BarangViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        barangAdapter = BarangAdapter()

        binding.apply {
            rvBarang.layoutManager = LinearLayoutManager(this@MainActivity)
            rvBarang.setHasFixedSize(true)
            rvBarang.adapter = barangAdapter.withLoadStateFooter(
                footer = BarangLoadStateAdapter {barangAdapter.retry()}
            )

        }

        lifecycleScope.launchWhenStarted{
            viewModel.barang.collect{
                barangAdapter.submitData(lifecycle, it)
            }
        }

    }
}