package com.maciejpaja.digiteqassigment.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.maciejpaja.digiteqassigment.databinding.ActivityMainBinding
import com.maciejpaja.digiteqassigment.ui.ConfigurableLayoutManager
import com.maciejpaja.digiteqassigment.ui.main.recyclerview.ItemAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)


        setContentView(binding.root)
        initRecyclerView()
    }


    private fun initRecyclerView() {
        val adapter = ItemAdapter()
        binding.recycler.layoutManager = ConfigurableLayoutManager(5,5)

        binding.recycler.adapter = adapter
        adapter.submitList(listOf("1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30",))
    }
}