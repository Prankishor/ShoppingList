package com.example.shoppinglist.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.shoppinglist.data.db.entities.ShoppingItem

@Dao
interface ShoppingDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(item: ShoppingItem)

    //Note: It doesn't allow us to write data in the main thread that is why we use coroutins
    //suspend is written so that we can perform these operations asynchronously.

    @Delete
    suspend fun delete(item: ShoppingItem)

    @Query("select * from shopping_items")
    fun getAllShoppingItems():LiveData<List<ShoppingItem>>

}