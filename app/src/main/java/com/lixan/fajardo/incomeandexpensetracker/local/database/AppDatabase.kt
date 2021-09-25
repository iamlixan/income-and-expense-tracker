package com.lixan.fajardo.incomeandexpensetracker.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.lixan.fajardo.incomeandexpensetracker.local.DBUserDataTokenTypeConverter
import com.lixan.fajardo.incomeandexpensetracker.local.dao.UserDataTokenDao
import com.lixan.fajardo.incomeandexpensetracker.local.models.DBUserDataToken


@Database(
    entities = [
        DBUserDataToken::class
    ],
    version = 1,
    exportSchema = true
)
@TypeConverters(DBUserDataTokenTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDataDao(): UserDataTokenDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context)
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            val dbName = "incomeandexpensetracker.db"

            val builder = Room.databaseBuilder(context, AppDatabase::class.java, dbName)
                .fallbackToDestructiveMigration()

            return builder.build()
        }
    }
}