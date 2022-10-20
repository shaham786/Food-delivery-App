package com.example.myapplication.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.myapplication.models.Product

@Database(entities = [Product::class], version = 1)
@TypeConverters(Converter::class)
 abstract class ProductDatabase : RoomDatabase() {


 abstract fun productDao() : ProductDao

  companion object{

   @Volatile
   private var INSTANCE : ProductDatabase? = null

   fun getDatabase(context: Context) : ProductDatabase {

    if (INSTANCE == null){
     synchronized(this){
      INSTANCE = Room.databaseBuilder(context,ProductDatabase::class.java, "productDB").build()
     }
    }
    return INSTANCE!!
   }
  }

}