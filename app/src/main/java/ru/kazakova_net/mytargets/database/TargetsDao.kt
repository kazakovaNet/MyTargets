package ru.kazakova_net.mytargets.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

/**
 * Created by Kazakova_net on 08.02.2020.
 */
@Dao
interface TargetsDatabaseDao {

    @Insert
    fun insert(target: Target)

    @Update
    fun update(target: Target)

    @Query("SELECT * FROM targets WHERE targetId = :key")
    fun get(key: Long): Target?

    @Query("DELETE FROM targets")
    fun clear()

    @Query("SELECT * FROM targets ORDER BY targetId DESC")
    fun getAllTargets(): LiveData<List<Target>>
}