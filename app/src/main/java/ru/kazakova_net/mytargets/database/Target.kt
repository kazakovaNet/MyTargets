package ru.kazakova_net.mytargets.database

/**
 * Created by Kazakova_net on 05.02.2020.
 */
data class Target(
    val targetId: Long = 0L,
    var title: String = "",
    var description: String = "",
    var longText: String = ""
)