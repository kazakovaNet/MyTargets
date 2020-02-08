package ru.kazakova_net.mytargets.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * Created by Kazakova_net on 08.02.2020.
 */
@Database(entities = [Target::class], version = 1, exportSchema = false)
abstract class TargetsDatabase : RoomDatabase() {

    abstract val targetsDatabaseDao: TargetsDatabaseDao

    companion object {

        @Volatile
        private var INSTANCE: TargetsDatabase? = null

        fun getInstance(context: Context): TargetsDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        TargetsDatabase::class.java,
                        "targets_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()

                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}