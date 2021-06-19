package com.example.shoppinglist.ui.shoppinglist

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shoppinglist.R
import com.example.shoppinglist.data.db.ShoppingDatabase
import com.example.shoppinglist.data.db.entities.ShoppingItem
import com.example.shoppinglist.databinding.ActivityShoppingBinding
import com.example.shoppinglist.other.ShoppingItemAdapter
import com.example.shoppinglist.repositories.ShoppingRepository

class ShoppingActivity : AppCompatActivity() {

    private lateinit var binding : ActivityShoppingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shopping)

        val database = ShoppingDatabase(this)
        val repository = ShoppingRepository(database)
        val factory = ShoppingViewModelFactory(repository)

        val viewModel = ViewModelProvider(this, factory).get(ShoppingViewModel::class.java)
        binding = ActivityShoppingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val adapter = ShoppingItemAdapter(listOf(), viewModel)

        binding.rvShoppingItems.layoutManager = LinearLayoutManager(this)

        binding.rvShoppingItems.adapter = adapter

        viewModel.getAllShoppingItems().observe(this, {
            adapter.items = it
            adapter.notifyDataSetChanged()
        })

        binding.fab.setOnClickListener{
            AddShoppingItemDialog(this,
            object : AddDialogListener{
                override fun onAddButtonClicked(item: ShoppingItem) {
                    viewModel.upsert(item)
                }
            }).show()
        }

    }
}