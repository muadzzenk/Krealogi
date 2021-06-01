package com.example.krealogi.network.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [
        UserTable::class
    ],
    version = 1
)

abstract class DatabaseRequestManagers : RoomDatabase() {

    companion object {
        @Volatile
        private var instance: DatabaseRequestManagers? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance
            ?: synchronized(LOCK) {
                instance
                    ?: buildDatabase(
                        context
                    ).also {
                        instance = it
                    }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                DatabaseRequestManagers::class.java,
                "Muvi Database"
            ).build()
    }

    abstract fun getDaoUser(): UserTable.DataAccessObject

}