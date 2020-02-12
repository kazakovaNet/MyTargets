package ru.kazakova_net.mytargets.database

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by Kazakova_net on 05.02.2020.
 */
@Entity(tableName = "targets")
data class Target(

    @PrimaryKey(autoGenerate = true)
    val targetId: Long = 0L,
    var parentId: Long = -1,
    var title: String = "",
    var description: String = "",
    var longText: String = ""
)