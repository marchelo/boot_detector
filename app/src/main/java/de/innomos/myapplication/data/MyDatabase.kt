package de.innomos.myapplication.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import de.innomos.myapplication.data.entities.BootRecord

@Database(entities = [BootRecord::class], version = 1, exportSchema = false)
abstract class MyDatabase : RoomDatabase() {

    abstract fun bootRecordDao(): BootRecordDao

    companion object {
        @Volatile
        private var INSTANCE: MyDatabase? = null

        fun getDatabase(context: Context): MyDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MyDatabase::class.java,
                    "word_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}