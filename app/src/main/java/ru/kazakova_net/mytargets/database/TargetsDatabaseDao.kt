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
    fun insert(target: MyTarget)

    @Update
    fun update(target: MyTarget)

    @Query("SELECT * FROM targets WHERE targetId = :key")
    fun get(key: Long): MyTarget?

    @Query("DELETE FROM targets")
    fun clear()

    @Query("SELECT * FROM targets ORDER BY targetId DESC LIMIT 1")
    fun getNewTarget(): MyTarget?

    @Query(
        "SELECT tg1.*, COUNT(tg2.targetId) as child_count " +
                "FROM targets AS tg1 " +
                "LEFT OUTER JOIN targets AS tg2 " +
                "ON tg2.parentId = tg1.targetId " +
                "WHERE tg1.parentId = :parentId " +
                "GROUP BY tg1.targetId"
    )
    fun getChildTargets(parentId: Long): LiveData<List<MyTarget>>

    @Query(
        "SELECT tg1.*, COUNT(tg2.targetId) as child_count " +
                "FROM targets AS tg1 " +
                "LEFT OUTER JOIN targets AS tg2 " +
                "ON tg2.parentId = tg1.targetId " +
                "WHERE tg1.parentId = -1 " +
                "GROUP BY tg1.targetId"
    )
    fun getAllGlobalTargets(): LiveData<List<MyTarget>>
}