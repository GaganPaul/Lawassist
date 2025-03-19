package com.example.lawassist.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [LawEntity::class], version = 1, exportSchema = false)
abstract class LawDatabase : RoomDatabase() {

    abstract fun lawDao(): LawDao

    companion object {
        @Volatile
        private var INSTANCE: LawDatabase? = null

        fun getDatabase(context: Context): LawDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    LawDatabase::class.java,
                    "law_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
