package com.example.shoppinglist.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.shoppinglist.data.db.entities.ShoppingItem

@Database(
    entities = [ShoppingItem::class],
    version = 1,
    exportSchema = false
)
abstract class ShoppingDatabase : RoomDatabase(){
    abstract fun getShoppingDao() : ShoppingDao

    companion object{
        @Volatile
        private var instance: ShoppingDatabase? = null

        private val LOCK = Any()

        //It is called everytime we use Shopping Database
        operator fun invoke(context: Context) = instance ?: synchronized(LOCK){ //Kunubai invoke korile, Lock eta logai dibo
            instance ?: createDatabase(context).also{ instance = it} //Jodi DB bonua nai, null hoi, then eta bonai dibo
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext,
            ShoppingDatabase::class.java,
            "ShoppingDB.db").build()
    }
}